@file:Suppress("SpellCheckingInspection")
package com.example.myapplication.personalization.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.entity.Proyecto

@Dao
interface ProyectoDAO {
    @Query("SELECT * FROM proyectos")
    suspend fun getProyectos(): List<Proyecto>

    @Insert
    suspend fun crearProyecto(proyecto: Proyecto)

    @Update
    suspend fun updateProyecto(proyecto: Proyecto)

    @Delete
    suspend fun deleteMenu(proyecto: Proyecto)

    @Query("SELECT * FROM proyectos WHERE id = :proyectoId")
    suspend fun getProyectoById(proyectoId: Int): Proyecto
}