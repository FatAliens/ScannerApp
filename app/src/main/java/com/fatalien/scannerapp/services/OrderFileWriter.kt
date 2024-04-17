package com.fatalien.scannerapp.services

import com.fatalien.scannerapp.data.entity.OrderItem
import com.fatalien.scannerapp.helpers.toDateString
import javax.inject.Inject

class OrderFileWriter @Inject constructor(private val _csv: CsvService) {
    fun write(path: String, products: List<OrderItem>){
        val header = listOf("TITLE", "QR", "COUNT", "COUNT REQUIRED", "BEST BEFORE DATE", "BEST BEFORE DATE REQUIRED")
        val data = products.map{ product -> listOf(product.title, product.qrCode, product.quantity.toString(), product.requiredQuantity.toString(), product.bestBeforeDate.toDateString(), product.requiredBestBeforeDate.toDateString()) }
        val result = mutableListOf<List<String>>(emptyList())
        result.add(header)
        result.addAll(data)
        _csv.writeCsvToFile(path, result)
    }
}