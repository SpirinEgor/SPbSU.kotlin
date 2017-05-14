package RedBlackTree

open class RedBlackTreeIterator<K : Comparable<K>, V>(val tree: RedBlackTree<K, V>): Iterator<RBNode<K, V>>{

    var used: Boolean = false
    var last: RBNode<K, V>? = if (tree.root != null) tree.root?.getMaximum(tree.nil!!) else null
    var cur: RBNode<K, V>? = if (tree.root != null) tree.root?.getMinimum(tree.nil!!) else null

    override fun hasNext(): Boolean{
        if (tree.root == null)
            return false
        return last != cur || !used
    }

    override fun next(): RBNode<K, V> {
        if (cur == last && !used) {
            used = true
            return cur!!
        }
        val ans = cur
        if (cur!!.right != tree.nil){
            cur = cur!!.right!!.getMinimum(tree.nil!!)
            return ans!!
        }
        while (cur!!.parent!!.right == cur) cur = cur!!.parent!!
        cur = cur!!.parent!!
        return ans!!
    }

}