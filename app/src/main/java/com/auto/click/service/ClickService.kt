package com.auto.click.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.view.accessibility.AccessibilityEvent
import com.auto.click.data.script.mode.Script
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

    fun slide(onCompleted: () -> Unit){
        val path = Path()
        path.moveTo(200f,300f)
        path.lineTo(200f,1000f)
        val description = GestureDescription.StrokeDescription(path,0,500)
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