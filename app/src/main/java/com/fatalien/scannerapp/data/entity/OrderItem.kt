package com.fatalien.scannerapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_items")
data class OrderItem(
    @ColumnInfo(name = "qr_code") val qrCode: String,
    val title: String,
    val quantity: Int,
    @ColumnInfo(name = "required_quantity")val requiredQuantity: Int,
    @ColumnInfo(name = "bbd") val bestBeforeDate: Long,
    @ColumnInfo(name = "required_bbd") val requiredBestBeforeDate: Long,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)