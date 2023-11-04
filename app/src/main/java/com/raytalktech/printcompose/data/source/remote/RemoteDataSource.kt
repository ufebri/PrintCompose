package com.raytalktech.printcompose.data.source.remote

class RemoteDataSource {

    companion object {
        private const val TAG = "RemoteDataSource"


        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

}