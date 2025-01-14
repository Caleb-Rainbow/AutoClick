package com.auto.click.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

/**
 * @description 列表状态的封装
 * @author 杨帅林
 * @create 2024/11/8 9:30
 **/
/**
 * 分页列表状态封装
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any> LazyPagingListState(
    modifier: Modifier = Modifier,
    list: LazyPagingItems<T>?,
    content: @Composable (LazyPagingItems<T>) -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()
    list?.let {
        val state = list.loadState
        PullToRefreshBox(
            modifier = modifier,
            state = pullToRefreshState,
            isRefreshing = state.refresh is LoadState.Loading,
            onRefresh = { list.refresh() },
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    state = pullToRefreshState,
                    isRefreshing = state.refresh is LoadState.Loading,
                )
            }) {
            if (state.refresh is LoadState.Loading) {
                LoadingDataLayout(modifier = modifier)
            } else if (state.hasError) {
                ErrorDataLayout(modifier = modifier)
            } else {
                if (list.itemCount == 0) {
                    EmptyDataLayout(modifier = modifier)
                } else {
                    content(list)
                }
            }
        }
    }
}