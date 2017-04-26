class TreeIterator <K: Comparable<K>, V>(var node: Node<K, V>?): Iterator<Node<K, V>> {

    private var queue: MutableList<Node<K, V>> = mutableListOf<Node<K, V>>()

    init{
        if (node != null)
            queue.add(node!!)
    }

    override fun hasNext(): Boolean = queue.isNotEmpty()

    override fun next(): Node<K, V> {
        var current = queue.removeAt(0)
        if (current.child.isNotEmpty())
            queue.addAll(current.child)
        return current
    }

}