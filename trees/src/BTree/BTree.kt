package BTree

import Tree

public class BTree<K: Comparable<K>, V>(var root: BNode<K, V>?, val t: Int): Iterable<BNode<K, V>>, Tree<K, V> {

    override fun iterator(): Iterator<BNode<K, V>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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
                    for (j in 1..10)
                        print(" ")
                    new_queue.add(null) //и добавляем null
                    new_queue.add(null)
                }
                else{
                    isPrint = true
                    print("(")
                    for (k in queue[i]!!.keys){
                        print("${k.key}/${k.value}")
                        if (k != queue[i]!!.keys.last())
                            print(",")
                    }
                    print(";${i})")
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
        var index = 0
        while (index < cur.keys.size && key > cur.keys[index].key)
            ++index
        if (index < cur.keys.size && key == cur.keys[index].key)
            return Pair(cur, index)
        else if (cur.leaf)
            return Pair(null, 0)
        else
            return search(cur.children[index], key)
    }

    public override fun check(key: K): Boolean = search(root, key).first != null

    private fun split_child(cur: BNode<K, V>, index: Int){
        val new = BNode<K, V>()
        val child = cur.children[index]
        new.leaf = child.leaf
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
        var index = 0
        while (index < cur.keys.size && key > cur.keys[index].key)
            ++index
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
            return
        }
        if (check(key))
            return
        val cur = root
        if (cur!!.keys.size == (2 * t - 1)){
            val new = BNode<K, V>()
            root = new
            new.leaf = false
            new.children.add(cur)
            split_child(new, 0)
        }
        add_nonfull(root!!, key, value)
    }

    public override fun remove(key: K) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}