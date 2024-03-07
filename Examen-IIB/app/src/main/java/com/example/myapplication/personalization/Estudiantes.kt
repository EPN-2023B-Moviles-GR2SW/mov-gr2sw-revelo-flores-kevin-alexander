@file:Suppress("SpellCheckingInspection")
package com.example.myapplication.personalization

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entity.Estudiante
import com.example.myapplication.personalization.objects.AdapterListenerEstudiante

class Estudiantes(
    val listaEstudiantes: MutableList<Estudiante>,
    val listener: AdapterListenerEstudiante
): RecyclerView.Adapter<Estudiantes.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_estudiante, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val estudiante = listaEstudiantes[position]

        holder.tvNombre.text = estudiante.nombre
        holder.tvArea.text = estudiante.area
        holder.tvHabilidad.text = estudiante.habilidad
        holder.tvGpa.text = estudiante.gpa.toString()

        holder.cvEstudiante.setOnClickListener {
            listener.onEditItemClick(estudiante)
        }

        holder.btnBorrarEstudiante.setOnClickListener {
            listener.onDeleteItemClick(estudiante)
        }

    }

    override fun getItemCount(): Int {
        return listaEstudiantes.size
    }

    inner class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        val cvEstudiante = itemView.findViewById<CardView>(R.id.cvEstudiante)

        val tvNombre = itemView.findViewById<TextView>(R.id.tvNombreEstudiante)
        val tvArea = itemView.findViewById<TextView>(R.id.tvAreaEstudiante)
        val tvHabilidad = itemView.findViewById<TextView>(R.id.tvHabilidadEstudiante)
        val tvGpa = itemView.findViewById<TextView>(R.id.tvGpaEstudiante)

        val btnBorrarEstudiante = itemView.findViewById<Button>(R.id.btnBorrarEstudiante)

    }

}
