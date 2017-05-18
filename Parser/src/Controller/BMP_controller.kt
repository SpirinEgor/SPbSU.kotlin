package Controller

import java.nio.file.Files
import java.nio.file.Paths

import Model.*

class BMP_controller: Controller{

    override fun Validator(path: String): Boolean = path.substring(path.length - 4) == ".bmp"

    override fun PassData(model: Model, path: String): String?{
        try{
            val model: BMP_model = BMP_model()
            return model.set(Files.readAllBytes(Paths.get(path)).toTypedArray())
        }
        catch (e: NoSuchFieldException){
            return("No such file")
        }
    }

}