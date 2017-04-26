class Tree <K: Comparable<K>, V> (var root: Node<K, V>? = null, val t: Int = 5): Iterable<Node<K, V>>{

    override fun iterator(): Iterator<Node<K, V>> = TreeIterator(root)  //итератор

    public fun draw(){  //рисование дерева
        if (root == null){  //если корень не существует
            println("Дерево еще не создано")
            return
        }
        var queue: MutableList<Node<K, V>?> = mutableListOf() //лист для вывода текущего уровня
        queue.add(root!!)
        var isPrint = true
        var indent = 64 //регулировка кривости 1.0
        while (isPrint){
            isPrint = false
            indent = indent / 2;   //регулировка кривости 2.0
            var new_queue: MutableList<Node<K, V>?> = mutableListOf() //следующий уровень
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
                    var print: String = "("
                    for (k in queue[i]!!.keys){
                        print += k.first
                        if (k != queue[i]!!.keys.last())
                            print += ","
                    }
                    print += ";${i})"
                    print(print)
                    for (ch in queue[i]!!.child)
                        new_queue.add(ch)
                }
            }
            println()
            queue = new_queue
        }
    }

    public fun search(key: K): V? = search(key, root) //поиск элемента

    private fun search(key: K, node: Node<K, V>?): V?{
        if (node == null)
            return null
        var i = 0;
        while (i < node.keys.size && key > node.keys[i].first)  //двигаемся по ключам
            ++i
        if (i < node.keys.size && node.keys[i].first == key)    //если нашли, то возвращаем значение
            return node.keys[i].second
        if (node.leaf)  //если лист, то такого нет
            return null
        return search(key, node.child[i])   //рекурсивно ищем от нужного ребенка
    }

    private fun splitChild(node: Node<K, V>, i: Int){   //разбиение узла
        var newNode = Node<K, V>()
        var splitNode = node.child[i]
        newNode.leaf = splitNode.leaf
        for (j in 0..t - 2){
            newNode.keys.add(splitNode.keys[t])
            splitNode.keys.removeAt(t)
        }
        if (!splitNode.leaf){
            for (j in 0..t - 1){
                newNode.child.add(splitNode.child[t])
                splitNode.child.removeAt(t)
            }
        }
        node.child.add(i + 1, newNode)
        node.keys.add(i, splitNode.keys[t - 1])
        splitNode.keys.removeAt(t - 1)
    }

    public fun insert(key: K, value: V){    //добавление элемента
        if (root == null){  //если нет корня еще, то ставим на место корня
            this.root = Node<K, V>()
            root!!.keys.add(Pair(key, value))
        }
        if (search(key) != null)    //если уже есть такой элемент
            return
        var old = root
        if (old!!.keys.size == 2 * t - 1){ //если корень заполнен до конца
            root = Node<K, V>()
            root!!.leaf = false
            root!!.child.add(old)
            splitChild(root!!, 0)
            insert(root!!, key, value)
        }
        else {
            insert(root!!, key, value)
        }
    }

    private fun insert(node: Node<K, V>, key: K, value: V){
        var i = 0
        if (node.leaf){
            while (i < node.keys.size && key > node.keys[i].first){
                ++i
            }
            node.keys.add(i, Pair(key, value))
        }
        else{
            while (i < node.keys.size && key > node.keys[i].first){
                ++i
            }
            if (node.child[i].keys.size == 2 * t - 1){
                splitChild(node, i)
                if (key > node.keys[i].first){
                    ++i
                }
            }
            insert(node.child[i], key, value)
        }
    }

}