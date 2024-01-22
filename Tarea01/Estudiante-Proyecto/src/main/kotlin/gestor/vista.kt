package gestor
import dao.estudianteDAO
import dao.proyectoDAO
import entidades.estudiante
import entidades.proyecto
import java.time.LocalDate
import java.util.*

class vista {
    private val estudianteDAO = estudianteDAO()
    private val scanner = Scanner(System.`in`)
    private val proyectoDAO = proyectoDAO()
    fun iniciar(){
        var opcion1: Int

        do{
            println("Menú Principal:")
            println("1. Estudiante")
            println("2. Proyecto")
            println("3. Salir")
            print("Ingrese la opción deseada: ")

            opcion1 = scanner.nextInt()
            when(opcion1){
                1 -> gestionarEstudiante()
                2 -> gestionarProyecto()
                3 -> println("Chauuu")
                else -> println("Opción no válida: Intente de nuevo")
            }
        }while (opcion1 != 3)
    }

    private fun gestionarEstudiante(){
        var opcion2: Int
        do{
            println("Menú Estudiante:")
            println("1. Agregar Estudiante")
            println("2. Ver Estudiantes")
            println("3. Actualizar Estudiante")
            println("4. Eliminar Estudiante")
            println("5. Volver al Menú Principal")
            print("Ingrese la opción deseada: ")

            opcion2 = scanner.nextInt()

            when(opcion2){
                1 -> agregarEstudiante()
                2 -> obtenerEstudiantes()
                3 -> estudianteDAO.actualizarEstudiante()
                4 -> eliminarEstudiantes()
                5 -> println("Chauuu")
                else -> println("Opción no válida: Intente de nuevo")
            }
            println()
        }while (opcion2 != 5)
    }

    private fun gestionarProyecto(){
        var opcion2: Int
        do{
            println("Menú Proyecto:")
            println("1. Agregar Proyecto")
            println("2. Ver Proyectos")
            println("3. Actualizar Proyectos")
            println("4. Eliminar Proyectos")
            println("5. Volver al Menú Principal")
            print("Ingrese la opción deseada: ")

            opcion2 = scanner.nextInt()

            when(opcion2){
                1 -> agregarProyecto()
                2 -> obtenerProyecto()
                3 -> actualizarProyecto()
                4 -> eliminarProyecto()
                5 -> println("Chauuu")
                else -> println("Opción no válida: Intente de nuevo")
            }
            println()
        }while (opcion2 != 5)
    }

    private fun agregarEstudiante(){
        println("NUEVO ESTUDIANTE")
        println("ID: ")
        val idEstudiante = scanner.nextInt()
        println("Ingrese el nombre del estudiante:")
        val nombre = scanner.next()
        println("Ingrese el fecha de nacimiento del estudiante:")
        val fechaNacimientoStr = scanner.next()
        val fechaNacimiento = LocalDate.parse(fechaNacimientoStr)
        println("Ingrese el GPA del estudiante:")
        val gpa = scanner.nextDouble()
        println("¿Es residente? (true/false):")
        val esResidente = scanner.nextBoolean()

        val nuevoEstudiante = estudiante(
            id = idEstudiante,
            nombre = nombre,
            fechaNacimiento = fechaNacimiento,
            gpa = gpa,
            esResidente = esResidente
        )
        estudianteDAO.agregarEstudiante(nuevoEstudiante)
        println("Estudiante agregado con éxito")
        scanner.nextLine() //Limpiar el buffer del scanner
    }
    private fun obtenerEstudiantes(){
        println("ESTUDIANTES")
        val estudiantes = estudianteDAO.obtenerEstudiantes()

        if(estudiantes.isEmpty()){
            println("No hay estudiantes ingresados")
        } else{
            estudiantes.forEach{ println(it) }
        }
    }

    private fun eliminarEstudiantes(){
        val estudiantes = estudianteDAO.obtenerEstudiantes()
        println("---------Lista Estudiantes-----------")
        estudiantes.forEach{println(it)}
        println("---------------")
        print("Ingrese el ID del estudiante que desea eliminar: ")
        val idEstudiante = scanner.nextInt()
        estudianteDAO.eliminarEstudiante(idEstudiante)
        println("Estudiante eliminado con éxito")
    }

