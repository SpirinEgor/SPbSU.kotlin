package BTree

import Node

public class BNodeSingle<K: Comparable<K>, V>(override var key: K, override var value: V?): Node<K, V> {

    public override fun print() {
        print("$key/$value")
    }

}

