public class RedBlackTree<K : Comparable<K>, V>(var root: RedBlackTree.RBNode<K, V>?): Iterable<RedBlackTree.RBNode<K, V>>{

    public fun draw(){ //функция рисования дерева
        if (root == null){  //если корень не существует
            println("Дерево еще не создано")
            return
        }
        var queue: MutableList<RedBlackTree.RBNode<K, V>?> = mutableListOf() //лист для вывода текущего уровня
        queue.add(root!!)
        var isPrint = true
        var indent = 64 //регулировка кривости 1.0
        while (isPrint){
            isPrint = false
            indent = indent / 2;   //регулировка кривости 2.0
            var new_queue: MutableList<RedBlackTree.RBNode<K, V>?> = mutableListOf() //следующий уровень
            for (i in 0..queue.size - 1){
                for (j in 1..indent)    //отступ
                    print(" ")
                if (queue[i] == null){  //если нет вершины, то пропуск
                    for (j in 1..10)
                        print(" ")
                    new_queue.add(null) //и добавляем null
                    new_queue.add(null)
                }
                else{
                    isPrint = true
                    print("(${queue[i]?.key}/${queue[i]?.value}/${if (queue[i]?.color == true) "RED" else "BLACK"}/$i)")  //вывели значение
                    new_queue.add(queue[i]?.left)   //добавили детей
                    new_queue.add(queue[i]?.right)
                }
            }
            println()
            queue = new_queue
        }
    }

    public fun check(checking: K?): kotlin.Boolean{  //функция проверки наличия элемента
        if (checking == null){
            return false
        }
        var dad = root
        while (true){
            if (dad == null){   //отсутствует
                return false
            }
            else{
                if (dad.key < checking){  //спускаемся вправо
                    dad = dad.right!!
                }
                else if (dad.key > checking){ //спускаемся влево
                    dad = dad.left!!
                }
                else{   //наличие
                    return true
                }
            }
        }
    }

    private fun rotate_right(start: RedBlackTree.RBNode<K, V>): RedBlackTree.RBNode<K, V> {
        if (start == null){
            return null!!
        }
        var res = start?.left
        res?.parent = start.parent
        if (start.left?.right == null)
            start.left = null
        else
            start.left = start.left?.right
        res?.right = start
        start.parent = res
        return res!!
    }

    private fun rotate_left(start: RedBlackTree.RBNode<K, V>): RedBlackTree.RBNode<K, V> {
        if (start == null){
            return null!!
        }
        var res = start?.right
        res?.parent = start.parent
        if (start.right?.left == null)
            start.right = null
        else
            start.right = start.right?.left
        res?.left = start
        start.parent = res
        return res!!
    }

    public fun add(key: K, value: V){   //функция добавления элемента
        if (root == null){  //если корень не существует
            root = RedBlackTree.RBNode(key, value, false)
            return;
        }
        var dad = root  //отец нового элемента
        var grdad = root  //дед нового элемента
        var dir_grdad: kotlin.Boolean = true  //направление от деда
        var dir_dad: kotlin.Boolean  //направление от отца
        while (true){
            ++dad!!.size
            if (dad.key < key){  //если больше текущего, то идем вправо
                if (dad.right == null){ //если справа пусто, то ставим туда новую вершину
                    dad.right = RedBlackTree.RBNode(key, value, true)
                    dad.right?.parent = dad
                    dir_dad = false
                    break
                }
                else{   //иначе переходим в правое поддерево
                    grdad = dad
                    dir_grdad = false
                    dad = dad.right!!
                }
            }
            else if (dad.key > key){    //если меньше, то идем влево
                if (dad.left == null){  //если слева пусто, то ставим туда новую вершину
                    dad.left = RedBlackTree.RBNode(key, value, true)
                    dad.left?.parent = dad
                    dir_dad = true
                    break
                }
                else{   //иначе переходим влево
                    grdad = dad
                    dir_grdad = true
                    dad = dad.left!!
                }
            }
            else{   //если равно, то такой элемент есть и ничего не меняем
                return
            }
        }
        if (dad?.color == false){    //балансировка
            return  //если отец нового элемента черный
        }
        else{   //если отец нового элемента красный
            var uncle_color: kotlin.Boolean    //определяем цвет дяди
            if (dir_grdad == true){
                if (grdad?.right == null)
                    uncle_color = false
                else
                    uncle_color = grdad?.right?.color!!
            }
            else{
                if (grdad?.left == null)
                    uncle_color = false
                else
                    uncle_color = grdad?.left?.color!!
            }
            if (uncle_color == true){   //если дядя красный
                if (grdad?.right != null) grdad?.right?.color = false
                if (grdad?.left != null) grdad?.left?.color = false
                if (grdad != root) grdad?.color = true
            }
            else{   //если дядя черный
                if (dir_grdad == true){  //случай левого потомка деда
                    if (dir_dad == false){ //если правый потомок отца
                        dad?.parent?.left = rotate_left(dad!!)
                        dir_dad = true
                        dad = dad?.parent?.left!!
                    }
                    dad?.color = false
                    grdad?.color = true
                    if (grdad?.parent != null){
                        grdad?.parent?.left = rotate_right(grdad!!)
                    }
                    else{
                        root = rotate_right(grdad!!)
                    }
                }
                else{   //случай правого потомка деда
                    if (dir_dad == true) {  //если левый потомок отца
                        dad?.parent?.right = rotate_right(dad!!)
                        dir_dad = false
                        dad = dad?.parent?.right!!
                    }
                    dad?.color = false
                    grdad?.color = true
                    if (grdad?.parent != null){
                        grdad?.parent?.right = rotate_left(grdad!!)
                    }
                    else{
                        root = rotate_left(grdad!!)
                    }
                }
            }
        }
    }

    override fun iterator(): Iterator<RedBlackTree.RBNode<K, V>> = RedBlackTree.RedBlackTreeIterator(root)

}