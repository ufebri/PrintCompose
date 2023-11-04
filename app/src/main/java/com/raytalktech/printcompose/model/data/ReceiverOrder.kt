package com.raytalktech.printcompose.model.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "receiverOrder")
data class ReceiverOrder(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "orderID") val orderID: Long? = null,
    @ColumnInfo(name = "receiverName") val receiverName: String,
    @ColumnInfo(name = "receiverLastUpdate") val receiverLastUpdate: String
) : Parcelable

val dummyPrintingData = listOf(
    ReceiverOrder(0, "Kasis", "Minggu, 15 Oktober 2023"),
    ReceiverOrder(1, "Saiful", "Sabtu, 14 Oktober 2023"),
    ReceiverOrder(2, "Saha", "Jumat, 13 Oktober 2023"),
    ReceiverOrder(3, "Nana", "Kamis, 12 Oktober 2023"),
    ReceiverOrder(4, "Sisi", "Rabu, 11 Oktober 2023"),
    ReceiverOrder(5, "Sasa", "Selasa, 10 Oktober 2023"),
    ReceiverOrder(6, "Silvi", "Senin, 9 Oktober 2023"),
    ReceiverOrder(7, "Sia", "Minggu, 8 Oktober 2023")
)