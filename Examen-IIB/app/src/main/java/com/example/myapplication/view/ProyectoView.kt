@file:Suppress("SpellCheckingInspection")
package com.example.myapplication.view

import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myapplication.personalization.objects.Objects
import com.example.myapplication.R
import com.example.myapplication.personalization.objects.AdapterListenerProyecto
import com.example.myapplication.personalization.Proyectos
import com.example.myapplication.databinding.ActivityProyectoBinding
import com.example.myapplication.entity.entity.Proyecto
import kotlinx.coroutines.launch
import java.util.Calendar

class ProyectoView : AppCompatActivity(), AdapterListenerProyecto {

    lateinit var update: ActivityProyectoBinding
    var listaProyectos: MutableList<Proyecto> = mutableListOf()
    lateinit var adaptador: Proyectos
    lateinit var db: Objects
    lateinit var proyecto: Proyecto


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        update = ActivityProyectoBinding.inflate(layoutInflater)
        setContentView(update.root)

        update.rvProyecto.layoutManager = LinearLayoutManager(this)

        db = Room.databaseBuilder(this, Objects::class.java, "dbPruebas").build()

        obtenerProyecto(db)

        setTitle(R.string.title_proyecto)

        update.btnAddUpdate.setOnClickListener {

            if (update.etNombre.text.isNullOrEmpty() ||
                update.activo.text.isNullOrEmpty() ||
                update.presupuesto.text.isNullOrEmpty() ||
                update.ranking.text.isNullOrEmpty()
            ) {
                Toast.makeText(this, "DEBES LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (update.btnAddUpdate.text.equals("agregar")) {
                proyecto = Proyecto(
                    listaProyectos.size+1,
                    update.etNombre.text.toString().trim(),
                    update.activo.isChecked,
                    update.presupuesto.text.toString().trim().toDouble(),
                    update.ranking.text.toString().trim().toDouble(),
                    obtenerFechaSeleccionada(update.fechaInicio)

                )
                agergarProyecto(db, proyecto)
            } else if (update.btnAddUpdate.text.equals("actualizar")) {
                proyecto.nombre = update.etNombre.text.toString().trim()
                proyecto.activo = update.activo.isChecked // Utiliza isChecked para obtener el estado del Switch
                proyecto.presupuesto = update.presupuesto.text.toString().trim().toDouble()
                proyecto.ranking = update.ranking.text.toString().trim().toDouble()
                proyecto.fechaInicio = obtenerFechaSeleccionada(update.fechaInicio)

                actualizarProyecto(db, proyecto)
            }
        }

    }

    private fun obtenerFechaSeleccionada(datePicker: DatePicker): String {
        val year = datePicker.year
        val month = datePicker.month + 1 // Los meses en DatePicker van de 0 a 11
        val day = datePicker.dayOfMonth

        return String.format("%04d-%02d-%02d", year, month, day)
    }

    fun obtenerProyecto(room: Objects) {
        lifecycleScope.launch {
            listaProyectos = room.proyectoDAO().getProyectos()
            adaptador = Proyectos(listaProyectos, this@ProyectoView)
            update.rvProyecto.adapter = adaptador
        }
    }

    fun agergarProyecto(room: Objects, proyecto: Proyecto) {
        lifecycleScope.launch {
            room.proyectoDAO().crearProyecto(proyecto)
            obtenerProyecto(room)
            limpiarCampos()
        }
    }

    fun actualizarProyecto(room: Objects, proyecto: Proyecto) {
        lifecycleScope.launch {
            room.proyectoDAO().updateProyecto(proyecto)
            obtenerProyecto(room)
            limpiarCampos()
        }
    }

    fun limpiarCampos() {
        update.etNombre.setText("")
        update.activo.isChecked = false
        update.presupuesto.setText("")
        update.ranking.setText("")

        val today = Calendar.getInstance()
        update.fechaInicio.updateDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH))

        if (update.btnAddUpdate.text.equals("actualizar")) {
            update.btnAddUpdate.setText("agregar")
            //  binding.etNombre.isEnabled = true
        }

    }

    override fun onEditItemClick(proyecto: Proyecto) {
        update.btnAddUpdate.setText("actualizar")
        //  binding.etNombre.isEnabled = false

        this.proyecto = proyecto
        update.etNombre.setText(this.proyecto.nombre)
        update.activo.isChecked = this.proyecto.activo
        update.presupuesto.setText(this.proyecto.presupuesto.toString())
        update.ranking.setText(this.proyecto.ranking.toString())


        val fechaAdicion = this.proyecto.fechaInicio // Supongamos que es una cadena en formato "YYYY-MM-DD"
        val fechaParts = fechaAdicion.split("-")
        if (fechaParts.size == 3) {
            val year = fechaParts[0].toInt()
            val month = fechaParts[1].toInt() - 1 // Restamos 1 para convertir de 1-12 a 0-11
            val day = fechaParts[2].toInt()
            update.fechaInicio.updateDate(year, month, day)
        }

    }

    override fun onDeleteItemClick(proyecto: Proyecto) {
        lifecycleScope.launch {
            db.proyectoDAO().deleteMenu(proyecto)
            adaptador.notifyDataSetChanged()
            obtenerProyecto(db)
        }
    }

}