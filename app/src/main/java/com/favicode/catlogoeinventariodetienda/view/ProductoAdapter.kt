package com.favicode.catlogoeinventariodetienda.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.favicode.catlogoeinventariodetienda.R
import com.favicode.catlogoeinventariodetienda.model.Producto
import com.squareup.picasso.Picasso

class ProductoAdapter(private var listaProductos: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProducto: ImageView = view.findViewById(R.id.ivProducto)
        val tvNombre: TextView = view.findViewById(R.id.tvNombreProducto)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcionProducto)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecioProducto)
        val tvStock: TextView = view.findViewById(R.id.tvStockProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]
        holder.tvNombre.text = producto.nombre
        holder.tvDescripcion.text = producto.descripcion
        holder.tvPrecio.text = "$ ${producto.precio}"
        holder.tvStock.text = "Stock: ${producto.stock}"

        // Carga de imagen con Picasso (si el producto tuviera un campo imagen URL, lo usaríamos aquí)
        // Como MockAPI de productos estándar genera un string de imagen o usaremos una predeterminada si viene vacío:
        val imagenUrl = "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=500" // Imagen de café referencial profesional

        Picasso.get()
            .load(imagenUrl)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(holder.ivProducto)
    }

    override fun getItemCount(): Int = listaProductos.size

    fun actualizarLista(nuevaLista: List<Producto>) {
        listaProductos = nuevaLista
        notifyDataSetChanged()
    }
}