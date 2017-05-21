import Controller.*
import Model.*
import Observer.BMP_observer

fun main(args: Array<String>){
    val model = BMP_model(BMP_observer())
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
        filename = "./Pictures/" + filename
        val result = controller.PassData(model, filename)
        if (result != null)
            println(result)
    }
    else
        println("Expected .bmp found smth else")
}