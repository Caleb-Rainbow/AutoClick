package com.auto.click.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.auto.click.util.SystemUtil
import org.koin.androidx.compose.koinViewModel

/**
 * @description
 * @author 杨帅林
 * @create 2025/1/6 10:53
 **/
@Composable
fun MainScreen(modifier: Modifier,viewModel: MainViewModel = koinViewModel()){
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            viewModel.showAddDialog()
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }) {paddingValues ->
        MainDialog(viewModel)
        Column(modifier = modifier.padding(paddingValues)) {
            val serviceState by viewModel.serviceState.collectAsStateWithLifecycle()
            PermissionCheck(modifier = Modifier.align(Alignment.CenterHorizontally),serviceState = serviceState)
            if (serviceState){
                Button(onClick = {
                    viewModel.showRunningControlWindow()
                }) {
                    Text(text = "启动")
                }
                Spacer(modifier = Modifier.height(5.dp))
                Button(onClick = {
                    viewModel.hideRunningControlWindow()
                }) {
                    Text(text = "关闭")
                }
            }
        }
    }
}

@Composable
private fun PermissionCheck(modifier: Modifier,serviceState:Boolean){
    val context = LocalContext.current
    if (!serviceState){
        Button(modifier = modifier,onClick = {
            SystemUtil.goAccessibilitySetting(context = context)
        }) {
            Text(text = "申请无障碍权限")
        }
    }else{
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