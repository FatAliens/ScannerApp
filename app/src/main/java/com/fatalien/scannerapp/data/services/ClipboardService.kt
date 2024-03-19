package com.fatalien.scannerapp.data.services

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ClipboardService @Inject constructor(@ApplicationContext _context: Context) {
    private val _clipboardManager : ClipboardManager = _context.getSystemService(ClipboardManager::class.java)
    fun onClipboardCopy(emit: (String) -> Unit) {
        _clipboardManager.addPrimaryClipChangedListener {
            val clipboardAsText = _clipboardManager.primaryClip?.getItemAt(0)?.text
            if (clipboardAsText != null) {
                val result = clipboardAsText.toString().trim().uppercase()
                Log.d("CLIPBOARD", result)
                emit(result)
            }
        }
    }

    fun setClipboard(text: String){
        _clipboardManager.setPrimaryClip(ClipData.newPlainText("QR", text))
    }
}