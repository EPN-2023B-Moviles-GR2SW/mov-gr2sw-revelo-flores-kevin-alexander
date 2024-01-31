package com.example.app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ProjectStudentDB"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createProjectTable = "CREATE TABLE Project (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, presupuesto REAL, proyectoActivo INTEGER)"
        val createStudentTable = "CREATE TABLE Student (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, fechaNacimiento TEXT, gpa REAL, esResidente INTEGER)"
        val createProjectStudentTable = "CREATE TABLE ProjectStudent (projectId INTEGER, studentId INTEGER, PRIMARY KEY (projectId, studentId), FOREIGN KEY (projectId) REFERENCES Project(id), FOREIGN KEY (studentId) REFERENCES Student(id))"

        db.execSQL(createProjectTable)
        db.execSQL(createStudentTable)
        db.execSQL(createProjectStudentTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Project")
        db.execSQL("DROP TABLE IF EXISTS Student")
        db.execSQL("DROP TABLE IF EXISTS ProjectStudent")
        onCreate(db)
    }

    // Métodos CRUD para Project

    // Agregar Proyecto
    fun addProject(project: Project): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", project.nombre)
            put("presupuesto", project.presupuesto)
            put("proyectoActivo", project.proyectoActivo)
        }

        val id = db.insert("Project", null, values)
        db.close()
        return id
    }

    // Obtener Proyecto
    fun getProject(id: Int): Project? {
        val db = this.readableDatabase
        val cursor = db.query("Project", arrayOf("id", "nombre", "presupuesto", "proyectoActivo"), "id = ?", arrayOf(id.toString()), null, null, null)
        var project: Project? = null

        if (cursor.moveToFirst()) {
            project = Project(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getInt(3) != 0)
        }
        cursor.close()
        db.close()
        return project
    }
    // Actualizar un proyecto
    fun updateProject(project: Project): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", project.nombre)
            put("presupuesto", project.presupuesto)
            put("proyectoActivo", project.proyectoActivo)
        }

        val affectedRows = db.update("Project", values, "id = ?", arrayOf(project.id.toString()))
        db.close()
        return affectedRows
    }

    // Eliminar un proyecto
    fun deleteProject(id: Int): Int {
        val db = this.writableDatabase
        val affectedRows = db.delete("Project", "id = ?", arrayOf(id.toString()))
        db.close()
        return affectedRows
    }

    // Métodos CRUD para Student

    // Agregar Estudiante
    fun addStudent(student: Student): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", student.nombre)
            put("fechaNacimiento", student.fechaNacimiento)
            put("gpa", student.gpa)
            put("esResidente", student.esResidente)
        }

        val id = db.insert("Student", null, values)
        db.close()
        return id
    }

    // Obtener Estudiante
    fun getStudent(id: Int): Student? {
        val db = this.readableDatabase
        val cursor = db.query("Student", arrayOf("id", "nombre", "fechaNacimiento", "gpa", "esResidente"), "id = ?", arrayOf(id.toString()), null, null, null)
        var student: Student? = null

        if (cursor.moveToFirst()) {
            student = Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3), cursor.getInt(4) != 0)
        }
        cursor.close()
        db.close()
        return student
    }

    // Actualizar un estudiante
    fun updateStudent(student: Student): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", student.nombre)
            put("fechaNacimiento", student.fechaNacimiento)
            put("gpa", student.gpa)
            put("esResidente", student.esResidente)
        }

        val affectedRows = db.update("Student", values, "id = ?", arrayOf(student.id.toString()))
        db.close()
        return affectedRows
    }

    // Eliminar un estudiante
    fun deleteStudent(id: Int): Int {
        val db = this.writableDatabase
        val affectedRows = db.delete("Student", "id = ?", arrayOf(id.toString()))
        db.close()
        return affectedRows
    }

    // Métodos para manejar la relación entre Project y Student
    // Asignar un estudiante a un proyecto
    fun addStudentToProject(projectId: Int, studentId: Int): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("projectId", projectId)
            put("studentId", studentId)
        }

        val id = db.insert("ProjectStudent", null, values)
        db.close()
        return id
    }

    // Eliminar un estudiante de un proyecto
    fun removeStudentFromProject(projectId: Int, studentId: Int): Int {
        val db = this.writableDatabase
        val affectedRows = db.delete("ProjectStudent", "projectId = ? AND studentId = ?", arrayOf(projectId.toString(), studentId.toString()))
        db.close()
        return affectedRows
    }
}