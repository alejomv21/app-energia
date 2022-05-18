package com.equitel.pruebaequitel.reciclerSheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.R

class EqAdapter : ListAdapter<Almacenamiento, EqAdapter.EqViewHolder>(DiffCallback){

    companion object DiffCallback : DiffUtil.ItemCallback<Almacenamiento>(){
        override fun areItemsTheSame(oldItem: Almacenamiento, newItem: Almacenamiento): Boolean {
            return oldItem.idEquipo == newItem.idEquipo
        }

        override fun areContentsTheSame(oldItem: Almacenamiento, newItem: Almacenamiento): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EqAdapter.EqViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.eq_list_item, parent, false)
        return EqViewHolder(view)
    }

    override fun onBindViewHolder(holder: EqAdapter.EqViewHolder, position: Int) {
        val earthquake = getItem(position)
        holder.ordenTrabajo.text = earthquake.ordenTrabajo.toString()
        holder.ciudad.text = earthquake.id.toString()
    }

    inner class EqViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val ordenTrabajo = view.findViewById<TextView>(R.id.sqlLiteOrdenTrabajo_text)
        val ciudad = view.findViewById<TextView>(R.id.sqlLiteCiudad_text)
    }
}