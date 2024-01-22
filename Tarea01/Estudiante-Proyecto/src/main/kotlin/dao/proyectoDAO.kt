package dao

import entidades.estudiante
import entidades.proyecto
import java.io.File
import java.time.LocalDate
import java.util.*


class proyectoDAO {
    private val estudianteDAO = estudianteDAO()
    private val scanner = Scanner(System.`in`)
    private val proyectosFile = File("src/main/kotlin/archivos/proyecto.txt")

    fun agregarProyecto(proyecto: proyecto) {
        val idsEstudiantes = proyecto.listEstudiante.map { it.id }

        proyectosFile.appendText("${proyecto.id} | ${proyecto.nombre} | ${proyecto.fechaInicio} | " +
                "${proyecto.presupuesto} | ${proyecto.proyectoActivo} | " +
                "${idsEstudiantes.joinToString(" | ")}\n"
        )
    }

    fun obtenerProyectos(): List<proyecto> {
        val estudiantes = estudianteDAO.obtenerEstudiantes()
        return proyectosFile.readLines().mapNotNull {
            val campos = it.split(" | ")
            val proyectoId = campos[0].toInt()
            val proyecto = proyecto(
                proyectoId,
                campos[1],
                LocalDate.parse(campos[2]),
                campos[3].toDouble(),
                campos[4].toBoolean(),
                mutableListOf()
            )
            val idsEstudiantes = campos[5].split(" | ").mapNotNull {
                try {
                    it.toInt()
                } catch (e: NumberFormatException) {
                    println("Error al convertir a número: $it")
                    null
                }
            }

            proyecto.listEstudiante.addAll(estudiantesAsociados(estudiantes, idsEstudiantes))
            proyecto
        }
    }

    private fun estudiantesAsociados(estudiantes: List<estudiante>, idsEstudiantes: List<Int>): List<estudiante> {
        // Utiliza la función obtenerEstudiantePorId de tu DAO de Estudiante
        return idsEstudiantes.mapNotNull { estudianteId ->
            estudianteDAO.obtenerEstudiantePorId(estudiantes, estudianteId)
        }
    }

    fun actualizarProyecto(id: Int, proyectoActualizado: proyecto) {
        val proyectos = obtenerProyectos().toMutableList()
        val indice = proyectos.indexOfFirst { it.id == id }

        if (indice != -1) {
            proyectoActualizado.id = id
            proyectos[indice] = proyectoActualizado
            guardarAchivoProyecto(proyectos)
            println("Proyecto actualizado con éxito.")
        } else {
            println("No se encontró un proyecto con el ID $id.")
        }
    }

    fun eliminarProyecto(id: Int) {
        val proyectos = obtenerProyectos()
        val proyectosActualizados = proyectos.filter { it.id != id }

        if (proyectos.size > proyectosActualizados.size) {
            guardarAchivoProyecto(proyectosActualizados)
            println("Proyecto eliminado con éxito.")
        } else {
            println("No se encontró un proyecto con el ID $id.")
        }
    }

    fun guardarAchivoProyecto(proyectos: List<proyecto>){
        proyectosFile.writeText(proyectos.joinToString("\n") {
            "${it.id} | ${it.nombre} | ${it.fechaInicio} | ${it.presupuesto} | ${it.proyectoActivo} | ${it.listEstudiante.joinToString(" | ") { estudiante -> estudiante.id.toString() }}"
        })
    }


}