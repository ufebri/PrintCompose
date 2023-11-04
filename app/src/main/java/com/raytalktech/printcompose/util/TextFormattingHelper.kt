package com.raytalktech.printcompose.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Currency
import java.util.Locale

object TextFormattingHelper {

    fun getCurrentDate(): String {
        val date = Calendar.getInstance().time
        val format = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        return format.format(date)
    }

    fun getCurrency(amount: Double, currencyCode: String): String {
        val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        format.maximumFractionDigits = 0

        if (currencyCode == "IDR") {
            val symbol = Currency.getInstance("IDR").symbol
            return format.format(amount).replace(symbol, "Rp ")
        } else {
            format.currency = Currency.getInstance(currencyCode)
            return format.format(amount)
        }
    }
}