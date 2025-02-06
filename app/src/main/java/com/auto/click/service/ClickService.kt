package com.auto.click.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.view.accessibility.AccessibilityEvent
import com.auto.click.data.script.mode.Script
import com.auto.click.data.script.mode.ScriptPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

/**
 * @description
 * @author 杨帅林
 * @create 2025/1/6 10:04
 **/
class ClickService: AccessibilityService() {
    override fun onServiceConnected() {
        super.onServiceConnected()
        service = this
        _serviceState.update { true }
    }
    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {

    }

    override fun onInterrupt() {
        service = null
        _serviceState.update { false }
    }

    override fun onDestroy() {
        super.onDestroy()
        service = null
        _serviceState.update { false }
    }

    fun slide(script: Script, scriptPointList:List<ScriptPoint>, onCompleted: () -> Unit = {}){
        if (scriptPointList.size != 2) return

        val path = Path()
        val startX: Float
        val startY: Float
        val endX: Float
        val endY: Float
        //随机位置
        if (script.isRandomLocation) {
            val random = Random.Default
            val randomOffsetX = random.nextInt(-51, 51).toFloat()
            val randomOffsetY = random.nextInt(-51, 51).toFloat()

            startX = scriptPointList[0].x + randomOffsetX
            startY = scriptPointList[0].y + randomOffsetY
            endX = scriptPointList[1].x + randomOffsetX
            endY = scriptPointList[1].y + randomOffsetY
        } else {
            startX = scriptPointList[0].x
            startY = scriptPointList[0].y
            endX = scriptPointList[1].x
            endY = scriptPointList[1].y
        }
        path.moveTo(startX, startY)
        path.lineTo(endX, endY)
        //随机持续时间
        val description = GestureDescription.StrokeDescription(path,0,if (script.isRandomDuration) Random.nextLong(script.duration-51,script.duration+51) else script.duration)
        dispatchGesture(GestureDescription.Builder().addStroke(description).build(),object : GestureResultCallback() {
            override fun onCompleted(gestureDescription: GestureDescription?) {
                super.onCompleted(gestureDescription)
                onCompleted()
            }
        },null)
    }

    companion object{
        var service:ClickService? = null
        private val _serviceState = MutableStateFlow(false)
        val serviceState = _serviceState.asStateFlow()
    }
}