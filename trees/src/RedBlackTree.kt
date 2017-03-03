public class RBNode(override var key: Int, override var value: Int, var color: Boolean): Node{

    var left: RBNode? = null
    var right: RBNode? = null
    var parent: RBNode? = null
    override var size: Int = 0

}

public class RedBlackTree(var root: RBNode?){

    public fun draw(){ //функция рисования дерева
        if (root == null){  //если корень не существует
            println("Дерево еще не создано")
            return
        }
        var queue: MutableList<RBNode?> = mutableListOf() //лист для вывода текущего уровня
        queue.add(root)
        var isPrint = true
        var indent = 50 //регулировка кривости 1.0
        while (isPrint){
            isPrint = false
            indent = (2 * indent - 1) / 3   //регулировка кривости 2.0
            var new_queue: MutableList<RBNode?> = mutableListOf() //следующий уровень
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
                    print("(${queue[i]?.key}/${queue[i]?.value}/${if (queue[i]?.color == true) "RED" else "BLACK"})")  //вывели значение
                    new_queue.add(queue[i]?.left)   //добавили детей
                    new_queue.add(queue[i]?.right)
                }
            }
            println()
            queue = new_queue
        }
    }

    public fun check(checking: Int?): Boolean{  //функция проверки наличия элемента
        if (checking == null){
            return false
        }
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

    private fun rotate_right(start: RBNode?): RBNode?{
        if (start == null){
            return null
        }
        var res = start?.left
        if (start.left?.right == null)
            start.left = null
        else
            start.left = start.left?.right
        res?.right = start
        return res
    }

    private fun rotate_left(start: RBNode?): RBNode?{
        if (start == null){
            return null
        }
        var res = start?.right
        if (start.right?.left == null)
            start.right = null
        else
            start.right = start.right?.left
        res?.left = start
        return res
    }

    public fun add(adding: RBNode?){   //функция добавления элемента
        if (adding == null){
            return
        }
        if (root == null){  //если корень не существует
            root = RBNode(adding.key, adding.value, false)
            return;
        }
        var cur = root  //отец нового элемента
        var prev = root  //дед нового элемента
        var side: Boolean = true  //направление от деда
        var cur_side: Boolean  //направление от отца
        while (true){
            ++cur!!.size
            if (cur.key < adding.key){  //если больше текущего, то идем вправо
                if (cur.right == null){ //если справа пусто, то ставим туда новую вершину
                    cur.right = RBNode(adding.key, adding.value, true)
                    cur.right?.parent = cur
                    cur_side = false
                    break
                }
                else{   //иначе переходим в правое поддерево
                    prev = cur
                    side = false
                    cur = cur.right
                }
            }
            else{    //если меньше или равно, то идем влево
                if (cur.left == null){  //если слева пусто, то ставим туда новую вершину
                    cur.left = RBNode(adding.key, adding.value, true)
                    cur.left?.parent = cur
                    cur_side = true
                    break
                }
                else{   //иначе переходим влево
                    prev = cur
                    side = true
                    cur = cur.left
                }
            }
        }
        if (cur?.color == false){    //балансировка
            return  //если отец нового элемента черный
        }
        else{   //если отец нового элемента красный
            var uncle_color: Boolean    //определяем цвет дяди
            if (side == true){
                if (prev?.right == null)
                    uncle_color = false
                else
                    uncle_color = prev?.right?.color!!
            }
            else{
                if (prev?.left == null)
                    uncle_color = false
                else
                    uncle_color = prev?.left?.color!!
            }
            if (uncle_color == true){   //если дядя красный
                if (prev?.right != null) prev?.right?.color = false
                if (prev?.left != null) prev?.left?.color = false
                if (prev != root) prev?.color = true
            }
            else{   //если дядя черный
                if (side == true){  //случай левого потомка деда
                    if (cur_side == false){ //если правый потомок отца
                        prev?.left = rotate_left(cur)
                        cur_side = true
                        cur = prev?.left
                    }
                    cur?.color = false
                    prev?.color = true
                    if (prev?.parent != null){
                        prev?.parent?.left = rotate_right(prev)
                    }
                    else{
                        root = rotate_right(prev)
                    }
                }
                else{   //случай правого потомка деда
                    if (cur_side == true) {  //если левый потомок отца
                        prev?.right = rotate_right(cur)
                        cur_side = false
                        cur = prev?.right
                    }
                    cur?.color = false
                    prev?.color = true
                    if (prev?.parent != null){
                        prev?.parent?.right = rotate_left(prev)
                    }
                    else{
                        root = rotate_left(prev)
                    }
                }
            }
        }
    }

}