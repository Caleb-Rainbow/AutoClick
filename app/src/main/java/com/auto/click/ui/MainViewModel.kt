package com.auto.click.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.auto.click.data.script.ScriptRepository
import com.auto.click.data.script.mode.Script
import com.auto.click.service.ClickService
import com.auto.click.ui.floatingWindow.RunningControlWindow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

/**
 * @description
 * @author 杨帅林
 * @create 2025/1/6 10:54
 **/
data class UiState(
    val scripts:Flow<PagingData<Script>>? = null,
    val isShowAddDialog:Boolean = false,
    val selectedScript:Script? = null
)
@KoinViewModel
class MainViewModel(
    private val scriptRepository: ScriptRepository,
    private val runningControlWindow: RunningControlWindow,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
):ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()
    val serviceState = ClickService.serviceState
    init {
        initScripts()
    }

    fun showRunningControlWindow(script: Script){
        runningControlWindow.show(script)
    }
    fun hideRunningControlWindow(script: Script){
        runningControlWindow.hide(script.id.toString())
    }
    fun cancelRunningControlWindow(script: Script){
        runningControlWindow.cancel(script.id.toString())
    }
    private fun initScripts(){
        viewModelScope.launch(ioDispatcher) {
            _uiState.update {
                it.copy(
                    scripts = scriptRepository.pagingSource().flow
                )
            }
        }
    }
    fun showAddDialog(){
        _uiState.update {
            it.copy(isShowAddDialog = true)
        }
    }
    fun hideAddDialog(){
        _uiState.update {
            it.copy(isShowAddDialog = false)
        }
    }
    fun setSelectedScript(script: Script?){
        _uiState.update {
            it.copy(selectedScript = script)
        }
    }
    fun deleteScript(script: Script){
        viewModelScope.launch(ioDispatcher) {
            scriptRepository.delete(script)
        }
    }
    fun onUpsertScript(script: Script){
        viewModelScope.launch(ioDispatcher) {
            scriptRepository.upsert(script)
        }
    }
}