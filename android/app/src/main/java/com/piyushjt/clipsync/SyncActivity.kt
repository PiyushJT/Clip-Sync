package com.piyushjt.clipsync

import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class SyncActivity : ComponentActivity() {
    private var syncStarted = false

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && !syncStarted) {
            syncStarted = true
            val repository = ClipboardRepository(this)
            lifecycleScope.launch {
                repository.syncClipboard()
                finish()
            }
        }
    }
}
