package com.example.app

class Project (
    var id: Int,
    val nombre: String,
    val presupuesto: Double,
    val proyectoActivo: Boolean,
    var listEstudiante: MutableList<Student>
)