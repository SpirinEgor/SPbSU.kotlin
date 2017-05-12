public class RedBlackTree<K : Comparable<K>, V>(var root: RBNode<K, V>?): Iterable<RBNode<K, V>>{

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

    public fun check(checking: K?): RBNode<K, V>?{  //функция проверки наличия элемента
        if (checking == null){
            return null
        }
        var dad = root
        while (true){
            if (dad == null){   //отсутствует
                return null
            }
            else{
                if (dad.key < checking){  //спускаемся вправо
                    dad = dad.right!!
                }
                else if (dad.key > checking){ //спускаемся влево
                    dad = dad.left!!
                }
                else{   //наличие
                    return dad
                }
            }
        }
    }

    private fun rotate_right(start: RBNode<K, V>): RBNode<K, V> {
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

    private fun rotate_left(start: RBNode<K, V>): RBNode<K, V> {
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
            nil = RBNode(key, null, false)
            root = RBNode(key, value, false)
            root!!.parent = nil
            root!!.left = nil
            root!!.right = nil
            return;
        }
        var dad = root  //отец нового элемента
        var grdad = root  //дед нового элемента
        var dir_grdad: kotlin.Boolean = true  //направление от деда
        var dir_dad: kotlin.Boolean  //направление от отца
        while (true){
            ++dad!!.size
            if (dad.key < key){  //если больше текущего, то идем вправо
                if (dad.right == null || dad.right == nil){ //если справа пусто, то ставим туда новую вершину
                    dad.right = RBNode(key, value, true)
                    dad.right!!.parent = dad
                    dad.right!!.left = nil
                    dad.right!!.right = nil
                    dir_dad = false
                    break
                }
                else{   //иначе переходим в правое поддерево
                    dad = dad.right!!
                }
            }
            else if (dad.key > key){    //если меньше, то идем влево
                if (dad.left == null || dad.left == nil){  //если слева пусто, то ставим туда новую вершину
                    dad.left = RBNode(key, value, true)
                    dad.left!!.parent = dad
                    dad.left!!.left = nil
                    dad.left!!.right = nil
                    dir_dad = true
                    break
                }
                else{   //иначе переходим влево
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
            val uncle_color: kotlin.Boolean    //определяем цвет дяди
            if (dir_grdad == true){
                if (grdad?.right == null || grdad.right == nil)
                    uncle_color = false
                else
                    uncle_color = grdad.right?.color!!
            }
            else{
                if (grdad?.left == null || grdad.left == nil)
                    uncle_color = false
                else
                    uncle_color = grdad.left?.color!!
            }
            if (uncle_color == true){   //если дядя красный
                if (grdad?.right != null && grdad.right != nil) grdad.right?.color = false
                if (grdad?.left != null && grdad.left != nil) grdad.left?.color = false
                if (grdad != root) grdad?.color = true
            }
            else{   //если дядя черный
                if (dir_grdad == true){  //случай левого потомка деда
                    if (dir_dad == false){ //если правый потомок отца
                        dad?.parent?.left = rotate_left(dad!!)
                        dir_dad = true
                        dad = dad.parent?.left!!
                    }
                    dad?.color = false
                    grdad?.color = true
                    if (grdad?.parent != null && grdad.parent != nil){
                        grdad.parent?.left = rotate_right(grdad)
                    }
                    else{
                        root = rotate_right(grdad!!)
                    }
                }
                else{   //случай правого потомка деда
                    if (dir_dad == true) {  //если левый потомок отца
                        dad?.parent?.right = rotate_right(dad!!)
                        dir_dad = false
                        dad = dad.parent?.right!!
                    }
                    dad?.color = false
                    grdad?.color = true
                    if (grdad?.parent != null && grdad.parent != nil){
                        grdad.parent?.right = rotate_left(grdad)
                    }
                    else{
                        root = rotate_left(grdad!!)
                    }
                }
            }
        }
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
                    x.parent = rotate_left(x.parent!!)
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
                        w = rotate_right(w)
                        w = x.parent!!.right!!
                    }
                    w.color = x.parent!!.color
                    x.parent!!.color = false
                    w.right!!.color = false
                    x?.parent = rotate_left(x?.parent!!)
                    x = root!!
                }
            }
            else {
                w = x.parent!!.left!!
                if (w.color) {
                    w.color = false
                    x.parent!!.color = true
                    x.parent = rotate_right(x.parent!!)
                    w = x.parent!!.left!!
                }
                if (!w.left!!.color && !w.right!!.color) {
                    w.color = true
                    x = x.parent!!
                } else {
                    if (!w.left!!.color) {
                        w.right!!.color = false
                        w.color = true
                        w = rotate_left(w)
                        w = x.parent!!.left!!
                    }
                    w.color = x.parent!!.color
                    x.parent!!.color = false
                    w.left!!.color = false
                    x?.parent = rotate_right(x?.parent!!)
                    x = root!!
                }
            }
        }
        x.color = false
    }

    private fun min_node_from_this(node: RBNode<K, V>): RBNode<K, V>{
        var result = node
        while (result.left != nil)
            result = result.left!!
        return result
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
            y = min_node_from_this(z.right!!)  //возьмем следущий узел и заменим им текущий, удалив его в исходном местоположение
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

    override fun iterator(): Iterator<RBNode<K, V>> = RedBlackTreeIterator(root)

}