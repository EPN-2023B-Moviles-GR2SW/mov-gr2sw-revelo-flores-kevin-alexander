package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch

class StudentManager : AppCompatActivity() {
    var dataMemory = DataMemory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_manager)

        fillData()

        val btnSaveStudent = findViewById<Button>(R.id.buttonSaveStudent)
        btnSaveStudent.setOnClickListener {
            saveStudent()
            finish()
        }

        val btnClose = findViewById<Button>(R.id.buttonCancelStudent)
        btnClose.setOnClickListener {
            finish()
        }
    }

    fun saveStudent() {
        val nameInput = findViewById<EditText>(R.id.input_name_student)
        val birthdayInput = findViewById<EditText>(R.id.input_birthdate_student)
        val gpaInput = findViewById<EditText>(R.id.input_gpa_student)
        val residentInput = findViewById<Switch>(R.id.input_resident_student)

        val idStudent = intent.getIntExtra("idStudent", -1)
        val student = Student(
            id = idStudent,
            nombre = nameInput.text.toString(),
            fechaNacimiento = birthdayInput.text.toString(),
            gpa = gpaInput.text.toString().toDouble(),
            esResidente = residentInput.isChecked
        )

        val idProject = intent.getIntExtra("idProject", -1)
        if (idStudent == -1) {
            DataMemory.addStudent(student)
            if (idProject != -1) {
                val project = dataMemory.searchProjectById(idProject)
                project?.listEstudiante?.add(student)
                project?.let { dataMemory.uptdateProject(idProject, it) }
            }
        } else {
            // Actualización de un estudiante existente
            DataMemory.updateStudent(idStudent, student)
            if (idProject != -1) {
                val project = dataMemory.searchProjectById(idProject)
                // Encontrar y actualizar el estudiante en la lista del proyecto
                project?.listEstudiante?.indexOfFirst { it.id == idStudent }?.let { index ->
                    if (index != -1) {
                        project.listEstudiante[index] = student
                    }
                }
                project?.let { dataMemory.uptdateProject(idProject, it) }
            }
        }
    }

    fun fillData() {
        val nameInput = findViewById<EditText>(R.id.input_name_student)
        val birthdayInput = findViewById<EditText>(R.id.input_birthdate_student)
        val gpaInput = findViewById<EditText>(R.id.input_gpa_student)
        val residentInput = findViewById<Switch>(R.id.input_resident_student)
        val idStudent = intent.getIntExtra("idStudent", -1)
        if (idStudent != -1) {
            val studentDB = dataMemory.searchStudentById(idStudent)
            studentDB?.let {
                nameInput.setText(it.nombre)
                birthdayInput.setText(it.fechaNacimiento)
                gpaInput.setText(it.gpa.toString())
                residentInput.isChecked = it.esResidente
            }
        }
    }
}