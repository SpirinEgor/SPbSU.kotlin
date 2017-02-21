import java.util.Scanner

class Node(var value: Int){   //класс вершины
    var left: Node? = null  //ссылка на левое поддерево
    var right: Node? = null //ссылка на правое поддерево
    var size: Int = 1   //размер дерева от этой вершины включительно
}

class BinarySearchTree(var root: Node?){ //класс бинарного дерево поиска
   
    fun draw(){ //функция рисования дерева
        if (root == null){  //если корень не существует
            println("Дерево еще не создано")
            return
        }
        var queue: MutableList<Node?> = mutableListOf() //лист для вывода текущего уровня
        queue.add(root)
        var isPrint = true
        var indent = 50 //регулировка кривости 1.0
        while (isPrint){
            isPrint = false
            indent = (2 * indent - 1) / 3   //регулировка кривости 2.0
            var new_queue: MutableList<Node?> = mutableListOf() //следующий уровень
            for (i in 0..queue.size - 1){
                for (j in 1..indent)    //отступ
                    print(" ")
                if (queue[i] == null){  //если нет вершины, то пропуск
                    print(" ")
                    new_queue.add(null) //и добавляем null
                    new_queue.add(null)
                }
                else{
                    isPrint = true  
                    print(queue[i]?.value)  //вывели значение
                    new_queue.add(queue[i]?.left)   //добавили детей
                    new_queue.add(queue[i]?.right)
                }
            }
            println()
            queue = new_queue
        }
    }

    fun add(adding: Int){   //функция добавления элемента
        if (root == null){  //если корень не существует
            root = Node(adding)
            return;
        }
        var cur = root
        while (true){
            ++cur!!.size
            if (cur.value < adding){  //если больше текущего, то идем вправо
                if (cur.right == null){ //если справа пусто, то ставим туда новую вершину
                    cur.right = Node(adding)
                    return
                }
                else{   //иначе переходим в правое поддерево
                    cur = cur.right
                }
            }
            else{    //если меньше или равно, то идем влево
                if (cur.left == null){  //если слева пусто, то ставим туда новую вершину
                    cur.left = Node(adding)
                    return
                }
                else{   //иначе переходим влево
                    cur = cur.left
                }
            }
        }
    }

    fun remove(removing: Int){ //функция удаления элемента
        if (root == null){  //если корня не существует
            return
        }
        var cur = root
        var prev: Node? = null
        var side = true
        while(true){
            if (cur == null){   //если элемент не найден
                return
            }
            else if (cur.value < removing){ //спускаемся вправо
                prev = cur
                cur = cur.right
                side = false
            }
            else if (cur.value > removing){ //спускаемся влево
                prev = cur
                cur = cur.left
                side = true
            }
            else{   //нашли нужный элемент
                if (cur.left == null && cur.right == null){ //если оба ребенка null, то у родителя ставим ссылку на null
                    if (prev != null){
                        if (side == true){
                            prev.left = null
                        }
                        else{
                            prev.right = null
                        }
                    }
                    else{   //если нет родителя, то корень, значит все дерево null
                        root = null
                    }
                    return
                }
                else if (cur.left == null || cur.right == null){    //если один из ребенков null, то у родителя заменяем ссылку на существующего, 
                    if (cur.left != null){                          //если нет родителя, то ставим новый корень
                        if (prev != null){
                            if (side == true){
                                prev.left = cur.left
                            }
                            else{
                                prev.right = cur.left
                            }
                        }
                        else{   
                            root = cur.left
                        }
                    }
                    else{
                         if (prev != null){
                            if (side == true){
                                prev.left = cur.right
                            }
                            else{
                                prev.right = cur.right
                            }
                        }
                        else{
                            root = cur.right
                        }
                    }
                    return
                }
                else{ //если есть оба ребенка, то левое поддерево прикрепляем к самому левому элементу правого поддерева, дальше по 2 случаю
                    val tmp = cur
                    cur = cur.right
                    while (cur?.left != null){
                        cur = cur?.left
                    }
                    cur?.left = tmp.left
                    if (prev != null){
                        if (side == true){
                            prev.left = cur
                        }
                        else{
                            prev.right = cur
                        }
                    }
                    else{
                        root = cur
                    }
                    return
                }
            }
        }
    }

    fun check(checking: Int): Boolean{  //функция проверки наличия элемента
        var cur = root
        while (true){
            if (cur == null){   //отсутствует
                return false
            }
            else{
                if (cur.value < checking){  //спускаемся вправо
                    cur = cur.right
                }
                else if (cur.value > checking){ //спускаемся влево
                    cur = cur.left
                }
                else{   //наличие
                    return true
                }
            }
        }
    }

}

fun main(args: Array<String>){
    val read = Scanner(System.`in`)

    var root: Node? = null
    val tree = BinarySearchTree(root)
   
    println("Для управления деревом используйте: \n 1 x - добавить x \n 2 x - удалить x \n 3 x - проверить наличие x \n 4 - нарисовать дерево\n 5 - выход")
    var com: Int
    var x: Int = 0
    while (true){
        com = read.nextInt()
        if (com >= 1 && com <= 3){
            x = read.nextInt()
        }
        when (com){
            1 -> tree.add(x)
            2 -> tree.remove(x)
            3 -> println(tree.check(x))
            4 -> tree.draw()
            else -> return 
        }
    }
}
