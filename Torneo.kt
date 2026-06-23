
val nombres = Array(100) { "" }
val puntos = IntArray(100)
var cantidad = 0  

fun main() {
    var opcion = 0

    while (opcion != 6) {
        mostrarMenu()
        opcion = leerOpcion()

        if (opcion == 1) {
            registrarParticipante()
        } else if (opcion == 2) {
            registrarPuntos()
        } else if (opcion == 3) {
            consultarParticipante()
        } else if (opcion == 4) {
            mostrarEstadisticas()
        } else if (opcion == 5) {
            mostrarSobrePromedio()
        } else if (opcion == 6) {
            println("\nFinalizando el sistema. Hasta pronto!")
        } else {
            println("\nOpcion no valida. Intente nuevamente.")
        }
    }
}

fun mostrarMenu() {
    println("\n=====================================")
    println("     BIENVENIDO AL TORNEO KOF")
    println("=====================================")
    println("1. Registrar participante")
    println("2. Registrar puntos")
    println("3. Consultar participante")
    println("4. Mostrar estadisticas del torneo")
    println("5. Mostrar participantes sobre el promedio")
    println("6. Finalizar programa")
    print("Seleccione una opcion: ")
}

fun leerOpcion(): Int {
    val entrada = readLine()
    if (entrada == null || entrada.trim() == "") {
        return 0
    }
    val numero = entrada.trim().toIntOrNull()
    if (numero == null) {
        return 0
    }
    return numero
}

fun buscarParticipante(nombre: String): Int {
    var posicion = -1
    for (i in 0 until cantidad) {
        if (nombres[i].lowercase() == nombre.lowercase()) {
            posicion = i
        }
    }
    return posicion
}

fun registrarParticipante() {
    print("\nIngrese el nombre del participante: ")
    val entrada = readLine()
    val nombre = entrada?.trim() ?: ""

    if (nombre == "") {
        println("Error: el nombre no puede estar vacio.")
        return
    }

    if (buscarParticipante(nombre) != -1) {
        println("Error: el participante '" + nombre + "' ya esta registrado.")
        return
    }

    nombres[cantidad] = nombre
    puntos[cantidad] = 0
    cantidad = cantidad + 1
    println("Participante '" + nombre + "' registrado correctamente.")
}

fun registrarPuntos() {
    if (cantidad == 0) {
        println("\nNo hay participantes registrados.")
        return
    }

    print("\nIngrese el nombre del participante: ")
    val entrada = readLine()
    val nombre = entrada?.trim() ?: ""

    val posicion = buscarParticipante(nombre)
    if (posicion == -1) {
        println("Error: el participante '" + nombre + "' no existe.")
        return
    }

    print("Ingrese los puntos a registrar: ")
    val entradaPuntos = readLine()
    val nuevosPuntos = entradaPuntos?.trim()?.toIntOrNull()

    if (nuevosPuntos == null || nuevosPuntos <= 0) {
        println("Error: los puntos deben ser un numero mayor que cero.")
        return
    }

    puntos[posicion] = puntos[posicion] + nuevosPuntos
    println("Se registraron " + nuevosPuntos + " puntos a '" + nombres[posicion] + "'.")
    println("Total acumulado: " + puntos[posicion] + " puntos.")
}

fun consultarParticipante() {
    if (cantidad == 0) {
        println("\nNo hay participantes registrados.")
        return
    }

    print("\nIngrese el nombre del participante: ")
    val entrada = readLine()
    val nombre = entrada?.trim() ?: ""

    val posicion = buscarParticipante(nombre)
    if (posicion == -1) {
        println("Error: el participante '" + nombre + "' no existe.")
        return
    }

    println("\n--- Informacion del participante ---")
    println("Nombre: " + nombres[posicion])
    println("Total de puntos: " + puntos[posicion])
    println("Categoria: " + obtenerCategoria(puntos[posicion]))
}

fun mostrarEstadisticas() {
    if (cantidad == 0) {
        println("\nNo hay participantes registrados.")
        return
    }

    var totalPuntos = 0
    for (i in 0 until cantidad) {
        totalPuntos = totalPuntos + puntos[i]
    }

    val promedio = totalPuntos.toDouble() / cantidad

    var posMayor = 0
    var posMenor = 0
    for (i in 0 until cantidad) {
        if (puntos[i] > puntos[posMayor]) {
            posMayor = i
        }
        if (puntos[i] < puntos[posMenor]) {
            posMenor = i
        }
    }

    println("\n--- Estadisticas del torneo ---")
    println("Cantidad total de participantes: " + cantidad)
    println("Total de puntos acumulados: " + totalPuntos)
    println("Promedio de puntos por participante: " + "%.2f".format(promedio))
    println("Mayor puntuacion: " + nombres[posMayor] + " (" + puntos[posMayor] + " puntos)")
    println("Menor puntuacion: " + nombres[posMenor] + " (" + puntos[posMenor] + " puntos)")

    var leyendas = 0
    var expertos = 0
    var competidores = 0
    var novatos = 0
    for (i in 0 until cantidad) {
        val categoria = obtenerCategoria(puntos[i])
        if (categoria == "Leyenda") {
            leyendas = leyendas + 1
        } else if (categoria == "Experto") {
            expertos = expertos + 1
        } else if (categoria == "Competidor") {
            competidores = competidores + 1
        } else {
            novatos = novatos + 1
        }
    }

    println("\nParticipantes por categoria:")
    println("  Leyenda: " + leyendas)
    println("  Experto: " + expertos)
    println("  Competidor: " + competidores)
    println("  Novato: " + novatos)
}

fun mostrarSobrePromedio() {
    if (cantidad == 0) {
        println("\nNo hay participantes registrados.")
        return
    }

    var totalPuntos = 0
    for (i in 0 until cantidad) {
        totalPuntos = totalPuntos + puntos[i]
    }
    val promedio = totalPuntos.toDouble() / cantidad

    println("\n--- Participantes sobre el promedio (" + "%.2f".format(promedio) + ") ---")
    var hayDestacados = false
    for (i in 0 until cantidad) {
        if (puntos[i] > promedio) {
            println("  " + nombres[i] + ": " + puntos[i] + " puntos")
            hayDestacados = true
        }
    }
    if (!hayDestacados) {
        println("Ningun participante supera el promedio general.")
    }
}

fun obtenerCategoria(puntosParticipante: Int): String {
    if (puntosParticipante >= 1000) {
        return "Leyenda"
    } else if (puntosParticipante >= 500) {
        return "Experto"
    } else if (puntosParticipante >= 200) {
        return "Competidor"
    } else {
        return "Novato"
    }
}
