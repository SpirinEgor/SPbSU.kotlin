import java.util.*

fun main(args: Array<String>){
    val read = Scanner(System.`in`)

    val tree: RedBlackTree<Int, Int> = RedBlackTree(null)

    println("Для управления деревом используйте: \n 1 x - добавить x \n " +
        "2 x - удалить x \n " +
        "3 x - проверить наличие x \n " +
        "4 - нарисовать дерево\n " +
        "5 - выход")
    var r: String
    var com: Int = 5
    var x: Int = 0
    while (true){
        r = read.next()
        var check: Boolean =    true
        try{
            com = Integer.parseInt(r)
        }
        catch (e: NumberFormatException){
            println("Используйте цифры для выбора команд")
            r = read.nextLine()
            continue
        }
        if (com in 1..3){
            r = read.next()
        }
        try{
            if (com in 1..3)
                x = Integer.parseInt(r)
        }
        catch (e: NumberFormatException){
            println("Дерево настроено только для работы с целыми числами")
            continue
        }
        when (com){
            1 -> tree.add(x, x)
            //2 -> tree.remove(x)
            3 -> println(tree.check(x))
            4 -> tree.draw()
            5 -> {
                for (i in tree){
                    println("qwe")
                }
            }
            else -> return
        }
    }

}
