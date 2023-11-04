package com.raytalktech.printcompose.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raytalktech.printcompose.data.DataRepository
import com.raytalktech.printcompose.data.UiState
import com.raytalktech.printcompose.model.data.ReceiverOrder
import com.raytalktech.printcompose.util.TextFormattingHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: DataRepository) : ViewModel() {

    /* Get Receiver List Order */
    private val _uiState: MutableStateFlow<UiState<List<ReceiverOrder>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<ReceiverOrder>>>
        get() = _uiState

    fun getReceiverData() {
        viewModelScope.launch {
            repository.getReceiverOrderList().catch {
                _uiState.value = UiState.Error(it.message.toString())
            }.collect { userData -> _uiState.value = UiState.Success(userData) }
        }
    }

    /* Insert Data */
    fun createReceiverOrder(receiverName: String) {
        val mData = ReceiverOrder(
            receiverName = receiverName.ifEmpty {
                String.format(
                    "Unnamed %s",
                    TextFormattingHelper.getCurrentDate()
                )
            },
            receiverLastUpdate = TextFormattingHelper.getCurrentDate()
        )
        repository.createReceiverOrder(mData)
    }
}