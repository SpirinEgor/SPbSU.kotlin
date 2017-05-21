package BinarySearchTree

import Node

public class BSNode<K : Comparable<K>, V>(override var key: K, override var value: V?) : Node<K, V> {

    var left: BSNode<K, V>? = null
    var right: BSNode<K, V>? = null
    var parent: BSNode<K, V>? = null

    public override fun print() {
        print("(${this.key}/${this.value})")
    }

    public fun getMinimum(): BSNode<K, V>{
        if (this.left == null)
            return this
        else
            return this.left!!.getMinimum()
    }

    public fun getMaximum(): BSNode<K, V>{
        if (this.right == null)
            return this
        else
            return this.right!!.getMaximum()
    }

    @Override
    public fun equals(other: BSNode<K, V>): Boolean = this.key == other.key && this.value == other.value

}