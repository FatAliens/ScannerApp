package com.fatalien.scannerapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fatalien.scannerapp.data.entity.CatalogItem

@Dao
interface CatalogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(vararg catalogItem: CatalogItem) : List<Long>

    @Query("select * FROM catalog_items")
    fun getAll() : List<CatalogItem>

    @Query("DELETE FROM catalog_items")
    fun clearCatalog() : Int
}