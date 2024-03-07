@file:Suppress("SpellCheckingInspection")
package com.example.myapplication.personalization.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.entity.Estudiante

@Dao
interface EstudianteDAO {
    @Query("SELECT * FROM estudiantes")
    suspend fun getEstudiantes(): List<Estudiante>

    @Insert
    suspend fun createEstudiante(estudiante: Estudiante)

    @Update
    suspend fun updateEstudiante(estudiante: Estudiante)

    @Query("DELETE FROM estudiantes WHERE id=:id")
    suspend fun deleteEstudiante(id: Int)

    @Query("SELECT * FROM estudiantes WHERE proyectoId = :proyectoId")
    suspend fun getEstudianteByProyectoId(proyectoId: Int): List<Estudiante>
}