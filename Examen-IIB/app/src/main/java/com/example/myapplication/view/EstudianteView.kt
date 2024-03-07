@file:Suppress("SpellCheckingInspection")

package com.example.myapplication.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myapplication.databinding.ActivityEstudianteBinding
import com.example.myapplication.personalization.objects.Objects
import com.example.myapplication.R
import com.example.myapplication.entity.Estudiante
import com.example.myapplication.personalization.Estudiantes
import com.example.myapplication.personalization.objects.AdapterListenerEstudiante
import kotlinx.coroutines.launch

class EstudianteView : AppCompatActivity(), AdapterListenerEstudiante {

    lateinit var update: ActivityEstudianteBinding

    var listaEstudiantes: MutableList<Estudiante> = mutableListOf()
    lateinit var adaptador: Estudiantes
    lateinit var db: Objects
    lateinit var estudiante: Estudiante

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        update = ActivityEstudianteBinding.inflate(layoutInflater)
        setContentView(update.root)

        update.rvEstudiantes.layoutManager = LinearLayoutManager(this)
        db = Room.databaseBuilder(this, Objects::class.java, "dbPruebas").build()

        obtenerEstudiantes(db)

        setTitle(R.string.title_estudiante)

        update.btnAddUpdateEstudiante.setOnClickListener {

            if (update.tvNombreEstudiante.text.isNullOrEmpty() ||
                update.areaEstudiante.text.isNullOrEmpty() ||
                update.EstudianteHabilidad.text.isNullOrEmpty() ||
                update.EstudianteGpa.text.isNullOrEmpty()
            ) {
                Toast.makeText(this, "DEBES LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (update.btnAddUpdateEstudiante.text.equals("agregar")) {
                estudiante = Estudiante(
                    listaEstudiantes.size + 1,
                    update.tvNombreEstudiante.text.toString().trim(),
                    update.areaEstudiante.text.toString().trim(),
                    update.EstudianteHabilidad.text.toString().trim(),
                    update.EstudianteGpa.text.toString().trim().toDouble(),
                    obtenerProyectoId()
                )
                agregarEstudiante(db, estudiante)
            } else if (update.btnAddUpdateEstudiante.text.equals("actualizar")) {
                estudiante.nombre = update.tvNombreEstudiante.text.toString().trim()
                estudiante.area = update.areaEstudiante.text.toString().trim()
                estudiante.habilidad = update.EstudianteHabilidad.text.toString().trim()
                estudiante.gpa = update.EstudianteGpa.text.toString().trim().toDouble()
                actualizarEstudiante(db, estudiante)
            }
        }

    }

    fun obtenerProyectoId(): Int {
        return intent.getIntExtra("menuId", -1)
    }

    fun obtenerEstudiantes(room: Objects) {
        lifecycleScope.launch {
         // listaComidas = room.comidaDAO().getComidas()
            listaEstudiantes = room.estudianteDAO().getEstudianteByProyectoId(obtenerProyectoId()).toMutableList()
            adaptador = Estudiantes(listaEstudiantes, this@EstudianteView)
            update.rvEstudiantes.adapter = adaptador
        }
    }

    fun agregarEstudiante(room: Objects, estudiante: Estudiante) {
        lifecycleScope.launch {
            room.estudianteDAO().createEstudiante(estudiante)
            obtenerEstudiantes(room)
            limpiarCampos()
        }
    }

    fun actualizarEstudiante(room: Objects, estudiante: Estudiante) {
        lifecycleScope.launch {
            room.estudianteDAO().updateEstudiante(estudiante)
            obtenerEstudiantes(room)
            limpiarCampos()
        }
    }

    fun limpiarCampos() {
        update.tvNombreEstudiante.setText("")
        update.areaEstudiante.setText("")
        update.EstudianteHabilidad.setText("")
        update.EstudianteGpa.setText("")
        if (update.btnAddUpdateEstudiante.text.equals("actualizar")) {
            update.btnAddUpdateEstudiante.setText("agregar")
        }
    }

    override fun onEditItemClick(estudiante: Estudiante) {
        update.btnAddUpdateEstudiante.setText("actualizar")
        this.estudiante = estudiante
        update.tvNombreEstudiante.setText(this.estudiante.nombre)
        update.areaEstudiante.setText(this.estudiante.area)
        update.EstudianteHabilidad.setText(this.estudiante.habilidad)
        update.EstudianteGpa.setText(this.estudiante.gpa.toString())
    }

    override fun onDeleteItemClick(estudiante: Estudiante) {
        lifecycleScope.launch {
            db.estudianteDAO().deleteEstudiante(estudiante.id)
            adaptador.notifyDataSetChanged()
            obtenerEstudiantes(db)
            limpiarCampos()
        }
    }

}