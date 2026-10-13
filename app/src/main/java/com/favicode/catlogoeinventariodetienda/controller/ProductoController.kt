package com.favicode.catlogoeinventariodetienda.controller

import com.favicode.catlogoeinventariodetienda.model.CafeteriaRepository
import com.favicode.catlogoeinventariodetienda.model.Producto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductoController {
    private val repository = CafeteriaRepository()

    suspend fun cargarProductos(): List<Producto> {
        return withContext(Dispatchers.IO) {
            try {
                repository.getProductos()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun eliminar(id: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = repository.deleteProducto(id)
                response.isSuccessful
            } catch (e: Exception) {
                false
            }
        }
    }
}