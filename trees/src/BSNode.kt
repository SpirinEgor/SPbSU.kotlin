public class BSNode<K : Comparable<K>, V>(override var key: K, override var value: V?) : Node<K, V> {

    var left: BSNode<K, V>? = null
    var right: BSNode<K, V>? = null
    override var size: Int = 0

}