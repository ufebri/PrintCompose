package com.raytalktech.printcompose.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.raytalktech.printcompose.model.data.ReceiverDetailOrder
import com.raytalktech.printcompose.model.data.ReceiverOrder
import com.raytalktech.printcompose.model.data.StoreData
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    /* Receiver Order */
    @Query("SELECT * FROM receiverOrder")
    fun getOrderReceiverList(): Flow<List<ReceiverOrder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createOrderReceiver(data: ReceiverOrder)

    @Delete
    fun deleteOrderReceiver(data: ReceiverOrder)

    /* Detail Person Order*/
    @Query("SELECT * FROM receiverDetailOrder WHERE idReceiverOrder = :id")
    fun getDetailPersonOrders(id: Long): Flow<List<ReceiverDetailOrder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createPersonOrder(order: ReceiverDetailOrder)

    @Delete
    fun deletePersonOrder(order: ReceiverDetailOrder)

    @Update
    fun updatePersonOrder(order: ReceiverDetailOrder)

    @Query("SELECT * FROM receiverDetailOrder WHERE idDetailOrder = :idOrder")
    fun getDetailItemOrder(idOrder: Long): Flow<ReceiverDetailOrder>

    /* Store */
    @Query("SELECT * FROM storeData")
    fun getStoreData(): Flow<List<StoreData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createStoreData(data: StoreData)

    @Update
    fun updateStoreData(data: StoreData)
}