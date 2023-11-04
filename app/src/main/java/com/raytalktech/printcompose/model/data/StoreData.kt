package com.raytalktech.printcompose.model.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "storeData")
@Parcelize
data class StoreData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idBrand") val idBrand: Long? = null,
    @ColumnInfo(name = "imgBrand") val imgBrand: String? = null,
    @ColumnInfo(name = "nameBrand") val nameBrand: String? = null,
    @ColumnInfo(name = "addressBrand") val addressBrand: String? = null,
    @ColumnInfo(name = "telpBrand") val telpBrand: String? = null,
    @ColumnInfo(name = "emailBrand") val emailBrand: String? = null,
) : Parcelable