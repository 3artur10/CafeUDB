package com.favicode.catlogoeinventariodetienda.model

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CafeteriaRepository {
    private val api: CafeteriaApiService

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://6acc0c98ef37b15040785212.mockapi.io/") //  URL de MockAPI.io
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(CafeteriaApiService::class.java)
    }

    suspend fun getProductos() = api.obtenerProductos()
    suspend fun getProductoById(id: String) = api.obtenerProductoPorId(id)
    suspend fun insertProducto(producto: Producto) = api.crearProducto(producto)
    suspend fun updateProducto(id: String, producto: Producto) = api.actualizarProducto(id, producto)
    suspend fun deleteProducto(id: String) = api.eliminarProducto(id)

    suspend fun getCategorias() = api.obtenerCategorias()
}