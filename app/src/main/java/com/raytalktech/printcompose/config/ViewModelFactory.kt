package com.raytalktech.printcompose.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raytalktech.printcompose.data.DataRepository
import com.raytalktech.printcompose.ui.screens.add.DetailPersonOrdersViewModel
import com.raytalktech.printcompose.ui.screens.admin.AdminViewModel
import com.raytalktech.printcompose.ui.screens.home.HomeViewModel
import com.raytalktech.printcompose.ui.screens.neworder.NewOrderViewModel

class ViewModelFactory(private val repository: DataRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailPersonOrdersViewModel::class.java)) {
            return DetailPersonOrdersViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(NewOrderViewModel::class.java)) {
            return NewOrderViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AdminViewModel::class.java)) {
            return AdminViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}