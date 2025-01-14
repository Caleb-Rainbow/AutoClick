package com.auto.click.ui.floatingWindow

import android.app.Application
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.auto.click.service.ClickService
import com.auto.click.ui.theme.AutoClickTheme
import com.petterp.floatingx.FloatingX
import com.petterp.floatingx.assist.FxScopeType
import com.petterp.floatingx.compose.enableComposeSupport
import org.koin.core.annotation.Single

/**
 * @description
 * @author 杨帅林
 * @create 2025/1/6 11:37
 **/
@Single
class RunningControlWindow (private val context:Application){
    private val tag = "RunningControlWindow"
    fun show(){
        if (!FloatingX.isInstalled(tag)){
            FloatingX.install {
                setContext(context)
                setTag(tag)
                enableComposeSupport()
                setScopeType(FxScopeType.SYSTEM_AUTO)
                setLayoutView(
                    ComposeView(context).apply {
                        setContent {
                            AutoClickTheme {
                                RunningControlWindow()
                            }
                        }
                    }
                )
            }
        }
        FloatingX.control(tag).show()
    }
    fun hide(){
        FloatingX.control(tag).hide()
    }

    fun cancel(){
        FloatingX.control(tag).cancel()
    }
}
@Composable
private fun RunningControlWindow() {
    var isRunning by remember {
        mutableStateOf(false)
    }
    OutlinedCard(onClick = {
        isRunning = !isRunning
        ClickService.service?.slide {
            isRunning = false
        }
        //todo 控制服务启停
    }) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = if(isRunning)Icons.Default.Stop else Icons.Default.PlayArrow ,contentDescription = null)
            Text(text = if (isRunning) "暂停" else "开始")
        }
    }
}