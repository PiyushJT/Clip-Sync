package com.piyushjt.clipsync

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.util.Base64
import androidx.core.content.FileProvider
import com.piyushjt.clipsync.api.ResponseRequest
import com.piyushjt.clipsync.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class ClipboardRepository(private val context: Context) {

    private val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    private val sharedPreferences = context.getSharedPreferences("clipboard_sync_prefs", Context.MODE_PRIVATE)

    suspend fun syncClipboard(): Result<String> = withContext(Dispatchers.IO) {
        try {
            val serverIp = sharedPreferences.getString("server_ip", "") ?: ""
            if (serverIp.isEmpty()) {
                return@withContext Result.failure(Exception("Server IP not configured"))
            }

            // Get data from clipboard
            val clipData = clipboardManager.primaryClip
            var clipboardText: String? = null
            var clipboardImage: String? = null

            if (clipData != null && clipData.itemCount > 0) {
                val item = clipData.getItemAt(0)
                
                // Priority 1: Image URI
                val uri = item.uri
                if (uri != null) {
                    clipboardImage = getBase64FromUri(uri)
                }
                
                // Priority 2: Text (only if no image found or read failed)
                if (clipboardImage == null) {
                    clipboardText = item.text?.toString()?.takeIf { it.isNotEmpty() }
                }
            }

            val clipService = RetrofitClient.getClipService(serverIp)
            val response = clipService.exchange(ResponseRequest(clipboardText, clipboardImage))

            if (response.isSuccessful) {
                val responseBody = response.body()
                val resultText = responseBody?.text?.takeIf { it.isNotEmpty() }
                val resultImage = responseBody?.image?.takeIf { it.isNotEmpty() }

                if (resultImage != null) {
                    // Sync image to clipboard
                    val imageUri = saveBase64ToFile(resultImage)
                    if (imageUri != null) {
                        withContext(Dispatchers.Main) {
                            val clip = ClipData.newUri(context.contentResolver, "Synced Image", imageUri)
                            clipboardManager.setPrimaryClip(clip)
                        }
                        Result.success("Sync successful (Image synced to phone)")
                    } else {
                        Result.failure(Exception("Failed to save received image"))
                    }
                } else if (resultText != null) {
                    // Sync text to clipboard
                    withContext(Dispatchers.Main) {
                        val newClip = ClipData.newPlainText("Synced Clip", resultText)
                        clipboardManager.setPrimaryClip(newClip)
                    }
                    Result.success("Sync successful: $resultText")
                } else {
                    Result.success("Backend returned null/empty content")
                }
            } else {
                Result.failure(Exception("Server error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getBase64FromUri(uri: Uri): String? {
        return try {
            val type = context.contentResolver.getType(uri) ?: ""
            // We only care about images
            if (!type.startsWith("image/") && !uri.toString().contains("image")) {
                // If type is unknown, try to read it anyway if it's a content/file URI
            }
            
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val bytes = inputStream.readBytes()
                if (bytes.isEmpty()) return null
                Base64.encodeToString(bytes, Base64.NO_WRAP)
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun saveBase64ToFile(base64: String): Uri? {
        return try {
            val bytes = Base64.decode(base64, Base64.DEFAULT)
            if (bytes.isEmpty()) return null
            
            val file = File(context.cacheDir, "synced_image.png")
            FileOutputStream(file).use { it.write(bytes) }
            
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
        } catch (e: Exception) {
            null
        }
    }
}
