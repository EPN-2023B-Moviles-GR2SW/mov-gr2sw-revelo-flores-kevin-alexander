package dao

import entidades.estudiante
import java.io.File
import java.time.LocalDate

class estudianteDAO () {
    private val estudiantesFile = File("src/main/kotlin/archivos/estudiante.txt")

    fun agregarEstudiante(estudiante: estudiante) {
        estudiantesFile.appendText("${estudiante.id} | ${estudiante.nombre} | ${estudiante.fechaNacimiento} | " +
                "${estudiante.gpa} | ${estudiante.esResidente}\n")
    }

    fun obtenerEstudiantes(): List<estudiante> {
        return estudiantesFile.readLines().mapNotNull {
            val campos = it.split(" | ")
            estudiante(
                campos[0].toInt(),
                campos[1],
                LocalDate.parse(campos[2]),
                campos[3].toDouble(),
                campos[4].toBoolean()
            )
        }
    }

    fun obtenerEstudiantePorId(estudiantes: List<estudiante>, id: Int): estudiante? {
        return estudiantes.find { it.id == id }
    }

    fun actualizarEstudiante(){
        val estudiantes = obtenerEstudiantes()

        println("---------Lista Estudiantes-----------")
        estudiantes.forEach { println(it) }
        println("---------------")

        println("ACTUALIZAR ESTUDIANTE")
        println("Ingresa el ID del estudiante a actualizar")
        val idEstudiante = readLine()?.toIntOrNull()

        if (idEstudiante != null) {
            val estudianteActualizado = obtenerEstudiantePorId(estudiantes, idEstudiante)

            if (estudianteActualizado != null) {
                println(estudianteActualizado.nombre)
                println("¿Qué desea actualizar?")
                println("1. Nombre")
                println("2. Fecha de Nacimiento")
                println("3. GPA")
                println("4. Residente")
                println("5. Volver al Menú Estudiante")

                var opcionCampos: Int
                do {
                    print("Ingrese el número del campo que desea actualizar: ")
                    opcionCampos = readLine()?.toIntOrNull() ?: 5

                    when (opcionCampos) {
                        1 -> {
                            print("Ingrese el nuevo nombre del estudiante: ")
                            val nuevoNombre = readLine() ?: ""
                            estudianteActualizado.nombre = nuevoNombre
                        }
                        2 -> {
                            print("Ingrese la nueva fecha de nacimiento del estudiante (YYYY-MM-DD): ")
                            val nuevaFechaNacimiento = readLine()?.let { LocalDate.parse(it) }
                            if (nuevaFechaNacimiento != null) {
                                estudianteActualizado.fechaNacimiento = nuevaFechaNacimiento
                            }
                        }
                        3 -> {
                            print("Ingrese el nuevo GPA del estudiante: ")
                            val nuevoGPA = readLine()?.toDoubleOrNull() ?: 0.0
                            estudianteActualizado.gpa = nuevoGPA
                        }
                        4 -> {
                            print("Ingrese si el estudiante es residente (true/false): ")
                            val esResidente = readLine()?.toBoolean() ?: false
                            estudianteActualizado.esResidente = esResidente
                        }
                        5 -> println("Volviendo al Menú Estudiante")
                    }
                } while (opcionCampos != 5)

                guardarAchivoEstudiante(estudiantes)
                println("Estudiante actualizado con éxito.")
            } else {
                println("No se encontró el estudiante con ID $idEstudiante.")
            }
        } else {
            println("Entrada no válida para el ID del estudiante.")
        }
    }

    fun eliminarEstudiante(id: Int){
        val estudiantes = obtenerEstudiantes().toMutableList()

        val estudianteEliminado = estudiantes.removeIf{it.id == id }
        if (estudianteEliminado){
            guardarAchivoEstudiante(estudiantes)
            println("Estudiante eliminado con exito")
        } else {
            println("NO se encontró el estudiante con ID $id")
        }
    }

    fun guardarAchivoEstudiante(listaEstudiante: List<estudiante>){
        estudiantesFile.writeText("")
        listaEstudiante.forEach{
            estudiantesFile.appendText("${it.id} | ${it.nombre} | ${it.fechaNacimiento} | " +
                    "${it.gpa} | ${it.esResidente}\n")
        }
    }

}