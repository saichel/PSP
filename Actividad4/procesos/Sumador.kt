package es.educacyl.dam.ut1.procesos
import kotlin.system.exitProcess

fun main (args:Array<String>) {
    val Valor1 = args[0].toInt()
    val Valor2 = args[1].toInt()

    var resultado = 0

    for (i in Valor1..Valor2) {
        resultado+=i
    }
    print(resultado)
    System.out.flush()//fuerza la salida al archivo


}