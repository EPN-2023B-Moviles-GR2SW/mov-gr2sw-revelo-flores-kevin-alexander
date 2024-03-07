@file:Suppress("SpellCheckingInspection")
package com.example.myapplication.personalization.objects

import com.example.myapplication.entity.entity.Proyecto

interface AdapterListenerProyecto {
    fun onEditItemClick(proyecto: Proyecto)
    fun onDeleteItemClick(proyecto: Proyecto)
}