package com.raytalktech.printcompose.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PrinterData(
    val corpData: StoreData? = null,
    val receiverOrder: ReceiverOrder? = null,
    val orderList: List<ReceiverDetailOrder>? = null,
) : Parcelable