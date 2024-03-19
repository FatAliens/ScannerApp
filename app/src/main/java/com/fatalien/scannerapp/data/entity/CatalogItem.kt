package com.fatalien.scannerapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "catalog_items", indices = [Index("qr_code", unique = true)])
data class CatalogItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "qr_code") val qrCode : String,
    val title: String
)