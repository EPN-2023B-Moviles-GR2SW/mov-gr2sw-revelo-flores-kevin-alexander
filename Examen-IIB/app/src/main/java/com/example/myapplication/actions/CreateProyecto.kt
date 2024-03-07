package com.example.myapplication.actions

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.entity.entity.Estudiante
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateProyecto : AppCompatActivity() {

    lateinit var estudiante: Estudiante

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proyecto)

        val botonCrear = findViewById<Button>(R.id.btnAddUpdate)

        botonCrear.setOnClickListener {

            val nombre = findViewById<EditText>(R.id.etNombre)
            val activo = findViewById<Switch>(R.id.activo).isChecked
            val presupuesto = findViewById<EditText>(R.id.presupuesto).text.toString()
            val ranking = findViewById<EditText>(R.id.ranking).text.toString()

            crearEstudiante(
                nombre.toString(),
                activo,
                presupuesto.toDouble(),
                ranking.toDouble(),
            )
        }
    }

    private fun crearEstudiante(
        nombre: String?,
        activo: Boolean?,
        presupuesto: Double?,
        ranking: Double?,

        ) {
        val db = Firebase.firestore
        val referenciaEstudiantes = db
            .collection("proyectos")

        referenciaEstudiantes.get().addOnSuccessListener { querySnapshot ->
            val nuevoId = querySnapshot.size() + 1

            val datosProyecto = hashMapOf(
                "id" to nuevoId,
                "nombre" to nombre,
                "activo" to activo,
                "presupuesto" to presupuesto,
                "ranking" to ranking
            )
            if (nombre != null) {
                referenciaEstudiantes.document(nuevoId.toString()).set(datosProyecto)
            }
            finish()
        }
    }
}
