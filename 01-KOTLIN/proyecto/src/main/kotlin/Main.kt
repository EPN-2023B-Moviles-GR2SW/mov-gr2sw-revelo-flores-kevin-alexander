import java.util.*

fun main(args: Array<String>) {
    println("Hello World!")

    // Variables INMUTABLES (NO se reasignan "=")
    val inmutable: String = "Kevin";
    // inmutable = "Vicente";

    // Variables Mutables (Re asignar)
    var mutable: String = "Vicente";
    mutable = "Kevin";

    //SIEMPRE USAREMOS VAL -> antes -> VAR
    // DUCK Typing (Suena como un pato, vuela como un pato, ...)
    var ejemploVariable = "Kevin Revelo" //Entiende el tipo de dato
    val edadEjemplo: Int = 12
    ejemploVariable.trim() //Metodos de los Strings
    // ejemploVariable = edadEjemplo;

    //Clases JAVA
    var fechaNacimiento: Date = Date()

    //SWITCH
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }

        "S" -> {
            println("Solgero")
        }

        else -> {
            println("No sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No" //If else no comun

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00, 20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00) //Named Parameters -> Para especificar
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00) //NO importa el orden

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null, 1)
    val SumaTres = Suma(1, null)
}

    abstract class NumerosJava{
        protected val numeroUno: Int
        protected val numeroDos: Int
        constructor(
            uno: Int,
            dos:Int
        ){//Bloque de codigo del constructor
            this.numeroUno = uno
            this.numeroDos = dos
            println("Inicializando")
        }
    }

    abstract class Numeros(//Constructor PRIMARIO
        // Ejemplo
        // uno: Int, (Parametro (Sin modificar acceso))
        // private var uno: Int, //Propiedad Publica Clase numeros.uno
        // var uno: Int, //Propiedad de la clase (por defecto PUBLIC)
        // public var uno: Int,
        protected val numeroUno: Int,
        protected val numeroDos: Int,
    ){
        // var cedula: String = "" (public es por defecto)
        // private valorCalculado: Int = 0 (private)
        init{ //Bloque constructor primario
            this.numeroUno; this.numeroDos; //this es OPCIONAL
            numeroUno; numeroDos; //Sin el "this", es lo mismo
            println("Inicializando");
        }
    }

    class Suma(
        unoParametro: Int, //Parametro
        dosParametro: Int, //Parametro
    ): Numeros(unoParametro, dosParametro){ //Extendiendo y mandando los parametros (super)
        init { //Bloque codigo constructor primario
            this.numeroUno
            this.numeroDos
        }
    constructor(//Segundo constructor
        uno: Int?,
        dos: Int
    ):this(
        if(uno == null) 0 else uno, dos
    )

    constructor(//Tercer constructor
        uno: Int, //Parametros
        dos: Int? //Parametros
    ):this(
        uno,
        if(dos == null) 0 else dos,
    )

    }





//Funciones
//void -> Unit (NO DEVUELVEN NADA)
//Estructura
fun imprimirNombre(nombre: String): Unit{
    // "Nombre : " + nombre...
    println("Nombre: ${nombre}") //Template strings
}

fun calcularSueldo(
    sueldo: Double, //Requerido -> Tiene que mandarlos
    tasa: Double =12.00, // Opcional -> Si desea o no
    bonoEspecial: Double? = null, //Opcion null -> NULLABLE -> Puede tener el valor de nulo (?)
): Double{//Ponemos lo que devolvemos
    // Int -> Int? (nullable)
    // String -> String?(nullable)
    // Date -> Date? (nullable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) + bonoEspecial
    }
}