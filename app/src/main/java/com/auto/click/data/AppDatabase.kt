package com.auto.click.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.auto.click.data.script.ScriptDao
import com.auto.click.data.script.mode.Script

/**
 * @description
 * @author 杨帅林
 * @create 2025/1/6 13:58
 **/
@Database(
    entities =[Script::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase :RoomDatabase(){
    abstract fun scriptDao(): ScriptDao
}