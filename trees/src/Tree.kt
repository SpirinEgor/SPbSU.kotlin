interface Tree<K : Comparable<K>, V>{

    public fun draw()

    public fun check(key: K): Boolean

    public fun add(key: K, value: V)

    public fun remove(key: K)

}