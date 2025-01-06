package com.auto.click.data.script

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.auto.click.data.script.mode.Script
import kotlinx.coroutines.flow.Flow

/**
 * @description
 * @author 杨帅林
 * @create 2025/1/6 13:59
 **/
@Dao
interface ScriptDao {
    @Upsert
    suspend fun upsert(script: Script)
    @Delete
    suspend fun delete(script: Script)
    @Query("SELECT * FROM Script")
    fun getAllFlow(): Flow<List<Script>>
    @Query("SELECT * FROM Script")
    fun pagingSource():PagingSource<Int,Script>
}