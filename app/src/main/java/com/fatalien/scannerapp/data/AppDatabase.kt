package com.fatalien.scannerapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fatalien.scannerapp.data.dao.CatalogDao
import com.fatalien.scannerapp.data.dao.OrderDao
import com.fatalien.scannerapp.data.dao.ProductDao
import com.fatalien.scannerapp.data.entity.CatalogItem
import com.fatalien.scannerapp.data.entity.OrderItem
import com.fatalien.scannerapp.data.entity.Product

@Database(version = 3, entities = [CatalogItem::class, Product::class, OrderItem::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCatalogDao() : CatalogDao
    abstract fun getProductDao() : ProductDao
    abstract fun getOrderDao() : OrderDao
}