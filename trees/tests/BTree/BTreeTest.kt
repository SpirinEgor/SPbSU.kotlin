package BTree

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class BTreeTest{

    private fun idealTree(): BTree<Int, Int> {
        val tree = BTree<Int, Int>(null, 3)
        tree.root = BNode<Int, Int>()
        tree.root!!.leaf = false
        tree.root!!.keys.add(BNodeSingle<Int, Int>(10, 10))
        tree.root!!.keys.add(BNodeSingle<Int, Int>(20, 20))
        tree.root!!.keys.add(BNodeSingle<Int, Int>(30, 30))
        var node = BNode<Int, Int>()
        node.leaf = true
        node.keys = mutableListOf(
                BNodeSingle<Int, Int>(2, 2),
                BNodeSingle<Int, Int>(4, 4),
                BNodeSingle<Int, Int>(6, 6)
        )
        tree.root!!.children.add(node)
        node = BNode<Int, Int>()
        node.leaf = true
        node.keys = mutableListOf(
                BNodeSingle<Int, Int>(12, 12),
                BNodeSingle<Int, Int>(15, 15),
                BNodeSingle<Int, Int>(17, 17),
                BNodeSingle<Int, Int>(19, 19)
        )
        tree.root!!.children.add(node)
        node = BNode<Int, Int>()
        node.leaf = true
        node.keys = mutableListOf(
                BNodeSingle<Int, Int>(21, 21),
                BNodeSingle<Int, Int>(27, 27)
        )
        tree.root!!.children.add(node)
        node = BNode<Int, Int>()
        node.leaf = true
        node.keys = mutableListOf(
                BNodeSingle<Int, Int>(32, 32),
                BNodeSingle<Int, Int>(35, 35),
                BNodeSingle<Int, Int>(36, 36),
                BNodeSingle<Int, Int>(41, 41),
                BNodeSingle<Int, Int>(53, 53)
        )
        tree.root!!.children.add(node)
        return tree
    }

    @Test
    operator fun iterator() {
        val tree = idealTree()
        val nodes: MutableList<BNode<Int, Int>> = tree.toMutableList()
        val two = BNode<Int, Int>()
        val three = BNode<Int, Int>()
        val four = BNode<Int, Int>()
        val five = BNode<Int, Int>()
        val one = BNode<Int, Int>()
        two.leaf = true
        two.keys = mutableListOf(
                BNodeSingle<Int, Int>(2, 2),
                BNodeSingle<Int, Int>(4, 4),
                BNodeSingle<Int, Int>(6, 6)
        )
        three.leaf = true
        three.keys = mutableListOf(
                BNodeSingle<Int, Int>(12, 12),
                BNodeSingle<Int, Int>(15, 15),
                BNodeSingle<Int, Int>(17, 17),
                BNodeSingle<Int, Int>(19, 19)
        )
        four.leaf = true
        four.keys = mutableListOf(
                BNodeSingle<Int, Int>(21, 21),
                BNodeSingle<Int, Int>(27, 27)
        )
        five.leaf = true
        five.keys = mutableListOf(
                BNodeSingle<Int, Int>(32, 32),
                BNodeSingle<Int, Int>(35, 35),
                BNodeSingle<Int, Int>(36, 36),
                BNodeSingle<Int, Int>(41, 41),
                BNodeSingle<Int, Int>(53, 53)
        )
        one.leaf = false
        one.keys = mutableListOf(
                BNodeSingle<Int, Int>(10, 10),
                BNodeSingle<Int, Int>(20, 20),
                BNodeSingle<Int, Int>(30, 30)
        )
        one.children = mutableListOf(two, three, four, five)
        val realNodes: MutableList<BNode<Int, Int>> = mutableListOf(one, two, three, four, five)
        assertEquals(nodes.size, realNodes.size)
        for (i in 0..nodes.size - 1)
            assert(nodes[i]!!.equals(realNodes[i]))
    }

    @Test
    fun searchInEmptyTree() {
        val tree = BTree<Int, Int>(null, 3)
        assertEquals(tree.search(tree.root, 1557), Pair(null, 0))
    }

    @Test
    fun searchInNonEmptyTreeExistentValue(){
        val random: Random = Random()
        val tree = BTree<Int, Int>(null, 3)
        val inserted: MutableList<Int> = mutableListOf()
        for (i in 0..30){
            val cur = random.nextInt()
            inserted.add(cur)
            tree.add(cur, cur)
        }
        //print(inserted)
        for (ins in inserted){
            val cur: Pair<BNode<Int, Int>?, Int> = tree.search(tree.root, ins)
            assertEquals(cur.first!!.keys[cur.second].key, ins)
        }
    }

    @Test
    fun searchInNonEmptyTreeNonExistentValue(){
        val random: Random = Random()
        val tree = BTree<Int, Int>(null, 3)
        val inserted: MutableSet<Int> = mutableSetOf()
        for (i in 0..30){
            val cur = random.nextInt()
            inserted.add(cur)
            tree.add(cur, cur)
        }
        for (i in 0..30){
            var cur = random.nextInt()
            while (inserted.contains(cur))
                cur = random.nextInt()
            assertEquals(tree.search(tree.root, cur), Pair(null, 0))
        }
    }

    @Test
    fun checkInEmptyTree() {
        val tree = BTree<Int, Int>(null, 3)
        assertEquals(tree.check(1557), false)
    }

    @Test
    fun checkInNonEmptyTreeExistentValue(){
        val random: Random = Random()
        val tree = BTree<Int, Int>(null, 3)
        val inserted: MutableList<Int> = mutableListOf()
        for (i in 0..30){
            val cur = random.nextInt()
            inserted.add(cur)
            tree.add(cur, cur)
        }
        for (ins in inserted){
            assertEquals(tree.check(ins), true)
        }
    }

    @Test
    fun checkInNonEmptyTreeNonExistentValue(){
        val random: Random = Random()
        val tree = BTree<Int, Int>(null, 3)
        val inserted: MutableSet<Int> = mutableSetOf()
        for (i in 0..30){
            val cur = random.nextInt()
            inserted.add(cur)
            tree.add(cur, cur)
        }
        for (i in 0..30){
            var cur = random.nextInt()
            while (inserted.contains(cur))
                cur = random.nextInt()
            assertEquals(tree.check(cur), false)
        }
    }

    @Test
    fun addExistentValue(){
        val tree = idealTree()
        tree.add(17, 17)
        val realTree = idealTree()
        assert(tree.equals(realTree))
    }

    @Test
    fun removeNonExistentValue(){
        val tree = idealTree()
        tree.remove(1557)
        val realTree = idealTree()
        assert(tree.equals(realTree))
    }

    @Test
    fun stressAddRandom1000(){
        val tree = BTree<Int, Int>(null, 1000)
        val random: Random = Random()
        for (i in 1..1000){
            val cur = random.nextInt()
            tree.add(cur, cur)
        }
    }

    @Test
    fun stressAddRandom1000000(){
        val tree = BTree<Int, Int>(null, 1000)
        val random: Random = Random()
        for (i in 1..1000000){
            val cur = random.nextInt()
            tree.add(cur, cur)
        }
    }

    @Test
    fun stressAddAscending1000(){
        val tree = BTree<Int, Int>(null, 1000)
        for (i in 1..1000)
            tree.add(i, i)
    }

    @Test
    fun stressAddAscending1000000(){
        val tree = BTree<Int, Int>(null, 1000)
        for (i in 1..1000000)
            tree.add(i, i)
    }

}