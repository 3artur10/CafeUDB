package com.favicode.catlogoeinventariodetienda.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.favicode.catlogoeinventariodetienda.R
import com.favicode.catlogoeinventariodetienda.controller.PerfilController
import com.favicode.catlogoeinventariodetienda.controller.ProductoController
import com.favicode.catlogoeinventariodetienda.model.Producto
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var productoController: ProductoController? = null
    private var perfilController: PerfilController? = null

    private var recyclerProductos: RecyclerView? = null
    private var productoAdapter: ProductoAdapter? = null
    private var bottomNavigation: BottomNavigationView? = null

    // Elementos de la Vista de Mi Cuenta
    private var layoutPerfil: LinearLayout? = null
    private var tvUserEmail: TextView? = null
    private var tvStatTotal: TextView? = null
    private var tvStatStockBajo: TextView? = null
    private var btnSincronizar: Button? = null
    private var btnGenerarReporte: Button? = null
    private var btnLogout: Button? = null
    private var tvTitle: TextView? = null

    private var listaActualProductos: List<Producto> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            productoController = ProductoController()
            perfilController = PerfilController()

            inicializarVistas()
            configurarEventos()
            cargarDatosProductos()

        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
        }
    }

    private fun inicializarVistas() {
        tvTitle = findViewById(R.id.tvTitle)
        recyclerProductos = findViewById(R.id.recyclerProductos)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        val includePerfil = findViewById<View>(R.id.includePerfil)
        if (includePerfil != null) {
            layoutPerfil = includePerfil.findViewById(R.id.layoutPerfil)
            tvUserEmail = includePerfil.findViewById(R.id.tvUserEmail)
            tvStatTotal = includePerfil.findViewById(R.id.tvStatTotal)
            tvStatStockBajo = includePerfil.findViewById(R.id.tvStatStockBajo)
            btnSincronizar = includePerfil.findViewById(R.id.btnSincronizar)
            btnGenerarReporte = includePerfil.findViewById(R.id.btnGenerarReporte)
            btnLogout = includePerfil.findViewById(R.id.btnLogout)
        }

        recyclerProductos?.layoutManager = LinearLayoutManager(this)
        productoAdapter = ProductoAdapter(emptyList())
        recyclerProductos?.adapter = productoAdapter

        tvUserEmail?.text = perfilController?.obtenerCorreoUsuario()
    }

    private fun configurarEventos() {
        btnSincronizar?.setOnClickListener {
            cargarDatosProductos()
            Toast.makeText(this, "Sincronizando con MockAPI...", Toast.LENGTH_SHORT).show()
        }

        btnGenerarReporte?.setOnClickListener {
            perfilController?.mostrarDialogoReporte(this, listaActualProductos)
        }

        btnLogout?.setOnClickListener {
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
            perfilController?.cerrarSesion(this)
        }

        bottomNavigation?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_productos -> {
                    mostrarProductos()
                    true
                }
                R.id.nav_categorias -> {
                    Toast.makeText(this, "Sección de Categorías", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_perfil -> {
                    mostrarPerfil()
                    true
                }
                else -> false
            }
        }
    }

    private fun mostrarProductos() {
        tvTitle?.text = "Inventario - Café UDB"
        recyclerProductos?.visibility = View.VISIBLE
        layoutPerfil?.visibility = View.GONE
    }

    private fun mostrarPerfil() {
        tvTitle?.text = "Mi Cuenta"
        recyclerProductos?.visibility = View.GONE
        layoutPerfil?.visibility = View.VISIBLE
        actualizarEstadisticasUI()
    }

    private fun cargarDatosProductos() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val lista = productoController?.cargarProductos() ?: emptyList()
                listaActualProductos = lista
                productoAdapter?.actualizarLista(lista)
                actualizarEstadisticasUI()
            } catch (_: Exception) {
                Toast.makeText(this@MainActivity, "Error al sincronizar con la API", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun actualizarEstadisticasUI() {
        val total = perfilController?.calcularTotalProductos(listaActualProductos) ?: 0
        val stockBajo = perfilController?.calcularStockBajo(listaActualProductos) ?: 0

        tvStatTotal?.text = total.toString()
        tvStatStockBajo?.text = stockBajo.toString()
    }
}