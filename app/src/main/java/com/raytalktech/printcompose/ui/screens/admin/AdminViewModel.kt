package com.raytalktech.printcompose.ui.screens.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raytalktech.printcompose.R
import com.raytalktech.printcompose.data.DataRepository
import com.raytalktech.printcompose.data.UiState
import com.raytalktech.printcompose.model.data.AdminData
import com.raytalktech.printcompose.model.data.StoreData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AdminViewModel(private val repository: DataRepository) : ViewModel() {

    /* Get Admin Data */
    private val _uiState: MutableStateFlow<UiState<List<StoreData>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<StoreData>>> get() = _uiState

    fun getStoreData() {
        viewModelScope.launch {
            repository.getStoreData().catch {
                _uiState.value = UiState.Error(it.message.toString())
            }.collect { storeData -> _uiState.value = UiState.Success(storeData) }
        }
    }

    fun createStoreData(
        nameBrand: String,
        telpBrand: String,
        emailBrand: String,
        addressBrand: String
    ) =
        repository.createStoreData(
            StoreData(
                idBrand = 1,
                nameBrand = nameBrand,
                telpBrand = telpBrand,
                emailBrand = emailBrand,
                addressBrand = addressBrand
            )
        )

    fun getAdminMenuList(): List<AdminData> = listOf(
        AdminData(1, R.drawable.ic_store, "My Store", "Set Store Information"),
        AdminData(2, R.drawable.ic_info, "Version", "v1.0")
    )
}