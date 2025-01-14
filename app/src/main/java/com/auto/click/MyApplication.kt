package com.auto.click

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

/**
 * @description
 * @author 杨帅林
 * @create 2025/1/6 11:01
 **/
class MyApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(KoinModule().module)
        }
    }
}