package RedBlackTree

import Node

public class RBNode<K : Comparable<K>, V>(override var key: K, override var value: V?, var color: Boolean): Node<K, V> {

    var left: RBNode<K, V>? = null
    var right: RBNode<K, V>? = null
    var parent: RBNode<K, V>? = null

    public override fun print() {
        print("($key/$value/${if (color) "RED" else "BLACK"})")
    }

    public fun getMinimum(nil: RBNode<K, V>): RBNode<K, V> {
        if (this.left == nil)
            return this
        else
            return this.left!!.getMinimum(nil)
    }

    public fun getMaximum(nil: RBNode<K, V>): RBNode<K, V> {
        if (this.right == nil)
            return this
        else
            return this.right!!.getMaximum(nil)
    }

    public fun rotate_left(nil: RBNode<K, V>, root_old: RBNode<K, V>): RBNode<K, V>{
        var root = root_old
        var rgson = this.right
        this.right = rgson!!.left
        if (rgson.left != nil)
            rgson.left!!.parent = this
        rgson.parent = this.parent
        if (this.parent == nil)
             root = rgson
        else if (this == this.parent!!.right)
            this.parent!!.right = rgson
        else
            this.parent!!.left = rgson
        rgson.left = this
        this.parent = rgson
        return root
    }

    public fun rotate_right(nil: RBNode<K, V>, root_old: RBNode<K, V>): RBNode<K, V>{
        var root = root_old
        var lson = this.left
        this.left = lson!!.right
        if (lson.right != nil)
            lson.right!!.parent = this
        lson.parent = this.parent
        if (this.parent == nil)
             root = lson
        else if (this == this.parent!!.right)
            this.parent!!.right = lson
        else
            this.parent!!.left = lson
        lson.right = this
        this.parent = lson
        return root
    }

    @Override
    public fun equals(other: RBNode<K, V>): Boolean{
        return this.key == other.key && this.value == other.value && this.color == this.color
    }

}