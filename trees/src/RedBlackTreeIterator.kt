open class RedBlackTreeIterator<K : Comparable<K>, V>(var node: RBNode<K, V>?): Iterator<RBNode<K, V>>{

    private var next: RBNode<K, V>? = null
    private var cur: RBNode<K, V>? = null

    private fun get_min(cur: RBNode<K, V>): RBNode<K, V> = if (cur.left != null) get_min(cur.left!!) else cur

    override fun hasNext(): Boolean {
        if (node == null)
            return false
        if (next == null){
            next = get_min(node!!)
            return true
        }
        cur = next
        if (cur!!.right != null){
            next = get_min(cur!!.right!!)
            return true
        }
        if (cur!!.parent != null && cur!!.parent!!.left == cur){
            next = cur!!.parent
            return true
        }
        else{
            return false
        }
    }

    override fun next(): RBNode<K, V> = next!!

}