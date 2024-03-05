package com.example.proyectoiib.todoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoiib.R

class CategoriesAdapter(
    private val categories: List<TaskCategory>,
    private val onItemSelected: (Int) -> Unit
) : RecyclerView.Adapter<CategoriesViewHolder>() {

    //Pinta El contenedor de la vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task_category, parent, false)
        return CategoriesViewHolder(view)
    }

    //Pinta el contenido (info)
    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.render(categories[position],onItemSelected)
    }

    //Pinta el numero de celdas del recyclerview
    //en este caso quiero que me pinte el tamaño de la lista
    override fun getItemCount(): Int {
        return categories.size
    }

}