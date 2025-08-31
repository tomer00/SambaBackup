package com.tomer.backup.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.awtTransferable
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import sambabackup.composeapp.generated.resources.Res
import sambabackup.composeapp.generated.resources.file
import sambabackup.composeapp.generated.resources.folder
import sambabackup.composeapp.generated.resources.font
import java.awt.datatransfer.DataFlavor
import java.io.File


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SourcesScreen() {
    var isHovering by remember { mutableStateOf(false) }
    val listFiles = remember { mutableStateListOf<File>() }
    val listFolders = remember { mutableStateListOf<File>() }
    val target = remember {
        object : DragAndDropTarget {
            override fun onEntered(event: DragAndDropEvent) {
                isHovering = true
            }

            override fun onExited(event: DragAndDropEvent) {
                isHovering = false
            }

            override fun onEnded(event: DragAndDropEvent) {
                isHovering = false
            }

            override fun onDrop(event: DragAndDropEvent): Boolean {
                val t = event.awtTransferable
                if (!t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) return false

                val files = (t.getTransferData(DataFlavor.javaFileListFlavor) as? List<*>)
                    ?.filterIsInstance<File>().orEmpty()
                println("Dropped directories: ${files.joinToString { it.absolutePath }}")
                for (file in files) {
                    if (listFolders.none { file.absolutePath.startsWith(it.absolutePath) }) {
                        if (file.isFile) listFiles.add(file)
                        else if (file.isDirectory) {
                            val iFiles = listFiles.iterator()
                            while (iFiles.hasNext()) {
                                val i = iFiles.next()
                                if (i.absolutePath.startsWith(file.absolutePath))
                                    iFiles.remove()
                            }
                            val iFolders = listFolders.iterator()
                            while (iFolders.hasNext()) {
                                val i = iFolders.next()
                                if (i.absolutePath.startsWith(file.absolutePath))
                                    iFolders.remove()
                            }
                            listFolders.add(file)
                        }
                    }
                }
                listFolders.sort()
                listFiles.sort()
                return true
            }
        }
    }
    Box(
        Modifier.fillMaxSize()
            .dragAndDropTarget(
                shouldStartDragAndDrop = { true }, target
            )
    ) {
        //region Main Flow layout
        FlowRow(Modifier.fillMaxSize()) {
            listFolders.forEach {
                FileItem(it)
            }
            listFiles.forEach {
                FileItem(it)
            }
        }
        //endregion Main Flow layout
        //region DragAndDropUI

        if (isHovering)
            Box(
                Modifier.fillMaxSize()
                    .background(Color.White.copy(.4f))
                    .padding(8.dp)
                    .drawBehind {
                        val stroke = Stroke(
                            width = 4f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f), 0f)
                        )
                        drawRoundRect(
                            color = Color.Black.copy(.6f),
                            size = Size(size.width, size.height),
                            cornerRadius = CornerRadius(12.dp.toPx()),
                            style = stroke
                        )
                    },
                Alignment.Center
            ) {
                Text(
                    text = "Drop files here",
                    color = Color.Black.copy(.6f),
                    fontSize = 52.sp,
                    fontFamily = Font(Res.font.font).toFontFamily(),
                    modifier = Modifier.wrapContentSize()
                )
            }

        //endregion DragAndDropUI
    }
}

@Composable
fun FileItem(file: File) {
    val icon: Painter = if (file.isDirectory)
        painterResource(Res.drawable.folder)
    else painterResource(Res.drawable.file)


    Column(
        modifier = Modifier
            .width(140.dp)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = icon,
            contentDescription = if (file.isDirectory) "Folder" else "File",
            modifier = Modifier.size(80.dp)
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = file.name,
            maxLines = 1,
            color = Color.Black,
            modifier = Modifier.basicMarquee()
        )
    }
}
