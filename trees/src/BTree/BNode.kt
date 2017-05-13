package BTree

import Node

public class BNodeSingle<K: Comparable<K>, V>(override var key: K, override var value: V?): Node<K, V> {}

public class BNode<K: Comparable<K>, V>{

    var keys: MutableList<BNodeSingle<K, V>> = mutableListOf<BNodeSingle<K, V>>()
    var children: MutableList<BNode<K, V>> = mutableListOf<BNode<K, V>>()
    var leaf: Boolean = true

}