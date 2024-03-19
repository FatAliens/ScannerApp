package com.fatalien.scannerapp.screens.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatalien.scannerapp.data.services.CatalogFileReader
import com.fatalien.scannerapp.data.entity.CatalogItem
import com.fatalien.scannerapp.data.db.CatalogRepository
import com.fatalien.scannerapp.data.services.ClipboardService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsScreenVM @Inject constructor(
    private val _clipboard: ClipboardService,
    private val _catalogRepository: CatalogRepository,
    private val _catalogFileReader: CatalogFileReader
) : ViewModel() {
    private val _catalog = MutableStateFlow<List<CatalogItem>>(emptyList())
    val catalog = _catalog.asStateFlow()

    init {
        _clipboard.onClipboardCopy { TODO("При сканировании кода товара") }
        loadCatalogFromDb()
    }

    private fun loadCatalogFromDb() {
        viewModelScope.launch {
            _catalog.emit(_catalogRepository.getAll())
        }
    }
    private fun updateCatalogDb(newCatalog: List<CatalogItem>){
        viewModelScope.launch {
            _catalogRepository.clear()
            _catalogRepository.insert(newCatalog)
            _catalog.emit(newCatalog)
        }
    }

    fun loadCatalogFromFile(path: String) {
        val newCatalog = _catalogFileReader.read(path)
        updateCatalogDb(newCatalog)
    }

    fun clearCatalog(){
        viewModelScope.launch {
            _catalogRepository.clear()
            _catalog.emit(emptyList())
        }
    }
}