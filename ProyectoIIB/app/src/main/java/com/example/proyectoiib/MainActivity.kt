package com.example.proyectoiib

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.proyectoiib.todoapp.TodoActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigateToTodoApp()
    }

    private fun navigateToTodoApp() {
        val intent = Intent(this, TodoActivity::class.java)
        startActivity(intent)
    }
}

