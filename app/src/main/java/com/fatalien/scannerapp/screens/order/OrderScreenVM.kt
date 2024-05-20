package com.fatalien.scannerapp.screens.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatalien.scannerapp.data.entity.OrderItem
import com.fatalien.scannerapp.data.repository.OrderRepository
import com.fatalien.scannerapp.helpers.toEpochMilli
import com.fatalien.scannerapp.services.ClipboardService
import com.fatalien.scannerapp.services.OrderFileReader
import com.fatalien.scannerapp.services.OrderFileWriter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class OrderScreenVM @Inject constructor(
    private val _clipboard: ClipboardService,
    private val _orderRepo: OrderRepository,
    private val _orderFileWriter: OrderFileWriter,
    private val _orderFileReader: OrderFileReader
) : ViewModel() {
    private val _orderItems = MutableStateFlow<List<OrderItem>>(emptyList())
    val orderItems = _orderItems.asStateFlow()
    private val _scannedOrderItem = MutableStateFlow<OrderItem?>(null)
    val scannedOrderItem = _scannedOrderItem.asStateFlow()

    init {
        _clipboard.setOnCopy { qr ->
            val sameQrProduct = _orderItems.value.firstOrNull { item -> item.qrCode == qr }

            sameQrProduct?.let { selectItem(OrderItem(sameQrProduct.qrCode, sameQrProduct.title, 0, 100, LocalDate.now().toEpochMilli(), LocalDate.now().toEpochMilli(), 0)) };
        }
        viewModelScope.launch {
            loadOrderFromDb();
        }
    }

    private suspend fun loadOrderFromDb() {
        _orderItems.emit(_orderRepo.getAll())
    }

    fun emulateQrScan(qr: String) {
        _clipboard.setClipboard(qr)
    }

    fun clearOrder() {
        viewModelScope.launch {
            _orderRepo.clear()
            loadOrderFromDb()
        }
    }

    fun readOrderFromFile(path: String) {
        val items = _orderFileReader.read(path)
        items?.let { updateOrder(it) }
    }

    private fun updateOrder(items: List<OrderItem>) {
        viewModelScope.launch {
            _orderRepo.clear()
            _orderRepo.insert(items)
            loadOrderFromDb()
        }
    }

    fun dismissNewItemDialog(){
        viewModelScope.launch {
            _scannedOrderItem.emit(null)
        }
    }

    fun updateOrderItem(item: OrderItem) {
        viewModelScope.launch {
            val oldItem = orderItems.value.firstOrNull { it.requiredBestBeforeDate == item.requiredBestBeforeDate && it.qrCode == item.qrCode }

            if(oldItem != null){
                if(oldItem.quantity < oldItem.requiredQuantity){
                    var newItem = oldItem.copy(quantity = oldItem.quantity+item.quantity)
                    if(newItem.quantity>newItem.requiredQuantity) newItem = newItem.copy(quantity = newItem.requiredQuantity)

                    _orderRepo.update(item)
                    loadOrderFromDb()
                }
            }
        }
    }

    fun saveOrderToFile(path: String) {
        _orderFileWriter.write(path, _orderItems.value)
    }

    fun selectItem(item: OrderItem) {
        viewModelScope.launch {
            _scannedOrderItem.emit(item)
        }
    }
}