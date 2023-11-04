package com.raytalktech.printcompose.data

import com.raytalktech.printcompose.config.AppExecutors
import com.raytalktech.printcompose.data.source.DataSource
import com.raytalktech.printcompose.data.source.local.LocalDataSource
import com.raytalktech.printcompose.data.source.remote.RemoteDataSource
import com.raytalktech.printcompose.model.data.ReceiverDetailOrder
import com.raytalktech.printcompose.model.data.ReceiverOrder
import com.raytalktech.printcompose.model.data.StoreData
import kotlinx.coroutines.flow.Flow

class DataRepository private constructor(
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
    private val remoteDataSource: RemoteDataSource
) : DataSource {

    override fun getReceiverOrderList(): Flow<List<ReceiverOrder>> =
        localDataSource.getOrderReceiverList()

    override fun createReceiverOrder(order: ReceiverOrder) =
        appExecutors.diskIO().execute { localDataSource.createOrderReceiver(order) }

    override fun deleteReceiverOrder(order: ReceiverOrder) =
        appExecutors.diskIO().execute { localDataSource.deleteOrderReceiver(order) }

    override fun getDetailPersonOrders(id: Long): Flow<List<ReceiverDetailOrder>> =
        localDataSource.getDetailPersonOrders(id)

    override fun getDetailItemOrder(idOrderItem: Long): Flow<ReceiverDetailOrder> =
        localDataSource.getDetailItemOrder(idOrderItem)

    override fun createPersonOrder(order: ReceiverDetailOrder) =
        appExecutors.diskIO().execute { localDataSource.createPersonOrder(order) }

    override fun deletePersonOrder(order: ReceiverDetailOrder) =
        appExecutors.diskIO().execute { localDataSource.deletePersonOrder(order) }

    override fun updatePersonOrder(order: ReceiverDetailOrder) =
        appExecutors.diskIO().execute { localDataSource.updatePersonOrder(order) }

    override fun getStoreData(): Flow<List<StoreData>> = localDataSource.getStoreData()

    override fun createStoreData(storeData: StoreData) =
        appExecutors.diskIO().execute { localDataSource.createStoreData(storeData) }

    override fun updateStoreData(storeData: StoreData) =
        appExecutors.diskIO().execute { localDataSource.updateStoreData(storeData) }

    companion object {

        @Volatile
        private var instance: DataRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository(
                    localDataSource,
                    appExecutors,
                    remoteData
                )
            }
    }
}