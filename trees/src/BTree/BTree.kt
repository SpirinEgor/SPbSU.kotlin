package BTree

import Tree

public class BTree<K: Comparable<K>, V>(var root: BNode<K, V>?, val t: Int): Iterable<BNode<K, V>>, Tree<K, V> {

    override fun iterator(): Iterator<BNode<K, V>> = BTreeIterator(this)

    public override fun draw(){  //рисование дерева
        if (root == null){  //если корень не существует
            println("Дерево еще не создано")
            return
        }
        var queue: MutableList<BNode<K, V>?> = mutableListOf() //лист для вывода текущего уровня
        queue.add(root)
        var isPrint = true
        var indent = 64 //регулировка кривости 1.0
        while (isPrint){
            isPrint = false
            indent = indent / 2;   //регулировка кривости 2.0
            val new_queue: MutableList<BNode<K, V>?> = mutableListOf() //следующий уровень
            for (i in 0..queue.size - 1){
                for (j in 1..indent)    //отступ
                    print(" ")
                if (queue[i] == null){  //если нет вершины, то пропуск
                    print(" null ")
                    new_queue.add(null) //и добавляем null
                    new_queue.add(null)
                }
                else{
                    isPrint = true
                    queue[i]!!.print()
                    for (ch in queue[i]!!.children)
                        new_queue.add(ch)
                }
            }
            println()
            queue = new_queue
        }
    }

    public fun search(cur: BNode<K, V>?, key: K): Pair<BNode<K, V>?, Int>{
        if (cur == null)
            return Pair(null, 0)
        var index = cur.keys.binarySearch { bNodeSingle -> bNodeSingle.key - key }
        if (index >= 0 && key == cur.keys[index].key)
            return Pair(cur, index)
        else if (cur.leaf)
            return Pair(null, 0)
        else {
            index = -index -1
            return search(cur.children[index], key)
        }
    }

    public override fun check(key: K): Boolean = search(root, key).first != null

    private fun split_child(cur: BNode<K, V>, index: Int){
        val new = BNode<K, V>()
        val child = cur.children[index]
        new.leaf = child.leaf
        new.parent = child.parent
        for (i in 0..t - 2){
            new.keys.add(child.keys[t])
            child.keys.removeAt(t)
        }
        if (!child.leaf)
            for (i in 0..t - 1){
                new.children.add(child.children[t])
                child.children.removeAt(t)
            }
        cur.children.add(index + 1, new)
        cur.keys.add(index, child.keys[t - 1])
        child.keys.removeAt(t - 1)
    }

    private fun add_nonfull(cur: BNode<K, V>, key: K, value: V){
        var index = cur.keys.binarySearch { bNodeSingle -> bNodeSingle.key - key }
        if (index < 0)
            index = -index - 1
        if (cur.leaf)
            cur.keys.add(index, BNodeSingle(key, value))
        else{
            if (cur.children[index].keys.size == (2 * t - 1)){
                split_child(cur, index)
                if (key > cur.keys[index].key)
                    ++index
            }
            add_nonfull(cur.children[index], key, value)
        }
    }

    public override fun add(key: K, value: V) {
        if (root == null){
            root = BNode()
            root!!.keys.add(BNodeSingle(key, value))
            root!!.parent = null
            return
        }
        if (check(key)) {
            println("This key already exists")
            return
        }
        val cur = root
        if (cur!!.keys.size == (2 * t - 1)){
            val new = BNode<K, V>()
            root = new
            new.leaf = false
            new.children.add(cur)
            cur.parent = new
            split_child(new, 0)
        }
        add_nonfull(root!!, key, value)
    }

    private fun remove(remove: BNode<K, V>, key: K) {
        var cur = remove
        if (cur.leaf) {    //если ключ лежит в листе (Кормен 1)
            if (cur == root) {   //если удаление в корне
                if (root!!.keys.size == 1) {
                    root = null
                    return
                }
            }
            val index = cur.keys.binarySearch { bNodeSingle -> bNodeSingle.key - key }
            cur.keys.removeAt(index)
            return
        }
        var index = cur.keys.binarySearch { a -> a.key - key }
        if (index >= 0 && cur.keys[index].key == key) { //если ключ лежит в cur и cur внутренний узел (Кормен 2)
            if (cur.children[index].keys.size > t - 1) {  //если предшествующему дочернему ключу узел содержит >= t элементов (Кормен 2.а)
                cur.keys[index] = cur.children[index].keys.last()   //заменим предшественником
                remove(cur.children[index], cur.keys[index].key)    //удалим рекурсивно
            }
            else if (cur.children[index + 1].keys.size > t - 1){    //если в следующем дочернем узле >= t элементов (Кормен 2.б)
                cur.keys[index] = cur.children[index + 1].keys[0]   //замени следующим
                remove(cur.children[index + 1], cur.keys[index].key)    //удалим рекурсивно
            }
            else{   //если оба соседних ребенка имеют по t - 1 элементу (Кормен 2.в)
                cur.merge(index)    //объединим соседних ребенков
                if (cur == root)
                    root = cur.children[0]
                remove(cur.children[index], key)    //удалим рекурсивно
            }
        }
        else{   //если ключ не лежит в cur (Кормен 3)
            val parent = cur
            index = -index - 1
            cur = cur.children[index]
            if (cur.keys.size <= t - 1){    //если в вершине с ключом <= t - 1 ключ
                if (index > 0 && parent.children[index - 1].keys.size > t - 1)  //если у левого соседа >= t, то переносим (Кормен 3.а.а)
                    parent.swap_left(index)
                else if (index < parent.children.size - 1 && parent.children[index + 1].keys.size > t - 1)  //если у правого соседа >= t, то переносим (Кормен 3.б.б)
                    parent.swap_right(index)
                else {  //иначе объединяем с одним из соседей (Кормен 3.б)
                    if (index > 0) {
                        parent.merge(index - 1)
                        cur = parent.children[index - 1]
                    }
                    else {
                        parent.merge(index)
                        if (parent == root)
                            root = parent.children[0]
                    }
                }
            }
            remove(cur, key)    //удалим рекурсивно
        }
    }

    public override fun remove(key: K) {
        if (root == null)
            return
        if (!check(key))
            return
        remove(root!!, key)
    }

    @Override
    public fun equals(other: BTree<K, V>): Boolean{
        val cur_nodes: MutableList<BNode<K, V>> = this.toMutableList()
        val other_nodes: MutableList<BNode<K, V>> = other.toMutableList()
        if (this.t != other.t)
            return false
        if (cur_nodes.size != other_nodes.size)
            return false
        for (i in 0..cur_nodes.size - 1)
            if (!cur_nodes[i].equals(other_nodes[i]))
                return false
        return true
    }

}

private operator fun  <K> K.minus(key: K): Int {
    val t = this as Int
    val k = key as Int
    val res: Long = t.toLong() - k.toLong()
    if (res < 0)
        return -1
    else if (res > 0)
        return 1
    else
        return 0
}
