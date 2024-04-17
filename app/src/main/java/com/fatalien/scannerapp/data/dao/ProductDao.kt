package com.fatalien.scannerapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fatalien.scannerapp.data.entity.Product

@Dao
interface ProductDao {
    @Insert
    fun insert(product: Product)

    @Query("SELECT * FROM products")
    fun getAll() : List<Product>

    @Query("DELETE FROM products")
    fun clear() : Int
    @Query("DELETE FROM products WHERE products.id = :id")
    fun delete(id: Int)

    @Query("UPDATE products SET quantity = products.quantity+1 WHERE products.qr_code = :qr")
    fun increment(qr: String)
}