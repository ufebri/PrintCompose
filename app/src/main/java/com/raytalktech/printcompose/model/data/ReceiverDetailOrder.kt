package com.raytalktech.printcompose.model.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "receiverDetailOrder")
data class ReceiverDetailOrder(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idDetailOrder") val idDetailOrder: Long? = null,
    @ColumnInfo(name = "nameItemOrder") val nameItemOrder: String,
    @ColumnInfo(name = "unitItemOrder") val unitItemOrder: String,
    @ColumnInfo(name = "quantityItemOrder") val quantityItemOrder: Int,
    @ColumnInfo(name = "priceItemOrder") val priceItemOrder: Int,
    @ColumnInfo(name = "amountItemOrder") val amountItemOrder: Int,
    @ColumnInfo(name = "idReceiverOrder") val idReceiverOrder: Long
) : Parcelable