package entidades

import java.time.LocalDate

class proyecto (
    var id: Int,
    var nombre: String,
    var fechaInicio: LocalDate,
    var presupuesto: Double,
    var proyectoActivo: Boolean,
    var listEstudiante: MutableList<estudiante> = mutableListOf()
) {
    override fun toString(): String {
        var estudiantes = listEstudiante.joinToString(separator = ", ") { it.nombre }
        return "ID: $id, Nombre: $nombre, Fecha de Inicio: $fechaInicio, Presupuesto: $presupuesto, " +
                "Activo: $proyectoActivo, Estudiantes: [$estudiantes]"
    }

}
