package com.fatalien.scannerapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fatalien.scannerapp.data.entity.OrderItem

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(vararg catalogItem: OrderItem) : List<Long>

    @Query("SELECT * FROM order_items")
    fun getAll() : List<OrderItem>

    @Query("DELETE FROM order_items")
    fun clear() : Int

    @Query("UPDATE order_items SET quantity = order_items.quantity+1 WHERE order_items.qr_code = :qr")
    fun increment(qr: String)

    @Update
    fun update(vararg items: OrderItem)
}