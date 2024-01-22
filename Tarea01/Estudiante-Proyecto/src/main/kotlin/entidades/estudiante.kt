package entidades

import java.time.LocalDate

class estudiante (
    var id: Int,
    var nombre: String,
    var fechaNacimiento: LocalDate,
    var gpa: Double,
    var esResidente: Boolean
)
{
    override fun toString(): String {
        return "ID: $id, Nombre: $nombre, Fecha de Nacimiento: $fechaNacimiento, GPA: $gpa, Residente: $esResidente"
    }
}
