package com.auto.click.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.auto.click.data.script.mode.Script
import com.auto.click.ui.common.LazyPagingListState
import com.auto.click.ui.theme.AutoClickTheme
import com.auto.click.util.SystemUtil
import org.koin.androidx.compose.koinViewModel

/**
 * @description
 * @author 杨帅林
 * @create 2025/1/6 10:53
 **/
@Composable
fun MainScreen(modifier: Modifier, viewModel: MainViewModel = koinViewModel()) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            viewModel.showAddDialog()
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }) { paddingValues ->
        MainDialog(viewModel)
        Column(modifier = modifier.padding(paddingValues)) {
            val serviceState by viewModel.serviceState.collectAsStateWithLifecycle()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val scriptList = uiState.scripts?.collectAsLazyPagingItems()
            PermissionCheck(modifier = Modifier.align(Alignment.CenterHorizontally), serviceState = serviceState)
            if (serviceState) {
                scriptList?.let {
                    ScriptList(list = it, onClick = { selectedScript ->
                        viewModel.setSelectedScript(selectedScript)
                    }) { script ->
                        viewModel.showRunningControlWindow(script)
                    }
                }
            }
        }
    }
}

@Composable
private fun PermissionCheck(modifier: Modifier, serviceState: Boolean) {
    val context = LocalContext.current
    if (!serviceState) {
        Button(modifier = modifier, onClick = {
            SystemUtil.goAccessibilitySetting(context = context)
        }) {
            Text(text = "申请无障碍权限")
        }
    } else {
        var isShow by remember {
            mutableStateOf(true)
        }
        if (isShow) OutlinedCard(modifier = modifier) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(modifier = Modifier.padding(10.dp), text = "无障碍权限已开启")
                Spacer(modifier = Modifier.width(20.dp))
                IconButton(onClick = {
                    isShow = false
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            }
        }
    }
}

@Composable
private fun ScriptList(list: LazyPagingItems<Script>, onClick: (Script) -> Unit, showScript: (Script) -> Unit) {
    val context = LocalContext.current
    OutlinedCard(
        modifier = Modifier.padding(horizontal = 15.dp)
    ) {
        LazyPagingListState(list = list) {
            LazyColumn {
                items(list.itemCount) { index ->
                    list[index]?.let {
                        ScriptItem(script = it, onClick = {
                            onClick(it)
                        }, showScript = {
                            showScript(it)
                        })
                    }
                    if (index != list.itemCount - 1) {
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ScriptItem(script: Script, onClick: () -> Unit, showScript: () -> Unit) {
    Row(modifier = Modifier
        .clickable {
            onClick()
        }
        .padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.weight(1f), text = script.name)
        TextButton(onClick = {
            showScript()
        }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "运行")
            }
        }
    }
}