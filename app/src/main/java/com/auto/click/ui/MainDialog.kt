package com.auto.click.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.auto.click.data.script.mode.Script
import com.auto.click.data.script.mode.ScriptPoint
import com.auto.click.ui.common.CheckboxAndText
import com.auto.click.ui.common.DeleteDialog
import com.auto.click.ui.common.HeightSpace
import com.auto.click.ui.common.RequiredOutlinedTextField
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.compose.koinInject

/**
 * @description
 * @author 杨帅林
 * @create 2025/1/6 14:47
 **/
@Composable
fun MainDialog(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.isShowAddDialog || uiState.selectedScript != null) {
        AddOrEditDialog(script = uiState.selectedScript, onDismiss = {
            viewModel.hideAddDialog()
            viewModel.setSelectedScript(null)
        }, onDelete = { viewModel.deleteScript(it) }, onUpsert = { viewModel.onUpsertScript(it) })
    }
}

@Composable
private fun AddOrEditDialog(
    script: Script?,
    onDismiss: () -> Unit,
    onDelete: (Script) -> Unit,
    onUpsert: (Script) -> Unit
) {
    var name by remember {
        mutableStateOf(script?.name ?: "")
    }
    var points by remember {
        mutableStateOf(script?.points ?: "")
    }
    var delay by remember {
        mutableStateOf(script?.delay?.toString() ?: "1000")
    }
    var duration by remember {
        mutableStateOf(script?.duration?.toString() ?: "500")
    }
    var numberOfRuns by remember {
        mutableStateOf(script?.numberOfRuns?.toString() ?: "0")
    }
    var isRandomLocation by remember {
        mutableStateOf(script?.isRandomLocation ?: false)
    }
    var isRandomDelay by remember {
        mutableStateOf(script?.isRandomDelay ?: false)
    }
    var isRandomDuration by remember {
        mutableStateOf(script?.isRandomDuration ?: false)
    }
    var isShowCollectCoordinatesDialog by remember {
        mutableStateOf(false)
    }
    if (isShowCollectCoordinatesDialog) {
        CollectCoordinatesDialog(onFinish = {
            points = it
            isShowCollectCoordinatesDialog = false
        })
    }
    AlertDialog(onDismissRequest = {}, title = {
        Text(text = if (script == null) "添加脚本" else "编辑脚本")
    }, text = {
        Column {
            RequiredOutlinedTextField(
                hint = "脚本名称",
                isRequired = true,
                value = name,
                onValueChanged = { name = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            RequiredOutlinedTextField(
                hint = "脚本坐标",
                isRequired = true,
                value = points,
                onValueChanged = { points = it },
                trailingIcon = {
                    IconButton(onClick = {
                        isShowCollectCoordinatesDialog = true
                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                },
                readOnly = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            RequiredOutlinedTextField(
                hint = "延时",
                isRequired = true,
                value = delay,
                onValueChanged = { delay = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            )
            RequiredOutlinedTextField(
                hint = "持续时间",
                isRequired = true,
                value = duration,
                onValueChanged = { duration = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            )
            RequiredOutlinedTextField(
                hint = "运行次数",
                isRequired = true,
                value = numberOfRuns,
                onValueChanged = { numberOfRuns = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            )
            HeightSpace(5.dp)
            CheckboxAndText(text = "随机位置", checked = isRandomLocation) {
                isRandomLocation = it
            }
            HeightSpace(5.dp)
            CheckboxAndText(text = "随机延时", checked = isRandomDelay) {
                isRandomDelay = it
            }
            HeightSpace(5.dp)
            CheckboxAndText(text = "随机持续时间", checked = isRandomDuration) {
                isRandomDuration = it
            }
        }
    }, confirmButton = {
        Button(
            enabled = name.isNotEmpty() && points.isNotEmpty() && delay.isNotEmpty() && duration.isNotEmpty() && numberOfRuns.isNotEmpty(),
            onClick = {
                onUpsert(
                    Script(
                        id = script?.id,
                        name = name,
                        points = points,
                        delay = delay.toLong(),
                        duration = duration.toLong(),
                        numberOfRuns = numberOfRuns.toInt(),
                        isRandomLocation = isRandomLocation,
                        isRandomDelay = isRandomDelay,
                        isRandomDuration = isRandomDuration,
                    )
                )
            }) {
            Text(text = "保存")
        }
    }, dismissButton = {
        Row {
            script?.let {
                var isShowDeleteDialog by remember {
                    mutableStateOf(false)
                }
                if (isShowDeleteDialog) {
                    DeleteDialog(
                        content = "请确认是否要删除该人脸信息",
                        onDismissRequest = { isShowDeleteDialog = false },
                        onConfirmClick = {
                            onDelete(it)
                            onDismiss()
                        })
                }
                TextButton(onClick = {
                    isShowDeleteDialog = true
                }, colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)) {
                    Text(text = "删除")
                }
            }
            Spacer(modifier = Modifier.width(100.dp))
            TextButton(onClick = onDismiss) {
                Text(text = "取消")
            }
        }
    })
}

@Composable
private fun CollectCoordinatesDialog(onFinish: (String) -> Unit, json: Json = koinInject()) {
    Dialog(onDismissRequest = {}, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        // 存储滑动起点和终点的坐标
        val startPoint = remember { mutableStateOf<Offset?>(null) }
        val endPoint = remember { mutableStateOf<Offset?>(null) }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            startPoint.value = it
                        },
                        onDrag = { change, _ ->
                            endPoint.value = change.position
                        },
                        onDragEnd = {
                            val list = mutableListOf<ScriptPoint>()
                            startPoint.value?.let {
                                list.add(ScriptPoint(it.x, it.y))
                            }
                            endPoint.value?.let {
                                list.add(ScriptPoint(it.x, it.y))
                            }
                            onFinish(json.encodeToString(list))
                        },
                        onDragCancel = {}
                    )
                },
        )
    }
}