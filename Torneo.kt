
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
            println("\nEl torneo ha terminado. Hasta la proxima batalla, peleadores!")
        } else {
            println("\nMovimiento no valido. Elige una opcion del menu.")
        }
    }
}

fun mostrarMenu() {
    println("\n=====================================")
    println("     BIENVENIDO AL TORNEO KOF")
    println("=====================================")
    println("1. Registrar peleador")
    println("2. Registrar puntos de batalla")
    println("3. Consultar peleador")
    println("4. Mostrar estadisticas del torneo")
    println("5. Mostrar peleadores sobre el promedio")
    println("6. Finalizar torneo")
    print("Elige tu opcion: ")
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
    print("\nIngrese el nombre del peleador: ")
    val entrada = readLine()
    val nombre = entrada?.trim() ?: ""

    if (nombre == "") {
        println("Error: el nombre del peleador no puede estar vacio.")
        return
    }

    if (buscarParticipante(nombre) != -1) {
        println("Error: el peleador '" + nombre + "' ya esta inscrito en el torneo.")
        return
    }

    nombres[cantidad] = nombre
    puntos[cantidad] = 0
    cantidad = cantidad + 1
    println("Peleador '" + nombre + "' inscrito en el torneo. Que comience la batalla!")
}

fun registrarPuntos() {
    if (cantidad == 0) {
        println("\nAun no hay peleadores en el torneo.")
        return
    }

    print("\nIngrese el nombre del peleador: ")
    val entrada = readLine()
    val nombre = entrada?.trim() ?: ""

    val posicion = buscarParticipante(nombre)
    if (posicion == -1) {
        println("Error: el peleador '" + nombre + "' no esta inscrito.")
        return
    }

    print("Ingrese los puntos de batalla obtenidos: ")
    val entradaPuntos = readLine()
    val nuevosPuntos = entradaPuntos?.trim()?.toIntOrNull()

    if (nuevosPuntos == null || nuevosPuntos <= 0) {
        println("Error: los puntos deben ser un numero mayor que cero.")
        return
    }

    puntos[posicion] = puntos[posicion] + nuevosPuntos
    println("Victoria! Se sumaron " + nuevosPuntos + " puntos a '" + nombres[posicion] + "'.")
    println("Total acumulado: " + puntos[posicion] + " puntos.")
}

fun consultarParticipante() {
    if (cantidad == 0) {
        println("\nAun no hay peleadores en el torneo.")
        return
    }

    print("\nIngrese el nombre del peleador: ")
    val entrada = readLine()
    val nombre = entrada?.trim() ?: ""

    val posicion = buscarParticipante(nombre)
    if (posicion == -1) {
        println("Error: el peleador '" + nombre + "' no esta inscrito.")
        return
    }

    println("\n--- Ficha del peleador ---")
    println("Nombre: " + nombres[posicion])
    println("Puntos de batalla: " + puntos[posicion])
    println("Rango: " + obtenerCategoria(puntos[posicion]))
}

fun mostrarEstadisticas() {
    if (cantidad == 0) {
        println("\nAun no hay peleadores en el torneo.")
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

    println("\n--- Estadisticas del torneo KOF ---")
    println("Total de peleadores: " + cantidad)
    println("Puntos totales del torneo: " + totalPuntos)
    println("Promedio de puntos por peleador: " + "%.2f".format(promedio))
    println("Campeon actual: " + nombres[posMayor] + " (" + puntos[posMayor] + " puntos)")
    println("Peleador con menos puntos: " + nombres[posMenor] + " (" + puntos[posMenor] + " puntos)")

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

    println("\nPeleadores por rango:")
    println("  Leyenda: " + leyendas)
    println("  Experto: " + expertos)
    println("  Competidor: " + competidores)
    println("  Novato: " + novatos)
}

fun mostrarSobrePromedio() {
    if (cantidad == 0) {
        println("\nAun no hay peleadores en el torneo.")
        return
    }

    var totalPuntos = 0
    for (i in 0 until cantidad) {
        totalPuntos = totalPuntos + puntos[i]
    }
    val promedio = totalPuntos.toDouble() / cantidad

    println("\n--- Peleadores de elite (sobre el promedio de " + "%.2f".format(promedio) + ") ---")
    var hayDestacados = false
    for (i in 0 until cantidad) {
        if (puntos[i] > promedio) {
            println("  " + nombres[i] + ": " + puntos[i] + " puntos")
            hayDestacados = true
        }
    }
    if (!hayDestacados) {
        println("Ningun peleador supera el promedio general.")
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
