package com.fatalien.scannerapp.services

import com.fatalien.scannerapp.data.entity.CatalogItem
import javax.inject.Inject

class CatalogFileReader @Inject constructor(private val _csv: CsvService) {
    fun read(path: String) : List<CatalogItem>?{
        val fileData = _csv.readCsvFromFile(path)
        val catalogItems = fileData.map {
            CatalogItem(
                qrCode = it["Штрих-код"] ?: return null,
                title = it["Наименование"] ?: return null
            )
        }
        return catalogItems
    }
}