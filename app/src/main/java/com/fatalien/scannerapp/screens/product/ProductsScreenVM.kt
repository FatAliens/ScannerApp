package com.fatalien.scannerapp.screens.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatalien.scannerapp.data.entity.CatalogItem
import com.fatalien.scannerapp.data.entity.Product
import com.fatalien.scannerapp.data.repository.CatalogRepository
import com.fatalien.scannerapp.data.repository.ProductRepository
import com.fatalien.scannerapp.helpers.toEpochMilli
import com.fatalien.scannerapp.services.CatalogFileReader
import com.fatalien.scannerapp.services.ClipboardService
import com.fatalien.scannerapp.services.ProductsFileWriter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ProductsScreenVM @Inject constructor(
    private val _clipboard: ClipboardService,
    private val _catalogRepository: CatalogRepository,
    private val _productRepository: ProductRepository,
    private val _catalogFileReader: CatalogFileReader,
    private val _productsFileWriter: ProductsFileWriter,
) : ViewModel() {
    private val _catalog = MutableStateFlow<List<CatalogItem>>(emptyList())
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    private val _scannedProduct = MutableStateFlow<Product?>(null)
    val catalog = _catalog.asStateFlow()
    val products = _products.asStateFlow()
    val scannedProduct = _scannedProduct.asStateFlow()

    init {
        _clipboard.setOnCopy(this::onScanQr)
        loadDataFromDb()
    }

    private fun loadDataFromDb() {
        viewModelScope.launch {
            _products.emit(_productRepository.getAll())
            _catalog.emit(_catalogRepository.getAll())
        }
    }

    private fun onScanQr(qr: String) {
        val sameQrCatalogItem = catalog.value.firstOrNull { it.qrCode == qr }
        sameQrCatalogItem?.let { catalogItem ->
            selectProduct(
                Product(
                    catalogItem.qrCode,
                    catalogItem.title,
                    1,
                    LocalDate.now().toEpochMilli(),
                    0
                )
            )
        }
    }


    private fun loadNewCatalogDb(newCatalog: List<CatalogItem>) {
        viewModelScope.launch {
            _catalogRepository.clear()
            _catalogRepository.insert(newCatalog)
            _catalog.emit(newCatalog)
        }
    }

    fun loadCatalogFromFile(path: String) {
        val newCatalog = _catalogFileReader.read(path)
        newCatalog?.let { loadNewCatalogDb(it) }
    }

    fun emulateQrScan(qr: String) {
        onScanQr(qr)
    }

    fun saveProductsToFile(path: String) {
        _productsFileWriter.write(path, products.value)
    }

    fun deleteProductById(id: Int) {
        viewModelScope.launch {
            _productRepository.delete(id)
            _products.emit(_productRepository.getAll())
        }
    }

    fun selectProduct(product: Product) {
        viewModelScope.launch {
            _scannedProduct.emit(product)
        }
    }

    fun dismissNewProduct() {
        viewModelScope.launch {
            _scannedProduct.emit(null)
        }
    }

    fun onProductAdd(newProduct: Product) {
        if (newProduct.id != 0) {
            viewModelScope.launch {
                _productRepository.update(newProduct)
                _products.emit(_productRepository.getAll())
            }
        } else {
            val sameProduct = products.value.firstOrNull { item ->
                item.qrCode == newProduct.qrCode && item.bestBeforeDate == newProduct.bestBeforeDate
            }
            if(sameProduct != null){
                viewModelScope.launch {
                    _productRepository.increment(sameProduct.id, newProduct.quantity)
                    _products.emit(_productRepository.getAll())
                }
            } else {
                viewModelScope.launch {
                    _productRepository.insert(newProduct)
                    _products.emit(_productRepository.getAll())
                }
            }
        }
    }
}