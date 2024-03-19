package com.fatalien.scannerapp.data.services

import android.content.Context
import android.net.Uri
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class CsvReaderService @Inject constructor(@ApplicationContext private val _context: Context) {
    fun readScvFromFile(path: String) : List<Map<String, String>> {
        val uri = Uri.parse(path)
        _context.contentResolver.openInputStream(uri)?.use { inputStream ->
            val csvReader = csvReader { delimiter = ',' }
            return csvReader.readAllWithHeader(inputStream)
        }
        throw IOException("Error on read file $path")
    }
}