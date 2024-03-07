package com.example.myapplication.entity.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "comidas", foreignKeys = [ForeignKey(entity = Menu::class, parentColumns = ["id"], childColumns = ["menuId"])])
@Entity(tableName = "estudiantes")
data class Estudiante(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "nombre") var nombre: String,
    @ColumnInfo(name = "area") var area: String,
    @ColumnInfo(name = "habilidad") var habilidad: String,
    @ColumnInfo(name = "gpa") var gpa: Double,
    @ColumnInfo(name = "proyectoId") var proyectoId: Int // Clave externa para la relación muchos a uno con Proyecto
)
