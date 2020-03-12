package com.example.data

import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * 求二叉树的最大深度及直径
 */

class Node<T>(var content: T?) {
    var left: Node<T>? = null
    var right: Node<T>? = null
}

/**
 * 递归算法
 */
fun maxDepth(root: Node<Int>?): Int {
    return if (root == null) {
        0
    } else {
        val max = max(maxDepth(root.left), maxDepth(root.right))
        println("max------------${1 + max}--------${root.content}")
        (1 + max)
    }
}

/**
 * 递归 结束的条件是当前节点是空节点
 */

fun minDepth(root: Node<Int>?): Int {
    return if (root == null) {
        0
    } else {
        val min = min(minDepth(root.left), minDepth(root.right))
        println("min------------$min")
        (1 + min)
    }
}

/**
 * 二叉树的直径为每一个节点左右深度之和的最大值。只用使用一个全局变量max，在求出深度的同时，记录每一个节点左右深度之和的最大值。
 */
var max: Int = 0
fun maxLength(root: Node<Int>?): Int {
    return if (root == null) {
        0
    } else {
        val left = maxLength(root.left)
        val right = maxLength(root.right)
        max = max(max, left + right)
        println("max------------$max--------${root.content}")
        max(left, right) + 1
    }
}

/**
 * 迭代方式（堆栈）  todo  correct
 */
fun maxDepth2(root: Node<Int>?): Int {
    if (root == null) {
        return 0
    }

    var num = 0
    val q: Queue<Node<Int>> = LinkedList()

    q.offer(root)

    while (!q.isEmpty()) {
        ++num
        val n = q.size
        for (i in 0..n) {
            val poll = q.poll()
            poll?.let {
                if (poll.left != null) q.offer(poll.left)
                if (poll.right != null) q.offer(poll.right)
            }
        }
    }
    return num
}


/**
 *                   1           5.3节点返回 0+1=1    6. 1节点 max(3,1)=3   return 4
 *                 /   \
 *                2     3       4. 2节点的  max(2,1)  return 2+1=3
 *               / \
 *              4  5           2. 4节点的left为对象，right为null max(1,0) 返回 1+1=2   3. 5的left right 都为null  max(0,0) return 0+1=1
 *             /
 *            6              1. 6节点的left 和right 都为null  返回 1+0=1
 *
 */
fun main(args: Array<String>) {
    val root = Node<Int>(1)
    root.left = Node(2)
    root.right = Node(3)
    root.left?.left = Node(4)
    root.left?.right = Node(5)
    root.left?.left?.left = Node(6)
    println("start---------------")
    println("递归最大深度为：${maxDepth(root)}")
    println("堆栈方式最大深度为：${maxDepth2(root)}")
    println("start---------------")
    println("递归最小深度为：${minDepth(root)}")
    println("length---------------")
    maxLength(root)
    println("递归二叉树的直径${max}")
}
