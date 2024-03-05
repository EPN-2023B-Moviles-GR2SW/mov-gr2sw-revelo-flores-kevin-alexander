package com.example.proyectoiib.todoapp

//Voy a crear objetos que sea de tipo TaskCategory
//Lo que voy hacer es mandar un listado de TaskCategory
sealed class TaskCategory(var isSelected:Boolean = true) {
    object TrenSuperior: TaskCategory()
    object TrenInferior: TaskCategory()
    object Otros: TaskCategory()

}
