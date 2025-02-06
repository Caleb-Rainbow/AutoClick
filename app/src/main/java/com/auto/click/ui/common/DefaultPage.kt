package com.auto.click.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.auto.click.R

/**
 * @description 缺省页-空数据
 * @param modifier 总体布局修饰符
 * @param imageModifier 图片修饰符
 * @param title 描述文字
 **/
@Composable
fun EmptyDataLayout(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    title: String = "暂无数据"
) {
    BaseDataLayout(
        modifier = modifier,
        imageModifier = imageModifier,
        title = title,
        imageUrl =  R.drawable.image_empty
    )
}
/**
 * @description 缺省页-加载失败
 * @param modifier 总体布局修饰符
 * @param imageModifier 图片修饰符
 * @param title 描述文字
 * */
@Composable
fun ErrorDataLayout(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    title: String = "加载失败"
) {
    BaseDataLayout(
        modifier = modifier,
        imageModifier = imageModifier,
        title = title,
        imageUrl = R.drawable.image_404
    )
}

/**
 * @description 缺省页-加载中
 * @param modifier 总体布局修饰符
 * */
@Composable
fun LoadingDataLayout(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun BaseDataLayout(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    title: String,
    imageUrl: Any
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        CommonImage(
            modifier = imageModifier,
            imageUrl = imageUrl
        )
        Text(text = title, color = Color.Gray)
    }
}


