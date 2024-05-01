package com.fatalien.scannerapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fatalien.scannerapp.data.entity.Product

@Dao
interface ProductDao {
    @Insert
    fun insert(product: Product)
    @Update
    fun update(product: Product)
    @Query("SELECT * FROM products")
    fun getAll() : List<Product>
    @Query("DELETE FROM products")
    fun clear() : Int
    @Query("DELETE FROM products WHERE products.id = :id")
    fun delete(id: Int)
    @Query("UPDATE products SET quantity = products.quantity+:count WHERE products.id = :id")
    fun increment(id: Int, count: Int)
}