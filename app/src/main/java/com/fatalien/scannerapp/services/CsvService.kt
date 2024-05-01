package com.fatalien.scannerapp.services

import android.content.Context
import android.net.Uri
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class CsvService @Inject constructor(@ApplicationContext private val _context: Context) {
    fun readCsvFromFile(path: String): List<Map<String, String>> {
        val uri = Uri.parse(path)
        _context.contentResolver.openInputStream(uri)?.use { inputStream ->
            val csvReader = csvReader { delimiter = ';' }
            return csvReader.readAllWithHeader(inputStream)
        }
        throw IOException("Error on read file $path")
    }

    fun writeCsvToFile(path: String, data: List<List<String>>) {
        val uri = Uri.parse(path)
        _context.contentResolver.openOutputStream(uri)?.use { outputStream ->
            val csvWriter = csvWriter {
                delimiter = ';'
            }
            csvWriter.writeAll(data, outputStream)
        }
    }

}