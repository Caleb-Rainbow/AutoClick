package com.auto.click.data.script

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.auto.click.data.script.mode.Script
import org.koin.core.annotation.Single

/**
 * @description
 * @author 杨帅林
 * @create 2025/1/6 14:00
 **/
@Single
class ScriptRepository(private val scriptDao: ScriptDao) {
    suspend fun upsert(script: Script) = scriptDao.upsert(script)
    suspend fun delete(script: Script) = scriptDao.delete(script)
    fun getAllFlow() = scriptDao.getAllFlow()
    fun pagingSource() = Pager(config = PagingConfig(pageSize = 20)){
        scriptDao.pagingSource()
    }

}