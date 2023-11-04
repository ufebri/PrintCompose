package com.raytalktech.printcompose.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdminData(
    val id: Int,
    val ic: Int,
    val title: String,
    val desc: String
) : Parcelable