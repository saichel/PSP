import java.io.File

object UtilidadesFicheros{
    //Singleton patron de diseño que garantiza una única instancia
    //de clase ,accedes directamente a sus metodos
    fun leerEntero(rutaFichero:String):Int{
        val fichero=File(rutaFichero)
        //lee el texto y devuelve un int
        val contenido=fichero.readText().trim()
        return contenido.toInt()
    }

}