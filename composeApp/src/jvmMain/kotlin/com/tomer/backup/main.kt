package com.tomer.backup

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.tomer.backup.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Samba Backup",
    ) {
        App()
    }
}