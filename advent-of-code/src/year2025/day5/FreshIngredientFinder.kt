package year2025.day5

import java.io.File
import java.io.InputStream

class FreshIngredientFinder {
    fun findFreshIngredients(filePath: String): Int {
        var root: TreeNode? = null
        var freshIngredients = 0

        val inputStream: InputStream = File(filePath).inputStream()
        val bufferedReader = inputStream.bufferedReader()

        var line = bufferedReader.readLine()
        while (line != null)
        {
            if (line.contains('-')) {
                val min = line.substringBefore("-").toLong()
                val max = line.substringAfter("-").toLong()

                root = TreeNode.addTreeNode(root, TreeNode(min, max, null, null))
            } else if (line.isNotEmpty()) {
                if (isFreshIngredient(root, line.toLong())) {
                    freshIngredients++
                }
            }

            line = bufferedReader.readLine()
        }

        return freshIngredients
    }

    private fun isFreshIngredient(node: TreeNode?, id: Long): Boolean {
        if (node == null) {
            return false
        }

        return if (id >= node.min && id <= node.max) {
            true
        } else if (id > node.max) {
            isFreshIngredient(node.right, id)
        } else {
            isFreshIngredient(node.left, id)
        }
    }

    fun findAmountOfFreshIngredientIds(filePath: String): Long {
        var root: TreeNode = getInitialTree(filePath) ?: return 0

        root = rebuildTreeUntilRangesUnique(root)

        return countTreeRanges(root)
    }

    private fun getInitialTree(filePath: String): TreeNode? {
        var root: TreeNode? = null

        val inputStream: InputStream = File(filePath).inputStream()
        val bufferedReader = inputStream.bufferedReader()

        var line = bufferedReader.readLine()
        while (line != null)
        {
            if (line.contains('-')) {
                val min = line.substringBefore("-").toLong()
                val max = line.substringAfter("-").toLong()

                root = TreeNode.addTreeNode(root, TreeNode(min, max, null, null))
            } else if (line.isNotEmpty()) {
                break
            }

            line = bufferedReader.readLine()
        }

        return root
    }

    private fun rebuildTreeUntilRangesUnique(root: TreeNode): TreeNode {
        var node = root
        var treeSize = TreeNode.getTreeSize(node)
        
        while (true) {
            var rebuiltNode = TreeNode(node.min, node.max, null, null)

            rebuiltNode = TreeNode.recomputeTree(rebuiltNode, node)
            node = rebuiltNode

            val rebuiltSize = TreeNode.getTreeSize(node)
            if (treeSize == rebuiltSize) {
                break
            }

            treeSize = rebuiltSize
        }

        return node
    }

    private fun countTreeRanges(node: TreeNode?): Long {
        if (node == null) {
            return 0
        }

        return node.max - node.min + 1 + countTreeRanges(node.left) + countTreeRanges(node.right)
    }
}