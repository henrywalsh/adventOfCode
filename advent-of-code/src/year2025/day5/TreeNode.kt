package year2025.day5

import kotlin.math.max
import kotlin.math.min

data class TreeNode(
    var min: Long,
    var max: Long,
    var left: TreeNode?,
    var right: TreeNode?,
) {
    companion object {
        fun addTreeNode(node: TreeNode?, new: TreeNode): TreeNode {
            if (node == null) {
                return new
            }

            if (new.max > node.max) {
                if (new.min <= node.max) {
                    node.min = min(node.min, new.min)
                    node.max = max(node.max, new.max)

                    return node
                }

                node.right = addTreeNode(node.right, new)
            } else {
                if (new.max >= node.min) {
                    node.min = min(node.min, new.min)
                    node.max = max(node.max, new.max)

                    return node
                }

                node.left = addTreeNode(node.left, new)
            }

            return node
        }

        fun getTreeSize(node: TreeNode?): Int {
            if (node == null) {
                return 0
            }

            return 1 + getTreeSize(node.left) + getTreeSize(node.right)
        }

        fun recomputeTree(root: TreeNode, new: TreeNode?): TreeNode {
            var node = root

            if (new == null) {
                return node
            }

            node = addTreeNode(node, TreeNode(new.min, new.max, null, null))

            node = recomputeTree(node, new.left)
            node = recomputeTree(node, new.right)

            return node
        }
    }
}
