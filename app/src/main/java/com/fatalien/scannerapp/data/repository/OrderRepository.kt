package com.fatalien.scannerapp.data.repository

import com.fatalien.scannerapp.data.dao.OrderDao
import com.fatalien.scannerapp.data.entity.OrderItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRepository @Inject constructor(private val orderDao: OrderDao) {
    suspend fun insert(items: List<OrderItem>) {
        withContext(Dispatchers.IO){
            orderDao.insertItems(*items.toTypedArray())
        }
    }

    suspend fun getAll() : List<OrderItem> {
        return withContext(Dispatchers.IO){
            return@withContext orderDao.getAll()
        }
    }

    suspend fun clear(){
        withContext(Dispatchers.IO){
            orderDao.clear()
        }
    }

    suspend fun increment(qr: String){
        withContext(Dispatchers.IO){
            orderDao.increment(qr)
        }
    }

    suspend fun update(item: OrderItem){
        withContext(Dispatchers.IO){
            orderDao.update(item)
        }
    }
}
