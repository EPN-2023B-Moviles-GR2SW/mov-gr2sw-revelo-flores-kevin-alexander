package com.example.myapplication.actions

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateEstudiante : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proyecto)

        val botonCrear = findViewById<Button>(R.id.btnAddUpdate)

        botonCrear.setOnClickListener {

            val nombre = findViewById<EditText>(R.id.etNombre).text.toString()
            val tipo = findViewById<EditText>(R.id.areaEstudiante).text.toString()
            val descripcion = findViewById<EditText>(R.id.EstudianteHabilidad).text.toString()
            val calorias = findViewById<EditText>(R.id.EstudianteGpa).text.toString()

            crearEstudiante(
                nombre,
                tipo,
                descripcion,
                calorias.toDouble()
            )
        }
    }

    private fun crearEstudiante(
        nombre: String?,
        area: String?,
        habilidades: String?,
        gpa: Double?
    ) {
        val db = Firebase.firestore
        val referenciaEstudiantes = db
            .collection("estudiantes")

        referenciaEstudiantes.get().addOnSuccessListener { querySnapshot ->
            val nuevoId = querySnapshot.size() + 1

            val datosEstudiante = hashMapOf(
                "id" to nuevoId,
                "nombre" to nombre,
                "area" to area,
                "habilidades" to habilidades,
                "gpa" to gpa
            )
            if (nombre != null) {
                referenciaEstudiantes.document(nuevoId.toString()).set(datosEstudiante)
            }
            finish()
        }
    }
}
