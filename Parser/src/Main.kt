import Controller.*
import Model.*

fun main(args: Array<String>){
    val model = BMP_model()
    val controller = BMP_controller()
    var filename: String = ""
    if (args.isEmpty())
        println("Didn't find any args")
    else
        filename = args[0]
    if (filename == "") {
        println("Enter filename")
        filename = readLine()!!
    }
    if (controller.Validator(filename)){
        val result = controller.PassData(model, filename)
        if (result != null)
            println(result)
    }
    else
        println("Expected .bmp found smth else")
}