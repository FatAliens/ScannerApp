package com.fatalien.scannerapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fatalien.scannerapp.data.entity.CatalogItem

@Entity(
    tableName = "products",
    foreignKeys = [ForeignKey(
        entity = CatalogItem::class,
        parentColumns = ["id"],
        childColumns = ["catalog_item_id"],
        onDelete = ForeignKey.CASCADE
    ),
    ],
    indices = [Index("catalog_item_id")]
)
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "best_before_date") val bestBeforeDateConverted : Long,
    val quantity: Int,
    @ColumnInfo(name = "catalog_item_id") val catalogItemId: Long
)