package BTree

open class BTreeIterator<K: Comparable<K>, V>(val tree: BTree<K, V>): Iterator<BNode<K, V>> {

    var queue: MutableList<BNode<K, V>> = mutableListOf()

    init {
        queue.add(tree.root!!)
    }

    override fun hasNext(): Boolean{
        if (tree.root == null)
            return false
        return queue.isNotEmpty()
    }

    override fun next(): BNode<K, V> {
        val cur = queue[0]
        queue.removeAt(0)
        if (!cur.leaf)
            for (c in cur.children)
                queue.add(c)
        return cur
    }
}