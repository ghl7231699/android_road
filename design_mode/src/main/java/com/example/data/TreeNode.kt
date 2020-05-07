package com.example.data

/**
 * 二叉树结构
 */

class TreeNode<T>(var content: T?) {
    var left: TreeNode<T>? = null
    var right: TreeNode<T>? = null

//    override fun toString(): String {
//        return "当前节点的值为：$content\n左节点的值为：${left?.content}\t右节点的值为：${right?.content}"
//    }
}