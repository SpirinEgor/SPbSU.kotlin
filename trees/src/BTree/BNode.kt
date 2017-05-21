package BTree

public class BNode<K: Comparable<K>, V>{

    var keys: MutableList<BNodeSingle<K, V>> = mutableListOf<BNodeSingle<K, V>>()
    var children: MutableList<BNode<K, V>> = mutableListOf<BNode<K, V>>()
    var parent: BNode<K, V>? = null
    var leaf: Boolean = true

    public fun print(){
        print("(")
        for (k in keys) {
            k.print()
            if (k != keys.last())
                print(",")
        }
        print(";${if (leaf) "leaf" else "not leaf"})")
    }

    public fun merge(index: Int){
        this.children[index].keys.add(this.keys[index])
        for (k in this.children[index + 1].keys)
            this.children[index].keys.add(k)
        for (c in this.children[index + 1].children)
            this.children[index].children.add(c)
        this.keys.removeAt(index)
        this.children.removeAt(index + 1)
    }

    public fun swap_left(index: Int){
        this.children[index].children.add(0, this.children[index - 1].children.last())
        this.children[index].keys.add(0, this.keys[index - 1])
        this.keys[index - 1] = this.children[index - 1].keys.last()
        this.children[index - 1].children.remove(this.children[index - 1].children.last())
        this.children[index - 1].keys.remove(this.children[index - 1].keys.last())
    }

    public fun swap_right(index: Int){
        this.children[index].children.add(this.children[index + 1].children[0])
        this.children[index].keys.add(this.keys[index])
        this.keys[index] = this.children[index + 1].keys[0]
        this.children[index + 1].children.removeAt(0)
        this.children[index + 1].keys.removeAt(0)
    }

    public fun getMinimum(): BNode<K, V>{
        if (leaf)
            return this
        else
            return this.children[0].getMinimum()
    }

    public fun getMaximum(): BNode<K, V>{
        if (leaf)
            return this
        else
            return this.children.last().getMaximum()
    }

    @Override
    public fun equals(other: BNode<K, V>): Boolean{
        if (this.leaf != other.leaf) return false
        if (this.keys.size != other.keys.size) return false
        for (i in 0..this.keys.size - 1)
            if (!this.keys[i].equals(other.keys[i])) return false
        return true
    }

}