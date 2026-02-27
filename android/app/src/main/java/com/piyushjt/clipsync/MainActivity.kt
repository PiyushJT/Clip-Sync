package com.piyushjt.clipsync

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.piyushjt.clipsync.api.ResponseRequest
import com.piyushjt.clipsync.api.RetrofitClient
import com.piyushjt.clipsync.ui.theme.ClipSyncTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val scope = rememberCoroutineScope()
            ClipSyncTheme {
                val context = LocalContext.current
                val repository = ClipboardRepository(context)

                Button(
                    modifier = Modifier.fillMaxSize(0.5f),
                    onClick = {
                        scope.launch {
                            repository.syncClipboard()
                        }
                    }
                ) {
                    Text("Sync Clipboard")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClipSyncTheme {
        Greeting("Android")
    }
}