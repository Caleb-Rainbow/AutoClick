package com.auto.click.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

@Composable
fun DeleteDialog(
    title:String = "删除",
    content:String = "请确认是否要删除",
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
    initialDelay: Int = 1
) {
    var countDown by remember { mutableIntStateOf(initialDelay) }
    var isButtonEnabled by remember { mutableStateOf(false) }
    LaunchedEffect(countDown) {
        if (countDown > 0) {
            delay(1000)
            countDown--
        } else {
            isButtonEnabled = true
        }
    }
    AlertDialog(onDismissRequest = onDismissRequest, title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text =title)
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color.Red
            )
        }
    }, text = {
        Text(text = content)
    }, confirmButton = {
        TextButton(enabled = isButtonEnabled, onClick = onConfirmClick) {
            Text(
                text = if (isButtonEnabled) "确认" else "确认(${countDown}秒)",
                color = if (isButtonEnabled) Color.Red else Color.Gray
            )
        }
    }, dismissButton = {
        TextButton(onClick = onDismissRequest) {
            Text(text = "取消")
        }
    })
}

@Composable
fun ConfirmDialog(
    title:String = "确认",
    content:String = "请确认是否要继续操作",
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
){
    AlertDialog(onDismissRequest = onDismissRequest, title = {
        Text(text =title)
    }, text = {
        Text(text = content)
    }, confirmButton = {
        TextButton( onClick = onConfirmClick) {
            Text(text =  "确认")
        }
    }, dismissButton = {
        TextButton(onClick = onDismissRequest) {
            Text(text = "取消", color = Color.Gray)
        }
    })
}

