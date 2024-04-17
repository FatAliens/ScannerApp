package com.fatalien.scannerapp.services

import com.fatalien.scannerapp.data.entity.CatalogItem
import javax.inject.Inject

class CatalogFileReader @Inject constructor(private val _csv: CsvService) {
    fun read(path: String) : List<CatalogItem>?{
        val fileData = _csv.readCsvFromFile(path)
        val catalogItems = fileData.map {
            CatalogItem(
                qrCode = it["QR"] ?: return null,
                title = it["TITLE"] ?: return null
            )
        }
        return catalogItems
    }
}