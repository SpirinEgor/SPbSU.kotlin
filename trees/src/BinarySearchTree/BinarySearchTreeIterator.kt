package BinarySearchTree

open class BinarySearchTreeIterator<K: Comparable<K>, V>(val tree: BinarySearchTree<K, V>): Iterator<BSNode<K, V>> {
    var used: Boolean = false
    var last: BSNode<K, V>? = if (tree.root != null) tree.root?.getMaximum() else null
    var cur: BSNode<K, V>? = if (tree.root != null) tree.root?.getMinimum() else null

    override fun hasNext(): Boolean{
        if (tree.root == null)
            return false
        return last != cur || !used
    }

    override fun next(): BSNode<K, V> {
        if (cur == last && !used) {
            used = true
            return cur!!
        }
        val ans = cur
        if (cur!!.right != null){
            cur = cur!!.right!!.getMinimum()
            return ans!!
        }
        while (cur!!.parent!!.right == cur) cur = cur!!.parent!!
        cur = cur!!.parent!!
        return ans!!
    }
}