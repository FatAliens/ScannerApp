package com.fatalien.scannerapp.screens.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatalien.scannerapp.data.entity.CatalogItem
import com.fatalien.scannerapp.data.entity.Product
import com.fatalien.scannerapp.data.repository.CatalogRepository
import com.fatalien.scannerapp.data.repository.ProductRepository
import com.fatalien.scannerapp.services.CatalogFileReader
import com.fatalien.scannerapp.services.ClipboardService
import com.fatalien.scannerapp.services.ProductsFileWriter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
    private val _scannedProduct = MutableStateFlow<NewProductState?>(null)
    val catalog = _catalog.asStateFlow()
    val products = _products.asStateFlow()
    val scannedProduct = _scannedProduct.asStateFlow()

    data class NewProductState(val qrCore: String, val title: String)

    init {
        _clipboard.setOnCopy { qr ->
            val sameQrProduct = products.value.firstOrNull{item->item.qrCode == qr}
            if(sameQrProduct != null){
                viewModelScope.launch {
                    _productRepository.increment(sameQrProduct.qrCode)
                    _products.emit(_productRepository.getAll())
                }
                return@setOnCopy
            }

            val sameQrCatalogItem = catalog.value.firstOrNull { it.qrCode == qr }
            if (sameQrCatalogItem != null) {
                viewModelScope.launch {
                    _scannedProduct.emit(
                        NewProductState(qr, sameQrCatalogItem.title)
                    )
                }
            }

        }
        loadDataFromDb()
    }

    private fun loadDataFromDb() {
        viewModelScope.launch {
            _products.emit(_productRepository.getAll())
            _catalog.emit(_catalogRepository.getAll())
        }
    }

    private fun updateCatalogDb(newCatalog: List<CatalogItem>) {
        viewModelScope.launch {
            _catalogRepository.clear()
            _catalogRepository.insert(newCatalog)
            _catalog.emit(newCatalog)
        }
    }

    fun loadCatalogFromFile(path: String) {
        val newCatalog = _catalogFileReader.read(path)
        newCatalog?.let { updateCatalogDb(it) }
    }

    fun emulateQrScan(qr: String) {
        _clipboard.setClipboard(qr)
    }

    fun saveProductsToFile(path: String) {
        _productsFileWriter.write(path, products.value)
    }

    fun dismissNewProduct() {
        viewModelScope.launch {
            _scannedProduct.emit(null)
        }
    }

    fun insertProduct(newProduct: Product) {
        viewModelScope.launch{
            _productRepository.insert(newProduct)
            _products.emit(_productRepository.getAll())
        }
    }

    fun deleteProductById(id: Int) {
        viewModelScope.launch {
            _productRepository.delete(id)
            _products.emit(_productRepository.getAll())
        }
    }
}