package com.auto.click.ui.floatingWindow

import android.app.Application
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import com.auto.click.data.script.mode.Script
import com.auto.click.data.script.mode.ScriptPoint
import com.auto.click.service.ClickService
import com.auto.click.ui.theme.AutoClickTheme
import com.petterp.floatingx.FloatingX
import com.petterp.floatingx.assist.FxScopeType
import com.petterp.floatingx.compose.enableComposeSupport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.compose.koinInject
import org.koin.core.annotation.Single
import kotlin.coroutines.cancellation.CancellationException
import kotlin.random.Random

/**
 * @description
 * @author 杨帅林
 * @create 2025/1/6 11:37
 **/
@Single
class RunningControlWindow(private val context: Application) {
    fun show(script: Script) {
        if (!FloatingX.isInstalled(script.id.toString())) {
            FloatingX.install {
                setContext(context)
                setTag(script.id.toString())
                enableComposeSupport()
                setEnableEdgeAdsorption(false)
                setScopeType(FxScopeType.SYSTEM_AUTO)
                setLayoutView(
                    ComposeView(context).apply {
                        setContent {
                            AutoClickTheme {
                                RunningControlWindow(script)
                            }
                        }
                    }
                )
            }
        }
        FloatingX.control(script.id.toString()).show()
    }

    fun hide(tagId: String) {
        FloatingX.control(tagId).hide()
    }

    fun cancel(tagId: String) {
        FloatingX.control(tagId).cancel()
    }
}

@Composable
private fun RunningControlWindow(script: Script, json: Json = koinInject(),runningControlWindow: RunningControlWindow = koinInject()) {
    val points = remember {
        json.decodeFromString<List<ScriptPoint>>(script.points)
    }

    var job by remember { mutableStateOf<Job?>(null) }

    val isRunning by remember {
        androidx.compose.runtime.derivedStateOf { job?.isActive == true }
    }

    val scope = rememberCoroutineScope()

    OutlinedCard(
        colors = CardDefaults.outlinedCardColors(CardDefaults.outlinedCardColors().containerColor.copy(alpha = 0.3f)),
        onClick = {
            if (isRunning) {
                stopScript(job) { job = null }
            } else {
                startScript(script, points,scope, onStart = {
                    job = it
                }) { job = null }
            }
        }
    ) {
        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = if (isRunning) Icons.Default.Stop else Icons.Default.PlayArrow, contentDescription = null)
            Text(text = script.name)
            IconButton(onClick = {
                runningControlWindow.cancel(script.id.toString())
            }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        }
    }
}

private fun stopScript(job: Job?, onStop: () -> Unit) {
    job?.cancel()
    onStop()
}

private fun startScript(
    script: Script,
    points: List<ScriptPoint>,
    scope: CoroutineScope,
    onStart: (Job) -> Unit,
    onCompleted: () -> Unit = {},
    onError: (Throwable) -> Unit = {}
) {
    val newJob = scope.launch(Dispatchers.IO) {
        try {
            runScriptLogic(script, points)
            onCompleted()
        } catch (e: CancellationException) {
            println("Job Cancelled.")
        }catch (e: Exception) {
            onError(e)
        }
    }
    onStart(newJob)
}

private suspend fun CoroutineScope.runScriptLogic(script: Script, points: List<ScriptPoint>) {
    val delay = if (script.isRandomDelay) Random.nextLong(script.delay - 51, script.delay + 51) else script.delay

    if (script.numberOfRuns == 0) {
        while (isActive) {
            delay(delay)
            executeScriptAction(script, points)
        }
    } else {
        repeat(script.numberOfRuns) {
            delay(delay)
            executeScriptAction(script,points)
        }
    }
}


private fun executeScriptAction(script: Script, points: List<ScriptPoint>) {
    ClickService.service?.slide(script = script, scriptPointList = points)
}

