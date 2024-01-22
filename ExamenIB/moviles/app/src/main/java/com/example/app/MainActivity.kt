package com.example.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.ArrayAdapter
import android.view.ContextMenu
import android.view.View
import android.view.MenuItem
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    lateinit var adapter: ArrayAdapter<String>
    var dataMemory = DataMemory
    private var idProjectSelected = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCreateProject = findViewById<Button>(R.id.id_btn_add_project)
        btnCreateProject.setOnClickListener {
            goActivity(ProjectManager::class.java)
        }
        updateListViewProjects()
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_projects, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val project = dataMemory.projects[info.position]
        idProjectSelected = project.id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_see_project -> {
                goActivity(ProjectView::class.java, idProjectSelected)
                return true
            }
            R.id.menu_edit_project -> {
                goActivity(ProjectManager::class.java, idProjectSelected)
                return true
            }
            R.id.menu_delete_project -> {
                openDialogDeleteProject()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun goActivity(clase: Class<*>, id: Int? = null) {
        val intent = Intent(this, clase)
        intent.putExtra("idProject", id)
        startActivity(intent)
    }

    fun openDialogDeleteProject() {
        val builderDialog = AlertDialog.Builder(this)
        builderDialog.setTitle("Desea eliminar?")
        builderDialog.setNegativeButton("No", null)
        builderDialog.setPositiveButton("Si") { _, _ ->
            dataMemory.deletePoject(idProjectSelected)
            updateListViewProjects()
        }
        val dialog = builderDialog.create()
        dialog.show()
    }

    fun updateListViewProjects() {
        val listViewProjects = findViewById<ListView>(R.id.id_list_view_projects)
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            dataMemory.projects.map { project ->
                "ID: ${project.id} - Proyecto: ${project.nombre}"
            }
        )
        listViewProjects.adapter = adapter
        adapter.notifyDataSetChanged()
        registerForContextMenu(listViewProjects)
    }


    override fun onRestart() {
        super.onRestart()
        updateListViewProjects()
    }

    override fun onResume() {
        super.onResume()
        updateListViewProjects()
    }

}