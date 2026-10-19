package es.educacyl.dam.ut1.lanzadores

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import javax.annotation.processing.ProcessingEnvironment

fun main() {
    val path= Paths.get("./src/main/kotlin/es/educacyl/dam/ut1")
    creador(100,4,path)
    var sumaTotal=0
    for (i in 1..4){
        val ruta="./src/main/kotlin/es/educacyl/dam/ut1/resultados/fiche$i.txt"
        //usamos el singleton
        val resultadoParcial=UtilidadesFicheros.leerEntero(ruta)
        sumaTotal +=resultadoParcial
    }
    print("El resultados es: $sumaTotal")
}
fun creador(elem:Int, procesos:Int, path: Path,){
    val rangos=repartidora2(100,4)
    val classPath=System.getProperty("java.class.path")
    //creamos el directorioPadre en la ruta
    val dirResultados= Files.createDirectory(path.resolve("resultados"))
    //creamos la lista de los procesos que vamos a lanzar
    val listaProcesos=mutableListOf<Process>()

    // este bucle hara todo dependiendo de los procesos que se quieran mandar
    for (i in 0 until procesos){
        val par=rangos[i]
        val rango=listOf(par.first.toString(),par.second.toString())

        //definimos las rutas dentro del nuevo fichero,de los archivos txt
        val ruta=dirResultados.resolve("fiche${i+1}.txt")

        //creamos los archivos fisicos si no existen previamente
        if(!Files.exists(ruta))Files.createFile(ruta)

        //creamos el comando que se reutilizara
        val command=mutableListOf("java","-cp",classPath,
            "es.educacyl.dam.ut1.procesos.SumadorKt")
        command.addAll(rango)
        //creamos el proceso
        val proceso= ProcessBuilder(command)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .redirectOutput(ProcessBuilder.Redirect.to(ruta.toFile())
            ).start()
        listaProcesos.add(proceso)
    }
    for (proceso in listaProcesos){
        proceso.waitFor()
    }
    println("Han terminado todos los procesos")



}
fun repartidora2(NElem: Int,NProcesos:Int):List<Pair<Int,Int>>{
   if (NProcesos>=NElem) {
        print("No se puede calcular ")
        return emptyList()
   }
    val espacio:Int=NElem/NProcesos
    var resto=NElem%NProcesos

    var inicio=1
    return List(NProcesos){ i->
        val fin =inicio+espacio-1+if(i<resto)1 else 0
        val rango =Pair(inicio,fin)
        inicio=fin+1
        rango
    }

}
    /*FORMA MANUAL
    val rango1 =arrayListOf<Int>()
    rango1.add(1)
    rango1.add(25)
    val rango2 =arrayListOf<Int>()
    rango2.add(26)
    rango2.add(50)
    val rango3=arrayListOf<Int>()
    rango3.add(51)
    rango3.add(75)
    val rango4=arrayListOf<Int>()
    rango4.add(76)
    rango4.add(100)
    /forma con funcion pero manual y no sirve si varia el numero de elementos
    val rangos=repartidora(100,3)
    val rango1=rangos[0]
    val rango2=rangos[1]
    val rango3=rangos[2]

    print(rangos)
}
fun repartidora(NElementos:Int,NProcesos:Int): ArrayList<Pair<Int,Int>> {
    val rangos=ArrayList<Pair<Int,Int>>()
    if (NProcesos > NElementos){ print("no se puede calcular")
    }else{
        val espacio:Int=NElementos/NProcesos//comprobar si es decimal
        rangos.add(Pair(1,espacio))
        val primero2:Int=(espacio+1)
        val ultimo2:Int=primero2+espacio
        rangos.add(Pair(primero2,ultimo2))
        val primero3:Int=ultimo2+1
        val ultimo3:Int=primero3+espacio
        rangos.add(Pair(primero3,ultimo3))

    }
    return rangos
}*/