import RedBlackTree.*
import BinarySearchTree.*
import BTree.*
import java.util.*

fun work_with_BSTree(read: Scanner){
    val tree: BinarySearchTree<Int, Int> = BinarySearchTree(null)
    var r: String
    var com: Int
    var x: Int = 0
    while (true){
        r = read.next()
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
            2 -> tree.remove(x)
            3 -> {
                val found = tree.check(x)
                println(if (!found) "NO" else "YES")
            }
            4 -> tree.draw()
//            5 -> {
//                for (i in tree){
//                    println("(${i.key}/${i.value}/${if (i.color == true) "RED" else "BLACK"})")
//                }
//            }
            else -> return
        }
    }
}

fun work_with_RBTree(read: Scanner){
    val tree: RedBlackTree<Int, Int> = RedBlackTree(null)
    var r: String
    var com: Int
    var x: Int = 0
    while (true){
        r = read.next()
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
            2 -> tree.remove(x)
            3 -> {
                val found = tree.check(x)
                println(if (!found) "NO" else "YES")
            }
            4 -> tree.draw()
            5 -> {
                for (i in tree){
                    println("(${i.key}/${i.value}/${if (i.color == true) "RED" else "BLACK"})")
                }
            }
            else -> return
        }
    }
}

fun work_with_BTree(read: Scanner, t: Int){
    val tree: BTree<Int, Int> = BTree(null, t)
    var r: String
    var com: Int
    var x: Int = 0
    while (true){
        r = read.next()
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
            2 -> tree.remove(x)
            3 -> {
                val found = tree.check(x)
                println(if (!found) "NO" else "YES")
            }
            4 -> tree.draw()
//            5 -> {
//                for (i in tree){
//                    println("(${i.key}/${i.value}/${if (i.color == true) "RED" else "BLACK"})")
//                }
//            }
            else -> return
        }
    }
}

fun main(args: Array<String>){
    val read = Scanner(System.`in`)
    while (true) {
        println("Выберите дерево, с которым хотите работать\n" +
                "1 - Binary Search Tree\n" +
                "2 - Red-Black Tree\n" +
                "3 - B-tree\n" +
                "0 - выход")
        var r: String
        var tree: Int
        var t: Int = 5
        while (true) {
            r = read.next()
            try {
                tree = Integer.parseInt(r)
            } catch (e: NumberFormatException) {
                println("Используйте цифры для выбора команд")
                r = read.nextLine()
                continue
            }
            if (tree in 0..3)
                break
            else
                println("Используйте числа 0..3")
        }
        if (tree == 0)
            return
        if (tree == 3){
            print("Введите минимальную степень дерева: ")
            while (true) {
                r = read.next()
                try {
                    t = Integer.parseInt(r)
                } catch (e: NumberFormatException) {
                    println("Используйте цифры для выбора команд")
                    r = read.nextLine()
                    continue
                }
                if (t >= 2)
                    break
                else
                    println("Минимальная степень дерева - 2")
            }
            println()
        }
        println("Для управления деревом используйте: \n 1 x - добавить x \n " +
                "2 x - удалить x \n " +
                "3 x - проверить наличие x \n " +
                "4 - нарисовать дерево\n " +
                "5 - вывод итератором\n " +
                "0 - выход")
        when (tree) {
            1 -> work_with_BSTree(read)
            2 -> work_with_RBTree(read)
            3 -> work_with_BTree(read, t)
            else -> return
        }
    }
}