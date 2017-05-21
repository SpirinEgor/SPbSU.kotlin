package RedBlackTree

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class RedBlackTreeTest {

    private fun idealTree(): RedBlackTree<Int, Int>{
        val tree = RedBlackTree<Int, Int>(null)
        tree.root = RBNode<Int, Int>(13, 13, false)
        tree.nil = RBNode<Int, Int>(13, null, false)
        tree.root!!.parent = tree.nil

        tree.root!!.left = RBNode<Int, Int>(8, 8, true) //left child of root
        tree.root!!.left!!.parent = tree.root
        tree.root!!.left!!.left = RBNode<Int, Int>(1, 1, false)
        tree.root!!.left!!.left!!.parent = tree.root!!.left
        tree.root!!.left!!.left!!.left = tree.nil
        tree.root!!.left!!.left!!.right = RBNode<Int, Int>(6, 6, true)
        tree.root!!.left!!.left!!.right!!.parent = tree.root!!.left!!.left
        tree.root!!.left!!.left!!.right!!.left = tree.nil
        tree.root!!.left!!.left!!.right!!.right = tree.nil
        tree.root!!.left!!.right = RBNode<Int, Int>(11, 11, false)
        tree.root!!.left!!.right!!.parent = tree.root!!.left
        tree.root!!.left!!.right!!.left = tree.nil
        tree.root!!.left!!.right!!.right = tree.nil

        tree.root!!.right = RBNode<Int, Int>(17, 17, true)  //right child of root
        tree.root!!.right!!.parent = tree.root
        tree.root!!.right!!.left = RBNode<Int, Int>(15, 15, false)
        tree.root!!.right!!.left!!.parent = tree.root!!.right
        tree.root!!.right!!.left!!.left = tree.nil
        tree.root!!.right!!.left!!.right = tree.nil
        tree.root!!.right!!.right = RBNode<Int, Int>(25, 25, false)
        tree.root!!.right!!.right!!.parent = tree.root!!.right
        tree.root!!.right!!.right!!.left = RBNode<Int, Int>(22, 22, true)
        tree.root!!.right!!.right!!.left!!.parent = tree.root!!.right!!.right
        tree.root!!.right!!.right!!.left!!.left = tree.nil
        tree.root!!.right!!.right!!.left!!.right = tree.nil
        tree.root!!.right!!.right!!.right = RBNode<Int, Int>(27, 27, true)
        tree.root!!.right!!.right!!.right!!.parent = tree.root!!.right!!.right
        tree.root!!.right!!.right!!.right!!.left = tree.nil
        tree.root!!.right!!.right!!.right!!.right = tree.nil

        return tree
    }

    @Test
    operator fun iterator() {
        val tree = idealTree()
        val nodes: MutableList<RBNode<Int, Int>?> = mutableListOf()
        for (node in tree){
            nodes.add(node)
        }
        val realNodes: MutableList<RBNode<Int, Int>?> = mutableListOf(
                tree.root!!.left!!.left,
                tree.root!!.left!!.left!!.right,
                tree.root!!.left,
                tree.root!!.left!!.right,
                tree.root,
                tree.root!!.right!!.left,
                tree.root!!.right,
                tree.root!!.right!!.right!!.left,
                tree.root!!.right!!.right,
                tree.root!!.right!!.right!!.right
        )
        assertEquals(nodes, realNodes)
    }

    @Test
    fun searchInEmptyTree() {
        val tree = RedBlackTree<Int, Int>(null)
        assertEquals(tree.search(1557), null)
    }

    @Test
    fun searchInNonEmptyTreeExistentValue(){
        val random: Random = Random()
        val tree = RedBlackTree<Int, Int>(null)
        val inserted: MutableList<Int> = mutableListOf()
        for (i in 0..30){
            val cur = random.nextInt()
            inserted.add(cur)
            tree.add(cur, cur)
        }
        for (ins in inserted){
            val cur = tree.search(ins)
            assertEquals(cur!!.key, ins)
        }
    }

    @Test
    fun searchInNonEmptyTreeNonExistentValue(){
        val random: Random = Random()
        val tree = RedBlackTree<Int, Int>(null)
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
            assertEquals(tree.search(cur), null)
        }
    }

    @Test
    fun checkInEmptyTree() {
        val tree = RedBlackTree<Int, Int>(null)
        assertEquals(tree.check(1557), false)
    }

    @Test
    fun checkInNonEmptyTreeExistentValue(){
        val random: Random = Random()
        val tree = RedBlackTree<Int, Int>(null)
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
        val tree = RedBlackTree<Int, Int>(null)
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
    fun addNonExistentValue() {
        val tree = RedBlackTree<Int, Int>(null)
        tree.add(13, 13)
        tree.add(8, 8)
        tree.add(17, 17)
        tree.add(1, 1)
        tree.add(11, 11)
        tree.add(6, 6)
        tree.add(15, 15)
        tree.add(25, 25)
        tree.add(22, 22)
        tree.add(27, 27)
        val realTree = idealTree()
        assert(tree.equals(realTree))
    }

    @Test
    fun addExistentValue(){
        val tree = idealTree()
        tree.add(15, 15)
        val realTree = idealTree()
        assert(tree.equals(realTree))
    }

    @Test
    fun removeExistentValue() {
        val tree = RedBlackTree<Int, Int>(null)
        tree.add(13, 13)
        tree.add(8, 8)
        tree.add(17, 17)
        tree.add(1, 1)
        tree.add(20, 20)
        tree.add(11, 11)
        tree.add(6, 6)
        tree.add(15, 15)
        tree.add(25, 25)
        tree.add(22, 22)
        tree.add(27, 27)
        tree.remove(20)
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
        val tree = RedBlackTree<Int, Int>(null)
        val random: Random = Random()
        for (i in 1..1000){
            val cur = random.nextInt()
            tree.add(cur, cur)
        }
    }

    @Test
    fun stressAddRandom1000000(){
        val tree = RedBlackTree<Int, Int>(null)
        val random: Random = Random()
        for (i in 1..1000000){
            val cur = random.nextInt()
            tree.add(cur, cur)
        }
    }

    @Test
    fun stressAddAscending1000(){
        val tree = RedBlackTree<Int, Int>(null)
        for (i in 1..1000)
            tree.add(i, i)
    }

    @Test
    fun stressAddAscending1000000(){
        val tree = RedBlackTree<Int, Int>(null)
        for (i in 1..1000000)
            tree.add(i, i)
    }

}