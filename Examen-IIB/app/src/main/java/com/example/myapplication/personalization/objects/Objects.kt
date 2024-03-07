@file:Suppress("SpellCheckingInspection")
package com.example.myapplication.personalization.objects

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Menu::class,
                Comida::class],
    version = 1
)
abstract class Objects: RoomDatabase() {
    abstract fun proyectoDAO(): MenuDAO
    abstract fun estudianteDAO(): ComidaDAO
}