    private fun agregarProyecto(){
        println("NUEVO PROYECTO")
        println("ID del proyecto: ")
        val idProyecto = scanner.nextInt()
        println("Ingrese nombre del proyecto:")
        val nombreProyecto = scanner.next()
        println("Ingrese fecha de inicio del proyecto: ")
        val fechaInicio = LocalDate.parse(scanner.next())
        println("Ingrese el presupuesto: ")
        val presupuesto = scanner.nextDouble()
        println("Proyecto Activo (true/false)")
        val proyectoActivo = scanner.nextBoolean()

        val estudiantesAsociados = obtenerEstudiantesAsociados()
        val nuevoProyecto = proyecto(idProyecto, nombreProyecto, fechaInicio, presupuesto, proyectoActivo, estudiantesAsociados)
        proyectoDAO.agregarProyecto(nuevoProyecto)
        proyectoDAO.guardarAchivoProyecto(proyectoDAO.obtenerProyectos())
    }

    private fun obtenerEstudiantesAsociados(): MutableList<estudiante> {
        println("Ingrese los IDs de los estudiantes asociados al proyecto (separados por espacios): ")

        val idsEstudiantes = readLine()?.split(" ")?.mapNotNull { it.toIntOrNull() } ?: emptyList()
        val estudiantes = estudianteDAO.obtenerEstudiantes()

        return idsEstudiantes.mapNotNull { id ->
            estudianteDAO.obtenerEstudiantePorId(estudiantes, id)
        }.toMutableList()
    }

    private fun obtenerProyecto(){
        val proyectos = proyectoDAO.obtenerProyectos()

        if (proyectos.isEmpty()) {
            println("No hay proyectos registrados.")
        } else {
            println("Lista de Proyectos:")
            for (proyecto in proyectos) {
                println("ID: ${proyecto.id}")
                println("Nombre: ${proyecto.nombre}")
                println("Fecha de Inicio: ${proyecto.fechaInicio}")
                println("Presupuesto: ${proyecto.presupuesto}")
                println("Proyecto Activo: ${proyecto.proyectoActivo}")

                if (proyecto.listEstudiante.isNotEmpty()) {
                    println("Estudiantes Asociados:")
                    for (estudiante in proyecto.listEstudiante) {
                        println("  - ID: ${estudiante.id}, Nombre: ${estudiante.nombre}")
                    }
                } else {
                    println("No hay estudiantes asociados a este proyecto.")
                }
                println("--------------")
            }
        }
    }
    private fun actualizarProyecto(){
        val proyectos = proyectoDAO.obtenerProyectos()
        println("---------Lista Proyectos-----------")
        proyectos.forEach{println(it)}
        println("---------------")

        println("Ingrese el ID del proyecto que desea actualizar: ")
        val idProyecto = scanner.nextInt()

        val proyectoActualizado = obtenerDatosProyecto()
        proyectoDAO.actualizarProyecto(idProyecto, proyectoActualizado)
    }
    private fun eliminarProyecto(){
        val proyectos = proyectoDAO.obtenerProyectos()
        println("---------Lista Proyectos-----------")
        proyectos.forEach{println(it)}
        println("---------------")
        println("Ingrese el ID del proyecto que desea eliminar: ")
        val idProyecto = scanner.nextInt()

        proyectoDAO.eliminarProyecto(idProyecto)
    }

    private fun obtenerDatosProyecto(): proyecto {
        println("Ingrese los nuevos datos del proyecto:")
        println("Ingrese nombre del proyecto:")
        val nombreProyecto = scanner.next()
        println("Ingrese fecha de inicio del proyecto (YYYY-MM-DD): ")
        val fechaInicio = LocalDate.parse(scanner.next())
        println("Ingrese el presupuesto: ")
        val presupuesto = scanner.nextDouble()
        println("Proyecto Activo (true/false)")
        val proyectoActivo = scanner.nextBoolean()
        val estudiantesAsociados = obtenerEstudiantesAsociados()
        return proyecto(0, nombreProyecto, fechaInicio, presupuesto, proyectoActivo, estudiantesAsociados)
    }
}