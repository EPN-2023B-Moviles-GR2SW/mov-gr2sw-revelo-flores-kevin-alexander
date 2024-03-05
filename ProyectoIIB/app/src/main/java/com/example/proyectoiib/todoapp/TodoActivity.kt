package com.example.proyectoiib.todoapp

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.proyectoiib.R
import com.example.proyectoiib.imccalculator.ImcCalculatorActivity
import com.example.proyectoiib.imccalculator.ResultIMCActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoActivity : AppCompatActivity() {

    private val categories = listOf(
        TaskCategory.TrenSuperior,
        TaskCategory.TrenInferior,
        TaskCategory.Otros
    )

    private val tasks = mutableListOf(
        Task("Press de Banca", TaskCategory.TrenSuperior),
        Task("Curl de Bíceps", TaskCategory.TrenSuperior),
        Task("Squats", TaskCategory.TrenInferior),
        Task("Curl Dumbbell", TaskCategory.TrenSuperior),
        Task("Curl martillo", TaskCategory.Otros),
        Task("Peso muerto", TaskCategory.TrenInferior),
        Task("Lunges", TaskCategory.TrenInferior)

    )

    private lateinit var rvCategories: RecyclerView
    private lateinit var categoriesAdapter: CategoriesAdapter

    private lateinit var rvTask: RecyclerView
    private lateinit var tasksAdapter: TaskAdapter

    private lateinit var fabAddTask: FloatingActionButton

    private lateinit var icono_IMC: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        initComponents()
        initUI()
        initListeners()
    }

    private fun initListeners() {
        fabAddTask.setOnClickListener {
            showDialog()
        }

        icono_IMC.setOnClickListener{
            navigateToIMCActivity()
        }
    }

    private fun navigateToIMCActivity() {
        val intent = Intent(this, ImcCalculatorActivity::class.java)
        startActivity(intent)
    }

    private fun showDialog() {

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_task)

        val btnAddTask: Button = dialog.findViewById(R.id.btnAddTask)
        val etTask: EditText = dialog.findViewById(R.id.etTask)
        val rgCategory: RadioGroup = dialog.findViewById(R.id.rgCategory)


        btnAddTask.setOnClickListener {
            val currentTask = etTask.text.toString()
            if (currentTask.isNotEmpty()) {
                val selectedId = rgCategory.checkedRadioButtonId
                val selectedRadioButton:RadioButton  = rgCategory.findViewById(selectedId)
                val currentCategory: TaskCategory = when (selectedRadioButton.text) {
                    getString(R.string.todo_dialog_category_TrenSuperior) -> TaskCategory.TrenSuperior
                    getString(R.string.todo_dialog_category_TrenInferior) -> TaskCategory.TrenInferior
                    else -> TaskCategory.Otros
                }
                tasks.add(Task(currentTask, currentCategory))
                updateTask()
                dialog.hide()
            }
        }
        dialog.show()
    }

    private fun initComponents() {
        rvCategories = findViewById(R.id.rvCategories)
        rvTask = findViewById(R.id.rvTasks)
        fabAddTask = findViewById(R.id.fabAddTask)
        icono_IMC = findViewById(R.id.icono_IMC)
    }

    private fun initUI() {
        categoriesAdapter = CategoriesAdapter(categories){position -> updateCategory(position)}
        //Para hacer la lista horizontal
        rvCategories.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCategories.adapter = categoriesAdapter

        tasksAdapter = TaskAdapter(tasks) {position -> onItemSelected(position)}
        //Si queremos vertical, solo ponemos "this"
        rvTask.layoutManager = LinearLayoutManager(this)
        rvTask.adapter = tasksAdapter
    }

    private fun updateCategory(position: Int){
        categories[position].isSelected = !categories[position].isSelected
        categoriesAdapter.notifyItemChanged(position)
        updateTask()
    }

    private fun onItemSelected(position:Int){
        tasks[position].isSelected = !tasks[position].isSelected
        updateTask()
    }

    private fun updateTask() {
        val selectedCategories:List<TaskCategory> = categories.filter { it.isSelected }
        val newTask = tasks.filter { selectedCategories.contains(it.category)}
        tasksAdapter.tasks = newTask
        tasksAdapter.notifyDataSetChanged()
    }

}