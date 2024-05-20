package com.fatalien.scannerapp.services

import com.fatalien.scannerapp.data.entity.Product
import com.fatalien.scannerapp.helpers.toDateString
import javax.inject.Inject

class ProductsFileWriter @Inject constructor(private val _csv: CsvService) {
    fun write(path: String, products: List<Product>){
        val header = listOf("Наименование", "Штрих-код", "Количество","Срок годности")

        val data = products.map{ product -> listOf<String>(product.title, product.qrCode, product.quantity.toString(), product.bestBeforeDate.toDateString()) }

        val result = mutableListOf<List<String>>(emptyList())
        result.add(header)
        result.addAll(data)

        _csv.writeCsvToFile(path, result)
    }
}