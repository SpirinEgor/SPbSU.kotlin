import java.util.Scanner

import RedBlackTree.*
import BinarySearchTree.*
import BTree.*

fun BST_iterate(tree: BinarySearchTree<Int, Int>) {
    for (node in tree) {
        node.print()
        println()
    }
}

fun RBT_iterate(tree: RedBlackTree<Int, Int>) {
    for (node in tree) {
        node.print()
        println()
    }
}

fun BT_iterate(tree: BTree<Int, Int>) {
    for (node in tree) {
        node.print()
        println()
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
        var tr: Int = 0
        val tree: Tree<Int, Int>
        var t: Int = 5
        while (true) {
            r = read.next()
            try {
                tr = Integer.parseInt(r)
            } catch (e: NumberFormatException) {
                println("Используйте цифры для выбора команд")
                r = read.nextLine()
                continue
            }
            if (tr in 0..3)
                break
            else
                println("Используйте числа 0..3")
        }
        if (tr == 0)
            return
        if (tr == 3){
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
        when (tr) {
            1 -> tree = BinarySearchTree<Int, Int>(null)
            2 -> tree = RedBlackTree<Int, Int>(null)
            3 -> tree = BTree<Int, Int>(null, t)
            else -> return
        }
        loop@ while (true) {
            var com: Int
            var x: Int = 0
            r = read.next()
            try {
                com = Integer.parseInt(r)
            } catch (e: NumberFormatException) {
                println("Используйте цифры для выбора команд")
                r = read.nextLine()
                continue
            }
            if (com in 1..3) {
                r = read.next()
            }
            try {
                if (com in 1..3)
                    x = Integer.parseInt(r)
            } catch (e: NumberFormatException) {
                println("Дерево настроено только для работы с целыми числами")
                continue
            }
            when (com) {
                1 -> tree.add(x, x)
                2 -> tree.remove(x)
                3 -> {
                    val found = tree.check(x)
                    println(if (!found) "NO" else "YES")
                }
                4 -> tree.draw()
                5 -> {
                    when(tr) {
                        1 -> BST_iterate(tree as BinarySearchTree<Int, Int>)
                        2 -> RBT_iterate(tree as RedBlackTree<Int, Int>)
                        3 -> BT_iterate(tree as BTree<Int, Int>)
                    }
                }
                else -> break@loop
            }
        }
    }
}