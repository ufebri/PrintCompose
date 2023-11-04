package com.raytalktech.printcompose.data.source.local

import com.raytalktech.printcompose.model.data.ReceiverDetailOrder
import com.raytalktech.printcompose.model.data.ReceiverOrder
import com.raytalktech.printcompose.model.data.StoreData
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val appDao: AppDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(appDao: AppDao): LocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = LocalDataSource(appDao)
            }
            return INSTANCE as LocalDataSource
        }
    }

    fun getOrderReceiverList(): Flow<List<ReceiverOrder>> = appDao.getOrderReceiverList()
    fun createOrderReceiver(dataEntity: ReceiverOrder) = appDao.createOrderReceiver(dataEntity)
    fun deleteOrderReceiver(dataEntity: ReceiverOrder) = appDao.deleteOrderReceiver(dataEntity)

    fun getDetailPersonOrders(id: Long) = appDao.getDetailPersonOrders(id)
    fun getDetailItemOrder(idOrder: Long) = appDao.getDetailItemOrder(idOrder)
    fun createPersonOrder(order: ReceiverDetailOrder) = appDao.createPersonOrder(order)
    fun deletePersonOrder(order: ReceiverDetailOrder) = appDao.deletePersonOrder(order)
    fun updatePersonOrder(order: ReceiverDetailOrder) = appDao.updatePersonOrder(order)

    fun getStoreData(): Flow<List<StoreData>> = appDao.getStoreData()
    fun createStoreData(storeData: StoreData) = appDao.createStoreData(storeData)
    fun updateStoreData(storeData: StoreData) = appDao.updateStoreData(storeData)
}