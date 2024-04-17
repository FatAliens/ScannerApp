package com.fatalien.scannerapp.services

import com.fatalien.scannerapp.data.entity.OrderItem
import com.fatalien.scannerapp.helpers.toEpochMilli
import javax.inject.Inject

class OrderFileReader @Inject constructor(private val _csv: CsvService) {
    fun read(path: String) : List<OrderItem>?{
        val fileData = _csv.readCsvFromFile(path)
        val catalogItems = fileData.map {
            OrderItem(
                qrCode = it["QR"] ?: return null,
                title = it["TITLE"] ?: return null,
                quantity = 0,
                requiredQuantity = it["QUANTITY"]?.toInt() ?: return null,
                bestBeforeDate = 0,
                requiredBestBeforeDate = it["BBD"]?.toEpochMilli() ?: return null,
            )
        }
        return catalogItems
    }
}