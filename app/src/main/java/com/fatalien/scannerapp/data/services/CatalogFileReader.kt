package com.fatalien.scannerapp.data.services

import android.nfc.FormatException
import com.fatalien.scannerapp.data.entity.CatalogItem
import javax.inject.Inject

class CatalogFileReader @Inject constructor(private val _csvReader: CsvReaderService) {
    fun read(path: String) : List<CatalogItem>{
        val fileData = _csvReader.readScvFromFile(path)
        val ex =
            FormatException("Данные в неверном формате, они должные иметь две единственные колонки 'title' и 'qr'")
        val catalogItems = fileData.map {
            CatalogItem(
                qrCode = it["QR"] ?: throw ex,
                title = it["TITLE"] ?: throw ex
            )
        }
        return catalogItems
    }
}