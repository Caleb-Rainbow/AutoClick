package com.auto.click.util

import android.content.Context
import android.content.Intent
import android.provider.Settings


/**
 * @description
 * @author 杨帅林
 * @create 2025/1/6 10:15
 **/
object SystemUtil {

    fun goAccessibilitySetting(context: Context){
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}