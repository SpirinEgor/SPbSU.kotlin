interface Node<K : Comparable<K>, V> {
    var key: K
    var value: V?
    var size: Int
}