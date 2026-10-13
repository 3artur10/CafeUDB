package com.favicode.catlogoeinventariodetienda.controller

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.favicode.catlogoeinventariodetienda.model.Producto
import com.favicode.catlogoeinventariodetienda.view.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class PerfilController {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun obtenerCorreoUsuario(): String {
        return auth.currentUser?.email ?: "Usuario"
    }

    fun cerrarSesion(context: Context) {
        auth.signOut()
        val intent = Intent(context, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(intent)
    }

    fun calcularTotalProductos(lista: List<Producto>): Int {
        return lista.size
    }

    fun calcularStockBajo(lista: List<Producto>): Int {
        return lista.count { it.stock < 5 }
    }

    fun mostrarDialogoReporte(context: Context, lista: List<Producto>) {
        val totalProductos = calcularTotalProductos(lista)
        val stockBajoCount = calcularStockBajo(lista)
        val valorInventario = lista.sumOf { it.precio * it.stock }

        val mensaje = """
            📊 REPORTE GENERAL DE CAFÉ UDB
            
            • Total de productos: $totalProductos
            • Productos con stock bajo (<5): $stockBajoCount
            • Valor estimado en stock: $ ${String.format("%.2f", valorInventario)}
            
            Estado: Sincronizado con MockAPI en la nube.
        """.trimIndent()

        AlertDialog.Builder(context)
            .setTitle("Reporte de Inventario")
            .setMessage(mensaje)
            .setPositiveButton("Copiar Resumen") { _, _ ->
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Reporte Inventario", mensaje)
                clipboard.setPrimaryClip(clip)
            }
            .setNegativeButton("Cerrar", null)
            .show()
    }
}