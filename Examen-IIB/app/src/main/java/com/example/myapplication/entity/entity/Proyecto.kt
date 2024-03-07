package com.example.myapplication.entity.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "proyectos")
data class Proyecto(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "nombre") var nombre: String,
    @ColumnInfo(name = "activo") var activo: Boolean,
    @ColumnInfo(name = "presupuesto") var presupuesto: Double,
    @ColumnInfo(name = "ranking") var ranking: Double,
    @ColumnInfo(name = "fechaInicio") var fechaInicio: String
)
