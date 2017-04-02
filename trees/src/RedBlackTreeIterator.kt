open class RedBlackTreeIterator<K : Comparable<K>, V>(open val root: RBNode<K, V>?): Iterator<RBNode<K, V>>{

    var current = root?.getMinimum();
    var last = root?.getMaximum();

    override fun hasNext(): kotlin.Boolean {
        return current != last
    }

    private fun sz(cur: RBNode<K, V>?): Int{
        if (cur == null) return 0
        else return cur.size
    }

    private fun get_kth_minimum(cur: RBNode<K, V>?, k: Int): RBNode<K, V>{
        if (sz(cur) == k)
            return cur!!
        else if (sz(cur) > k)
            return get_kth_minimum(cur?.left, k)
        else
            return get_kth_minimum(cur?.right, k - sz(cur?.left) - 1)
    }

    private fun get_k(root: RBNode<K, V>?, cur: RBNode<K, V>?, k: Int): Int{
        if (root == null)
            return -1
        if (cur == null)
            return -1
        if (root.key == cur.key)
            return k
        else if (root.key > cur.key)
            return get_k(root.left, cur, k - 1)
        else
            return get_k(root.right, cur, k + 1)
    }

    override fun next(): RBNode<K, V> {
        if (hasNext())
            return get_kth_minimum(root, get_k(root, current, sz(root)) + 1)
        else
            return last as RBNode<K, V>
    }

}