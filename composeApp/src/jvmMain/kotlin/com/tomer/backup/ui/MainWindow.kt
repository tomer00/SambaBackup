package com.tomer.backup.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun BackupToolUI() {
    val menuItems = listOf(
        "Sources",
        "Logs",
    )

    var selectedItem by remember { mutableStateOf("Sources") }

    Row(Modifier.fillMaxSize()) {
        // Sidebar
        Column(
            modifier = Modifier
                .width(220.dp)
                .fillMaxHeight()
                .background(Color(0xFF1E1E1E))
                .padding(12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                "ShadowSync",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )

            Spacer(Modifier.height(16.dp))

            menuItems.forEach { label ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            selectedItem = label
                        }
                        .background(if (selectedItem==label) Color.DarkGray else Color.Transparent)
                        .padding(12.dp)
                ) {
                    Text(label, color = Color.White, fontSize = 14.sp)
                }
                Spacer(Modifier.height(8.dp))
            }
        }

        // Main Content Area
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F4F4))
                .padding(16.dp)
        ) {
            when(selectedItem){
                "Logs"-> LogScreen()
                else -> SourcesScreen()
            }
        }
    }
}
