package com.equitel.pruebaequitel.reciclerSheet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.R
import com.equitel.pruebaequitel.databinding.EqListItemBinding

class EqAdapter (val context: Context) : ListAdapter<Almacenamiento, EqAdapter.ViewHolder>(DiffCallback){

    companion object DiffCallback : DiffUtil.ItemCallback<Almacenamiento>(){
        override fun areItemsTheSame(oldItem: Almacenamiento, newItem: Almacenamiento): Boolean {
            return oldItem.idEquipo == newItem.idEquipo
        }

        override fun areContentsTheSame(oldItem: Almacenamiento, newItem: Almacenamiento): Boolean {
            return oldItem == newItem
        }
    }
    lateinit var onItemClickListener: ((almacenamiento :Almacenamiento) -> Unit)



    fun setOnItemClickListener1(onItemClickListener: (earthquake: Almacenamiento) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EqListItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val almacenamiento: Almacenamiento = getItem(position)
        holder.bind(almacenamiento)
    }

    inner class ViewHolder(private val binding: EqListItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(almacenamiento: Almacenamiento) {
            /*binding.sqlLiteOrdenTrabajoText.text = context.getString(R.string.magnitude_format,
                almacenamiento.magnitude)*/
            binding.sqlLiteIDText.text = almacenamiento.id.toString()
            binding.sqlLiteTipoServicioText.text = almacenamiento.tipoServicio
            binding.sqlLiteClienteText.text = almacenamiento.cliente

            binding.root.setOnClickListener {
                if (::onItemClickListener.isInitialized) {
                    onItemClickListener(almacenamiento)
                }
            }
        }
    }

    /*
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
    }*/
}