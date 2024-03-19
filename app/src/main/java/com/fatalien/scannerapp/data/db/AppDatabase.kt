package com.fatalien.scannerapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fatalien.scannerapp.data.db.CatalogDao
import com.fatalien.scannerapp.data.entity.CatalogItem
import com.fatalien.scannerapp.data.entity.Product
import com.fatalien.scannerapp.data.db.ProductDao

@Database(version = 1, entities = [CatalogItem::class, Product::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCatalogDao() : CatalogDao
    abstract fun getProductDao() : ProductDao
}