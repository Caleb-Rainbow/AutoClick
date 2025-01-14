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
        path.moveTo(scriptPointList[0].x,scriptPointList[0].y)
        path.lineTo(scriptPointList[1].x,scriptPointList[1].y)
        val description = GestureDescription.StrokeDescription(path,0,script.duration)
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