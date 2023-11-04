package com.raytalktech.printcompose.util

import com.raytalktech.printcompose.model.data.ReceiverDetailOrder
import com.raytalktech.printcompose.model.data.StoreData

class BluetoothTextBuilder {
    private val textBuilder = StringBuilder()

    fun append(text: String) {
        textBuilder.append(text)
    }

    fun appendLine(text: String) {
        textBuilder.append(text).append("\n")
    }

    fun appendBold(text: String) {
        textBuilder.append("\u001B\u0045").append(text).append("\u001B\u0046")
    }

    fun appendCenterAligned(text: String) {
        textBuilder.append("\u001B\u0061\u0001").append(text).append("\n")
    }

    fun appendLeftAligned(text: String) {
        textBuilder.append("\u001B\u0061\u0000").append(text).append("\n")
    }

    fun appendRightAligned(text: String) {
        textBuilder.append("\u001B\u0061\u0002").append(text).append("\n")
    }

    fun appendLineBreak() {
        textBuilder.append("\n")
    }

    fun clearText() {
        textBuilder.clear()
    }


    private val defaultLineLength = 40
    private val defaultLineCharacter = '-'

    fun appendDefaultLineSeparator() {
        appendLineSeparator(defaultLineCharacter, defaultLineLength)
    }

    private fun appendLineSeparator(character: Char, length: Int) {
        val separator = buildString {
            repeat(length) {
                append(character)
            }
            append("\n")
        }
        textBuilder.append(separator)
    }


    fun receiptDataText(mDataOrderList: List<ReceiverDetailOrder>?, mDataStore: StoreData?) {
        appendCenterAligned(mDataStore?.nameBrand ?: "")
        appendCenterAligned(mDataStore?.addressBrand ?: "")
        appendCenterAligned(mDataStore?.telpBrand ?: "")
        appendCenterAligned(mDataStore?.addressBrand ?: "")

        appendDefaultLineSeparator()

        mDataOrderList?.forEach { item ->
            appendLeftAligned(String.format("%d@%s %s         %d", item.quantityItemOrder, item.unitItemOrder, item.nameItemOrder, item.priceItemOrder))
            appendRightAligned(item.amountItemOrder.toString())
        }
    }

    fun buildText(): String {
        return textBuilder.toString()
    }
}
