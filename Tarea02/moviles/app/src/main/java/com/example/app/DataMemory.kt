package com.example.app

import android.content.ContentValues
import android.content.Context

class DataMemory(context: Context) {
    private val dbHelper = SQLiteHelper(context)

    fun addProject(project: Project): Long {
        return dbHelper.addProject(project)
    }

    fun getProject(id: Int): Project? {
        return dbHelper.getProject(id)
    }

    fun getProjects(): List<Project> {
        val projects = mutableListOf<Project>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Project", null)
        if (cursor.moveToFirst()) {
            do {
                val project = Project(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getInt(3) != 0)
                projects.add(project)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return projects
    }

    // Actualizar un proyecto
    fun updateProject(project: Project): Int {
        return dbHelper.updateProject(project)
    }

    // Eliminar un proyecto
    fun deleteProject(id: Int): Int {
        return dbHelper.deleteProject(id)
    }

    // Agregar un nuevo estudiante
    fun addStudent(student: Student): Long {
        return dbHelper.addStudent(student)
    }

    // Obtener un estudiante por ID
    fun getStudent(id: Int): Student? {
        return dbHelper.getStudent(id)
    }

    // Obtener todos los estudiantes
    fun getStudents(): List<Student> {
        val students = mutableListOf<Student>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Student", null)
        if (cursor.moveToFirst()) {
            do {
                val student = Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3), cursor.getInt(4) != 0)
                students.add(student)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return students
    }

    // Actualizar un estudiante
    fun updateStudent(student: Student): Int {
        return dbHelper.updateStudent(student)
    }

    // Eliminar un estudiante
    fun deleteStudent(id: Int): Int {
        return dbHelper.deleteStudent(id)
    }

    fun addStudentToProject(projectId: Int, studentId: Int): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("projectId", projectId)
            put("studentId", studentId)
        }

        val id = db.insert("ProjectStudent", null, values)
        db.close()
        return id
    }

    // Obtener estudiantes de un proyecto específico
    fun getStudentsForProject(projectId: Int): List<Student> {
        val students = mutableListOf<Student>()
        val db = dbHelper.readableDatabase
        val query = "SELECT Student.* FROM Student INNER JOIN ProjectStudent ON Student.id = ProjectStudent.studentId WHERE ProjectStudent.projectId = ?"
        val cursor = db.rawQuery(query, arrayOf(projectId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val student = Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3), cursor.getInt(4) != 0)
                students.add(student)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return students
    }

    // Eliminar un estudiante de un proyecto
    fun removeStudentFromProject(projectId: Int, studentId: Int): Int {
        val db = dbHelper.writableDatabase
        val affectedRows = db.delete("ProjectStudent", "projectId = ? AND studentId = ?", arrayOf(projectId.toString(), studentId.toString()))
        db.close()
        return affectedRows
    }
}