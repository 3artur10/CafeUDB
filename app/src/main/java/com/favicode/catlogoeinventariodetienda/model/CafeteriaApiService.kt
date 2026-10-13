package com.favicode.catlogoeinventariodetienda.model

import retrofit2.Response
import retrofit2.http.*

interface CafeteriaApiService {
    // --- PRODUCTOS ---
    @GET("producto")
    suspend fun obtenerProductos(): List<Producto>

    @GET("producto/{id}")
    suspend fun obtenerProductoPorId(@Path("id") id: String): Response<Producto>

    @POST("producto")
    suspend fun crearProducto(@Body producto: Producto): Response<Producto>

    @PUT("producto/{id}")
    suspend fun actualizarProducto(@Path("id") id: String, @Body producto: Producto): Response<Producto>

    @DELETE("producto/{id}")
    suspend fun eliminarProducto(@Path("id") id: String): Response<Producto>

    // --- CATEGORIAS ---
    @GET("categoria")
    suspend fun obtenerCategorias(): List<Categoria>

    @POST("categoria")
    suspend fun crearCategoria(@Body categoria: Categoria): Response<Categoria>
}