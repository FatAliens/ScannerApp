package com.fatalien.scannerapp.services

import com.fatalien.scannerapp.data.entity.OrderItem
import com.fatalien.scannerapp.helpers.toEpochMilli
import java.time.LocalDate
import javax.inject.Inject

class OrderFileReader @Inject constructor(private val _csv: CsvService) {
    fun read(path: String) : List<OrderItem>?{
        val fileData = _csv.readCsvFromFile(path)
        val catalogItems = fileData.map {
            OrderItem(
                qrCode = it["Штрих-код"] ?: return null,
                title = it["Наименование"] ?: return null,
                quantity = 0,
                requiredQuantity = if(it["Кол-во коробок"].isNullOrEmpty()) 1 else it["Кол-во коробок"]!!.toInt(),
                bestBeforeDate = 0,
                requiredBestBeforeDate = if(it["Срок годности"].isNullOrEmpty()) LocalDate.now().toEpochMilli() else it["Срок годности"]!!.toEpochMilli(),
            )
        }
        return catalogItems
    }
}