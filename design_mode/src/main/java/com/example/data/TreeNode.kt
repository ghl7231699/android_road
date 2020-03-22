package com.example.data

/**
 * 二叉树结构
 */

class TreeNode<T>(var content: T?) {
    public var mLeft: TreeNode<T>? = null
    public var mRight: TreeNode<T>? = null

    override fun toString(): String {
        return "当前节点的值为：$content\n左节点的值为：${mLeft?.content}\t右节点的值为：${mRight?.content}"
    }
}