package com.example.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class ProjectView : AppCompatActivity() {

    lateinit var adapter: ArrayAdapter<String>
    var dataMemory = DataMemory
    var idProject = -1
    private var idStudentSelected = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_view)

        val btnReturn = findViewById<Button>(R.id.id_btn_atras)
        btnReturn.setOnClickListener{
            finish()
        }
        val btnAddStudent = findViewById<Button>(R.id.id_btn_add_student)
        btnAddStudent.setOnClickListener{
            val intent = Intent(this, StudentManager::class.java)
            intent.putExtra("idProject", idProject)
            startActivity(intent)
        }

        idProject = intent.getIntExtra("idProject", -1)
        updateListViewStudent()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_student, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val project = dataMemory.searchProjectById(idProject)
        idStudentSelected = project?.listEstudiante?.get(info.position)?.id ?: -1
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_edit_student -> {
                goActivity(StudentManager::class.java, idStudentSelected)
                true
            }
            R.id.menu_delete_student -> {
                openDialogDeleteStudent()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun goActivity(clase: Class<*>, idStudent: Int?=null){
        val intent = Intent(this, clase)
        intent.putExtra("idProject", idProject)
        idStudent?.let{
            intent.putExtra("idStudent", it)
        }
        startActivity(intent)
    }

    fun openDialogDeleteStudent(){
        val builderDialog = AlertDialog.Builder(this)
        builderDialog.setTitle("Desea eliminar?")
        builderDialog.setNegativeButton("No", null)
        builderDialog.setPositiveButton("Si") { _, _ ->
            val deleteSuccessful = dataMemory.deleteStudent(idStudentSelected)
            if (deleteSuccessful) {
                // Eliminar estudiante del proyecto específico
                val project = dataMemory.searchProjectById(idProject)
                project?.listEstudiante?.removeIf { it.id == idStudentSelected }
                project?.let { dataMemory.uptdateProject(idProject, it) }
            }

            updateListViewStudent()
        }
        val dialog = builderDialog.create()
        dialog.show()
    }

    fun updateListViewStudent() {
        val listViewStudents = findViewById<ListView>(R.id.id_list_view_students)
        val project = dataMemory.searchProjectById(idProject)
        val students = project?.listEstudiante ?: mutableListOf()

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            students.map { student ->
                "ID: ${student.id} - Estudiante: ${student.nombre}"
            }
        )
        listViewStudents.adapter = adapter
        adapter.notifyDataSetChanged()
        registerForContextMenu(listViewStudents)
    }

    override fun onRestart() {
        super.onRestart()
        updateListViewStudent()
    }

    override fun onResume() {
        super.onResume()
        updateListViewStudent()
    }
}