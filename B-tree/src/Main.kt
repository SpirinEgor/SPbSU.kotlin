import java.util.*

fun main(args: Array<String>){
    val read = Scanner(System.`in`)
    println("Введите t - минимальная степень B-дерева")
    var s: String
    var t: Int = 0
    while (t < 2) {
        s = read.next()
        try {
            t = Integer.parseInt(s)
        } catch (e: NumberFormatException) {
            println("t - должно быть натуральным числом в промежутке (2; 2^31)")
            s = read.nextLine()
            continue
        }
    }
    val tree: Tree<Int, Int> = Tree<Int, Int>(null, t)
    println("Для управления деревом используйте: \n 1 x - добавить x \n " +
            "2 x - удалить x \n " +
            "3 x - проверить наличие x \n " +
            "4 - нарисовать дерево\n " +
            "5 - вывод итератором\n " +
            "6 - выход")
    var com: Int
    var x: Int = 0
    while (true){
        s = read.next()
        var check: Boolean =    true
        try{
            com = Integer.parseInt(s)
        }
        catch (e: NumberFormatException){
            println("Используйте цифры для выбора команд")
            s = read.nextLine()
            continue
        }
        if (com in 1..3){
            s = read.next()
        }
        try{
            if (com in 1..3)
                x = Integer.parseInt(s)
        }
        catch (e: NumberFormatException){
            println("Дерево настроено только для работы с целыми числами")
            continue
        }
        when (com){
            1 -> tree.insert(x, x)
        //2 -> tree.remove(x)
            3 -> println(tree.search(x))
            4 -> tree.draw()
            5 -> {
                for (i in tree){
                    var cur: String = ""
                    if (i.leaf) cur = "leaf, keys: "
                    else cur = "not leaf, keys: "
                    for (k in i.keys) {
                        cur += k.first
                        if (k != i.keys.last())
                            cur += ", "
                    }
                    println(cur)
                }
            }
            else -> return
        }
    }
}