package com.fatalien.scannerapp.data.db

import com.fatalien.scannerapp.data.entity.Product
import com.fatalien.scannerapp.data.entity.ProductWithCatalog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDao: ProductDao) {
    suspend fun insert(product: Product) {
        withContext(Dispatchers.IO){
            productDao.insert(product)
        }
    }

    suspend fun getAll() : List<ProductWithCatalog> {
        return withContext(Dispatchers.IO){
            return@withContext productDao.getAll()
        }
    }

    suspend fun clear(){
        withContext(Dispatchers.IO){
            productDao.clear()
        }
    }
}
