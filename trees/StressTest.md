# StressTest for trees

### Add (average value for 5 measurements)

   Type of tree   |  Random 1000 | Random 1000000 | Ascending 1000 | Ascending 1000000
------------------|--------------|----------------|----------------|------------------
BinarySearchTree  |    1.4 ms    |   1329.2 ms    |     13.8 ms    |      too long
RedBlackTree      |    3.4 ms    |   1426.6 ms    |     2.6 ms     |      856.6 ms
BTree (t = 1000) |    11.8 ms   |   1755.6 ms    |     0.6 ms     |      405 ms
