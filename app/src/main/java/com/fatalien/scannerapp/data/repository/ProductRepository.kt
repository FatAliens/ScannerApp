package com.fatalien.scannerapp.data.repository

import com.fatalien.scannerapp.data.dao.ProductDao
import com.fatalien.scannerapp.data.entity.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDao: ProductDao) {
    suspend fun insert(product: Product) {
        withContext(Dispatchers.IO){
            productDao.insert(product)
        }
    }
    suspend fun update(product: Product) {
        withContext(Dispatchers.IO){
            productDao.update(product)
        }
    }

    suspend fun getAll() : List<Product> {
        return withContext(Dispatchers.IO){
            return@withContext productDao.getAll()
        }
    }

    suspend fun clear(){
        withContext(Dispatchers.IO){
            productDao.clear()
        }
    }

    suspend fun delete(id: Int){
        withContext(Dispatchers.IO){
            productDao.delete(id)
        }
    }

    suspend fun increment(qr: String){
        withContext(Dispatchers.IO){
            productDao.increment(qr)
        }
    }
}
