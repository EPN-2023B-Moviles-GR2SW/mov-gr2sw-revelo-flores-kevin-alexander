@file:Suppress("SpellCheckingInspection")
package com.example.myapplication.personalization

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.personalization.objects.AdapterListenerProyecto
import com.example.myapplication.view.EstudianteView

class Proyectos(
    val listaaProyectos: MutableList<com.example.myapplication.entity.entity.Proyecto>,
    val listener: AdapterListenerProyecto
): RecyclerView.Adapter<Proyectos.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_proyecto, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val proyecto = listaaProyectos[position]

        holder.tvNombre.text = proyecto.nombre
        holder.tvActivo.text = proyecto.activo.toString()
        holder.tvPresupuesto.text = proyecto.presupuesto.toString()
        holder.tvRanking.text = proyecto.ranking.toString()
        holder.tvFechaInicio.text = proyecto.fechaInicio.toString()

        holder.cvProyecto.setOnClickListener {
            listener.onEditItemClick(proyecto)
        }

        holder.btnBorrar.setOnClickListener {
            listener.onDeleteItemClick(proyecto)
        }


        holder.btnVerEstudiantes.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, EstudianteView::class.java)
            intent.putExtra("proyectoId", proyecto.id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return listaaProyectos.size
    }

    inner class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {

        val cvProyecto = itemView.findViewById<CardView>(R.id.cvProyecto)
        val tvNombre = itemView.findViewById<TextView>(R.id.tvnombre)
        val tvActivo = itemView.findViewById<TextView>(R.id.tvActivo)
        val tvPresupuesto = itemView.findViewById<TextView>(R.id.tvPresupuesto)
        val tvRanking = itemView.findViewById<TextView>(R.id.tvRanking)
        val tvFechaInicio = itemView.findViewById<TextView>(R.id.tvFechaInicio)

        val btnBorrar = itemView.findViewById<Button>(R.id.btnBorrar)
        val btnVerEstudiantes = itemView.findViewById<Button>(R.id.btnVerEstudiantes)
    }

}