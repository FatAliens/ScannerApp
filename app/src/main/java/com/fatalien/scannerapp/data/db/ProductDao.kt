package com.fatalien.scannerapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fatalien.scannerapp.data.entity.Product
import com.fatalien.scannerapp.data.entity.ProductWithCatalog

@Dao
interface ProductDao {
    @Insert
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Query("SELECT p.id as productId, c.id as catalogId, c.qr_code as qr, c.title as title, p.quantity as quantity, p.best_before_date as bestBeforeDate FROM products p join catalog_items c on p.catalog_item_id = c.id")
    fun getAll() : List<ProductWithCatalog>

    @Query("DELETE FROM products")
    fun clear() : Int
}