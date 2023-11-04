package com.raytalktech.printcompose.di

import android.content.Context
import com.raytalktech.printcompose.config.AppExecutors
import com.raytalktech.printcompose.data.DataRepository
import com.raytalktech.printcompose.data.source.local.AppDatabase
import com.raytalktech.printcompose.data.source.local.LocalDataSource
import com.raytalktech.printcompose.data.source.remote.RemoteDataSource


object Injection {

    fun provideRepository(context: Context): DataRepository {
        val database = AppDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.appDao())
        val appExecutors = AppExecutors()
        return DataRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}