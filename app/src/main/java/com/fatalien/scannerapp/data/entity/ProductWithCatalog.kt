package com.fatalien.scannerapp.data.entity

import com.fatalien.scannerapp.data.entity.Product

data class ProductWithCatalog(
    val productId: Long,
    val catalogId: Long,
    val qr: String,
    val title: String,
    val quantity: Int,
    val bestBeforeDate: Long
) {
    public fun toProduct(): Product {
        return Product(productId, bestBeforeDate, quantity, catalogId)
    }
}