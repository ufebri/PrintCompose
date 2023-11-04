package com.raytalktech.printcompose.ui.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raytalktech.printcompose.data.DataRepository
import com.raytalktech.printcompose.data.UiState
import com.raytalktech.printcompose.model.data.ReceiverDetailOrder
import com.raytalktech.printcompose.model.data.StoreData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailPersonOrdersViewModel(private val repository: DataRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<ReceiverDetailOrder>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<ReceiverDetailOrder>>> get() = _uiState

    fun getListOrderItems(id: Long) {
        viewModelScope.launch {
            repository.getDetailPersonOrders(id).catch {
                _uiState.value = UiState.Error(it.message.toString())
            }.collect { personOrders ->
                _uiState.value = UiState.Success(personOrders)
            }
        }
    }
}