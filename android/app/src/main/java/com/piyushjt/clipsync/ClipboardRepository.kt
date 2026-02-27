package com.piyushjt.clipsync

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.piyushjt.clipsync.api.ResponseRequest
import com.piyushjt.clipsync.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClipboardRepository(private val context: Context) {

    private val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    suspend fun syncClipboard(): Result<String> = withContext(Dispatchers.IO) {
        try {
            // Get text from clipboard
            val clipData = clipboardManager.primaryClip
            val clipboardText = if (clipData != null && clipData.itemCount > 0) {
                clipData.getItemAt(0).text?.toString() ?: ""
            } else {
                ""
            }

            if (clipboardText.isEmpty()) {
                return@withContext Result.failure(Exception("Clipboard is empty"))
            }

            val response = RetrofitClient.clipService.exchange(ResponseRequest(clipboardText))

            if (response.isSuccessful) {
                val responseBody = response.body()
                val resultText = responseBody?.text ?: ""

                if (resultText.isNotEmpty()) {
                    // Copy received text to clipboard
                    // Note: clipboardManager.setPrimaryClip must be called on the main thread
                    withContext(Dispatchers.Main) {
                        val newClip = ClipData.newPlainText("Synced Clip", resultText)
                        clipboardManager.setPrimaryClip(newClip)
                    }
                    Result.success(resultText)
                } else {
                    Result.failure(Exception("Received empty text"))
                }
            } else {
                Result.failure(Exception("Error code: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
