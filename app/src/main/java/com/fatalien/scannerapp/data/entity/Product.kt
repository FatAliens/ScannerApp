package com.fatalien.scannerapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "products", indices = [Index("qr_code", unique = true)])
data class Product(
    @ColumnInfo(name = "qr_code") val qrCode: String,
    val title: String,
    val quantity: Int,
    @ColumnInfo(name = "bbd") val bestBeforeDate: Long,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)