package com.raytalktech.printcompose.data.source

import com.raytalktech.printcompose.model.data.ReceiverDetailOrder
import com.raytalktech.printcompose.model.data.ReceiverOrder
import com.raytalktech.printcompose.model.data.StoreData
import kotlinx.coroutines.flow.Flow

interface DataSource {

    /* All Person Order */
    fun getReceiverOrderList(): Flow<List<ReceiverOrder>>
    fun createReceiverOrder(order: ReceiverOrder)
    fun deleteReceiverOrder(order: ReceiverOrder)

    /* Detail Person Order */
    fun getDetailPersonOrders(id: Long): Flow<List<ReceiverDetailOrder>>
    fun getDetailItemOrder(idOrderItem: Long): Flow<ReceiverDetailOrder>
    fun createPersonOrder(order: ReceiverDetailOrder)
    fun deletePersonOrder(order: ReceiverDetailOrder)
    fun updatePersonOrder(order: ReceiverDetailOrder)

    /* Store Data */
    fun getStoreData(): Flow<List<StoreData>>
    fun createStoreData(storeData: StoreData)
    fun updateStoreData(storeData: StoreData)
}