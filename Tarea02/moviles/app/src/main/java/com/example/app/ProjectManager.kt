package com.example.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Switch
import android.widget.Toast

class ProjectManager : AppCompatActivity() {
    var dataMemory = DataMemory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_manager)

        // Llenar los datos si es necesario
        fillData()

        val btnSaveProject = findViewById<Button>(R.id.buttonSaveProject)
        btnSaveProject.setOnClickListener {
            try {
                saveProject()
                val newIntent = Intent(this, MainActivity::class.java)
                startActivity(newIntent)
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        val btnCloseProject = findViewById<Button>(R.id.buttonCancelProject)
        btnCloseProject.setOnClickListener { finish() }
    }
    fun saveProject() {
        val nameInput = findViewById<EditText>(R.id.input_name_project)
        val estimateInput = findViewById<EditText>(R.id.input_estimate_project)
        val activeProjectInput = findViewById<Switch>(R.id.input_active_project)

        val idProject = intent.getIntExtra("idProject",-1)
        val project = Project(
            id = idProject,
            nombre = nameInput.text.toString(),
            presupuesto = estimateInput.text.toString().toDouble(),
            proyectoActivo = activeProjectInput.isChecked,
            listEstudiante = mutableListOf()
        )
        if (idProject == -1) {
            dataMemory.addProject(project)
        } else {
            dataMemory.uptdateProject(idProject, project)
        }
    }

    fun fillData() {
        val nameInput = findViewById<EditText>(R.id.input_name_project)
        val estimateInput = findViewById<EditText>(R.id.input_estimate_project)
        val activeProjectInput = findViewById<Switch>(R.id.input_active_project)

        val idProject = intent.getIntExtra("idProject", -1)
        if (idProject != -1) {
            val proyectoDB = dataMemory.searchProjectById(idProject)
            proyectoDB?.let {
                nameInput.setText(it.nombre)
                estimateInput.setText(it.presupuesto.toString())
                activeProjectInput.isChecked = it.proyectoActivo
                val listViewStudentsProject = findViewById<ListView>(R.id.id_list_view_students)
                val studentsAdapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    it.listEstudiante.map{ student ->
                        "ID: ${student.id} - Estudiante: ${student.nombre}"
                    }
                )
                listViewStudentsProject.adapter = studentsAdapter
            }
        }
    }
}