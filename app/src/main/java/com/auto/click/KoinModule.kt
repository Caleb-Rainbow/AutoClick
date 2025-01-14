package com.auto.click

import android.app.Application
import androidx.room.Room
import com.auto.click.data.AppDatabase
import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

/**
 * @description
 * @author 杨帅林
 * @create 2025/1/6 11:00
 **/
@Module
@ComponentScan
class KoinModule {
    @Single
    fun provideAppDataBase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "database")
            .fallbackToDestructiveMigration().build()
    }

    @Single
    fun provideScriptDao(appDatabase: AppDatabase) = appDatabase.scriptDao()

    @Single
    fun provideJsonSerializer(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }
}