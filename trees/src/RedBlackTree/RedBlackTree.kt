package RedBlackTree

import Tree

public class RedBlackTree<K : Comparable<K>, V>(var root: RBNode<K, V>?): Iterable<RBNode<K, V>>, Tree<K, V>{

    var nil: RBNode<K, V>? = null

    public fun draw(){ //функция рисования дерева
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

    public fun check(key: K): RBNode<K, V>?{  //функция проверки наличия элемента
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

    private fun fix_insert(add: RBNode<K, V>){
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

    public fun add(key: K, value: V){
        if (root == null){
            root = RBNode(key, value, false)
            nil = RBNode(key, null, false)
            root!!.parent = nil
            root!!.left = nil
            root!!.right = nil
            return
        }
        if (check(key) != null)
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
        fix_insert(new)
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

    private fun fix_remove(xx: RBNode<K, V>){   //перебалансировка по Кормену
        var x = xx
        var w: RBNode<K, V>
        while (x != root && !x.color){
            if (x == x.parent!!.left){
                w = x.parent!!.right!!
                if (w.color){
                    w.color = false
                    x.parent!!.color = true
                    root = x.parent!!.rotate_left(nil!!, root!!)
                    w = x.parent!!.right!!
                }
                if (!w.left!!.color && !w.right!!.color){
                    w.color = true
                    x = x.parent!!
                }
                else {
                    if (!w.right!!.color) {
                        w.left!!.color = false
                        w.color = true
                        root = w.rotate_right(nil!!, root!!)
                        w = x.parent!!.right!!
                    }
                    w.color = x.parent!!.color
                    x.parent!!.color = false
                    w.right!!.color = false
                    root = x?.parent!!.rotate_left(nil!!, root!!)
                    x = root!!
                }
            }
            else {
                w = x.parent!!.left!!
                if (w.color) {
                    w.color = false
                    x.parent!!.color = true
                    root = x.parent!!.rotate_right(nil!!, root!!)
                    w = x.parent!!.left!!
                }
                if (!w.left!!.color && !w.right!!.color) {
                    w.color = true
                    x = x.parent!!
                } else {
                    if (!w.left!!.color) {
                        w.right!!.color = false
                        w.color = true
                        root = w.rotate_left(nil!!, root!!)
                        w = x.parent!!.left!!
                    }
                    w.color = x.parent!!.color
                    x.parent!!.color = false
                    w.left!!.color = false
                    root = x?.parent!!.rotate_right(nil!!, root!!)
                    x = root!!
                }
            }
        }
        x.color = false
    }

    public fun remove(key: K){
        if (root == null) return
        val z = check(key) ?: return //находим удаляемый узел
        var y = z
        var original_color: Boolean = y.color    //изначальный узел
        val x: RBNode<K, V>
        if (z.left == nil){  //если нет левого ребенка или обоих
            x = z.right!!
            transplate(z, z.right!!)
        }
        else if (z.right == nil){    //если нет правого ребенка
            x = z.left!!
            transplate(z, z.left!!)
        }
        else{   //если есть оба ребенка
            y = z.right!!.getMinimum(nil!!)  //возьмем следущий узел и заменим им текущий, удалив его в исходном местоположение
            original_color = y.color
            x = y.right!!
            if (y.parent == z)
                x.parent = y
            else{
                transplate(y, y.right!!)
                y.right = z.right
                y.right!!.parent = y
            }
            transplate(z, y)
            y.left = z.left
            y.left!!.parent = y
            y.color = z.color
        }
        if (!original_color)
            fix_remove(x)
    }

    override fun iterator(): Iterator<RBNode<K, V>> = RedBlackTreeIterator(root, nil)

}