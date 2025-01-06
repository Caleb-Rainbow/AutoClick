package com.auto.click.data.script.mode

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @description
 * @author 杨帅林
 * @create 2025/1/6 13:57
 **/
@Entity
data class Script(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val name:String,
    /**序列化存储*/
    val points:String,
    /**延时  单位：毫秒*/
    val delay:Long,
    /**滑动持续时间  单位：毫秒*/
    val duration:Long,
    /**运行次数 0-无限 else-次数*/
    val numberOfRuns:Int,
    /**是否随机位置*/
    val isRandomLocation:Boolean,
    /**是否随机延时*/
    val isRandomDelay:Boolean,
    /**是否随机持续时间*/
    val isRandomDuration:Boolean
)
