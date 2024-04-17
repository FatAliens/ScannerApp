package com.fatalien.scannerapp.data.repository

import com.fatalien.scannerapp.data.dao.CatalogDao
import com.fatalien.scannerapp.data.entity.CatalogItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatalogRepository @Inject constructor(private val catalogDao: CatalogDao) {
    suspend fun insert(items: List<CatalogItem>) {
        withContext(Dispatchers.IO){
            catalogDao.insertItems(*items.toTypedArray())
        }
    }

    suspend fun getAll() : List<CatalogItem> {
        return withContext(Dispatchers.IO){
            return@withContext catalogDao.getAll()
        }
    }

    suspend fun clear(){
        withContext(Dispatchers.IO){
            catalogDao.clearCatalog()
        }
    }
}
