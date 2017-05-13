package RedBlackTree

import Tree

public class RedBlackTree<K : Comparable<K>, V>(var root: RBNode<K, V>?): Iterable<RBNode<K, V>>, Tree<K, V>{

    var nil: RBNode<K, V>? = null

    override fun iterator(): Iterator<RBNode<K, V>> = RedBlackTreeIterator(root, nil)

    public override fun draw(){ //функция рисования дерева
        if (root == null || root == nil){  //если корень не существует
            println("Дерево еще не создано")
            return
        }
        var queue: MutableList<RBNode<K, V>?> = mutableListOf() //лист для вывода текущего уровня
        queue.add(root!!)
        var isPrint = true
        var indent = 64 //регулировка кривости 1.0
        while (isPrint){
            isPrint = false
            indent = indent / 2;   //регулировка кривости 2.0
            var new_queue: MutableList<RBNode<K, V>?> = mutableListOf() //следующий уровень
            for (i in 0..queue.size - 1){
                for (j in 1..indent)    //отступ
                    print(" ")
                if (queue[i] == null || queue[i] == nil){  //если нет вершины, то пропуск
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

    public fun search(key: K): RBNode<K, V>?{  //функция проверки наличия элемента
        if (root == null)
            return null
        var cur = root
        while (cur != nil){
            if (key == cur!!.key)
                return cur
            else if (key < cur.key)
                cur = cur.left
            else
                cur = cur.right
        }
        return null
    }

    public override fun check(key: K): Boolean = search(key) != null

    private fun fix_add(add: RBNode<K, V>){
        var cur = add
        while (cur != root && cur.parent!!.color){
            if (cur.parent == cur.parent!!.parent!!.left){
                val grdad = cur.parent!!.parent!!.right
                if (grdad!!.color){
                    cur.parent!!.color = false
                    grdad.color = false
                    cur.parent!!.parent!!.color = true
                    cur = cur.parent!!.parent!!
                }
                else{
                    if (cur == cur.parent!!.right!!){
                        cur = cur.parent!!
                        root = cur.rotate_left(nil!!, root!!)
                    }
                    cur.parent!!.color = false
                    cur.parent!!.parent!!.color = true
                    root = cur.parent!!.parent!!.rotate_right(nil!!, root!!)
                }
            }
            else {
                val grdad = cur.parent!!.parent!!.left
                if (grdad!!.color) {
                    cur.parent!!.color = false
                    grdad.color = false
                    cur.parent!!.parent!!.color = true
                    cur = cur.parent!!.parent!!
                } else {
                    if (cur == cur.parent!!.left!!) {
                        cur = cur.parent!!
                        root = cur.rotate_right(nil!!, root!!)
                    }
                    cur.parent!!.color = false
                    cur.parent!!.parent!!.color = true
                    root = cur.parent!!.parent!!.rotate_left(nil!!, root!!)
                }
            }
        }
        root!!.color = false
    }

    public override fun add(key: K, value: V){
        if (root == null){
            root = RBNode(key, value, false)
            nil = RBNode(key, null, false)
            root!!.parent = nil
            root!!.left = nil
            root!!.right = nil
            return
        }
        if (check(key))
            return
        val new = RBNode(key, value, true)
        var dad = nil
        var cur = root
        while (cur != nil){
            dad = cur
            if (key < cur!!.key)
                cur = cur.left
            else
                cur = cur.right
        }
        new.parent = dad
        if (dad == nil)
            root = new
        else if (key < dad!!.key)
            dad.left = new
        else
            dad.right = new
        new.left = nil
        new.right = nil
        fix_add(new)
    }

    private fun transplate(old: RBNode<K, V>, new: RBNode<K, V>){   //замена поддерева другим
        if (old.parent == nil) { //случай, когда старое - корень
            new.parent = nil
            root = new
        }
        else{
            if (old == old.parent!!.left)   //если старое поддерево левый потомок
                old.parent!!.left = new
            else    //если старое поддерево правый потомок
                old.parent!!.right = new
            new.parent = old.parent //обновили родителя
        }
    }

    private fun fix_remove(replace: RBNode<K, V>){   //перебалансировка по Кормену
        var cur = replace
        var bro: RBNode<K, V>
        while (cur != root && !cur.color){
            if (cur == cur.parent!!.left){
                bro = cur.parent!!.right!!
                if (bro.color){
                    bro.color = false
                    cur.parent!!.color = true
                    root = cur.parent!!.rotate_left(nil!!, root!!)
                    bro = cur.parent!!.right!!
                }
                if (!bro.left!!.color && !bro.right!!.color){
                    bro.color = true
                    cur = cur.parent!!
                }
                else {
                    if (!bro.right!!.color) {
                        bro.left!!.color = false
                        bro.color = true
                        root = bro.rotate_right(nil!!, root!!)
                        bro = cur.parent!!.right!!
                    }
                    bro.color = cur.parent!!.color
                    cur.parent!!.color = false
                    bro.right!!.color = false
                    root = cur.parent!!.rotate_left(nil!!, root!!)
                    cur = root!!
                }
            }
            else {
                bro = cur.parent!!.left!!
                if (bro.color) {
                    bro.color = false
                    cur.parent!!.color = true
                    root = cur.parent!!.rotate_right(nil!!, root!!)
                    bro = cur.parent!!.left!!
                }
                if (!bro.left!!.color && !bro.right!!.color) {
                    bro.color = true
                    cur = cur.parent!!
                } else {
                    if (!bro.left!!.color) {
                        bro.right!!.color = false
                        bro.color = true
                        root = bro.rotate_left(nil!!, root!!)
                        bro = cur.parent!!.left!!
                    }
                    bro.color = cur.parent!!.color
                    cur.parent!!.color = false
                    bro.left!!.color = false
                    root = cur.parent!!.rotate_right(nil!!, root!!)
                    cur = root!!
                }
            }
        }
        cur.color = false
    }

    public override fun remove(key: K){
        if (root == null) return
        val cur = search(key) ?: return//находим удаляемый узел
        var cur_move = cur
        var original_color: Boolean = cur_move.color    //изначальный узел
        val replace: RBNode<K, V>
        if (cur.left == nil){  //если нет левого ребенка или обоих
            replace = cur.right!!
            transplate(cur, cur.right!!)
        }
        else if (cur.right == nil){    //если нет правого ребенка
            replace = cur.left!!
            transplate(cur, cur.left!!)
        }
        else{   //если есть оба ребенка
            cur_move = cur.right!!.getMinimum(nil!!)  //возьмем следущий узел и заменим им текущий, удалив его в исходном местоположение
            original_color = cur_move.color
            replace = cur_move.right!!
            if (cur_move.parent == cur)
                replace.parent = cur_move
            else{
                transplate(cur_move, cur_move.right!!)
                cur_move.right = cur.right
                cur_move.right!!.parent = cur_move
            }
            transplate(cur, cur_move)
            cur_move.left = cur.left
            cur_move.left!!.parent = cur_move
            cur_move.color = cur.color
        }
        if (!original_color)
            fix_remove(replace)
    }

}