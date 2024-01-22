package com.example.app

class DataMemory {
    companion object {
        val students = mutableListOf<Student>()
        val projects = mutableListOf<Project>()
        private var nexProjectId = 3
        private var nextStudentId = 7

        //CRUD Student
        fun addStudent(student: Student) {
            student.id = nextStudentId++
            students.add(student)
        }

        fun updateStudent(id: Int, newStudent: Student) {
            val index = students.indexOfFirst { it.id == id }
            if (index != -1){
                students[index] = newStudent
            }
        }

        fun deleteStudent(id: Int): Boolean {
            val student = students.find { it.id == id }
            return if (student != null) {
                students.remove(student)
                true
            } else {
                false
            }
        }

        fun searchStudentById(idStudent: Int): Student? {
            return students.find { it.id == idStudent }
        }

        //CRUD Project

        fun addProject(project: Project) {
            project.id = nexProjectId++
            projects.add(project)
        }

        fun uptdateProject(id: Int, newProject: Project) {
            val project = projects.find { it.id == id }
            project?.let {
                val existingStudents = it.listEstudiante
                newProject.listEstudiante = existingStudents

                val index = projects.indexOf(it)
                projects[index] = newProject
            } ?: println("Proyecto no encontrado con el ID: $id")
        }

        fun deletePoject(id: Int): Boolean {
            val project = projects.find { it.id == id }
            return if (project != null) {
                projects.remove(project)
                true
            } else {
                false
            }
        }

        fun searchProjectById(idProject: Int): Project? {
            return projects.find { it.id == idProject }
        }

        init {
            val student1 = Student(1, "Juan", "1999-01-01", 3.5, true)
            val student2 = Student(2, "Ana", "2000-02-02", 3.7, false)
            val student3 = Student(3, "Luis", "1998-03-03", 3.8, true)

            val student4 = Student(4, "Sara", "2001-12-23", 2.6, true)
            val student5 = Student(5, "Ricardo", "2000-12-23", 3.3, false)
            val student6 = Student(6, "Jose", "1994", 2.2, true)

            students.addAll(listOf(student1, student2, student3, student4, student5, student6))

            val projectAlpha =
                Project(1, "Alpha", 10000.0, true, mutableListOf(student1, student2, student3))
            val projectGamma =
                Project(2, "Gamma", 20000.0, true, mutableListOf(student4, student5, student6))

            projects.add(projectAlpha)
            projects.add(projectGamma)
        }
    }
}