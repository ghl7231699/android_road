package com.example.data.tree;

import com.example.data.tree.TreeNode;

/**
 * 二叉树相关算法
 */
public class TreeNodeMain {
    static int preOrder[] = {1, 2, 4, 7, 3, 5, 6, 8};
    static int inOrder[] = {4, 7, 2, 1, 5, 3, 8, 6};

    public static void main(String[] args) {
        TreeNode<Integer> treeNode = reBuildTree(preOrder, 0, preOrder.length - 1, inOrder, 0, inOrder.length - 1);
//        System.out.println(treeNode.toString());
    }


    /**
     * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不包含重复的数字。
     * 例如输入前序遍历序列{1， 2， 4， 7， 3， 5， 6， 8}和中序遍历序列{4， 7， 2， 1，5， 3， 8， 6}，则重建出二叉树并输出它的头结点
     * <p>
     * <p>
     * <p>
     * <p>
     * 1
     * 2    3
     * 4
     * 7
     *
     * @param preOrder 前序遍历结果
     *                 <p>
     *                 <p>
     *                 https://blog.csdn.net/u013132035/article/details/80519895
     */
    private static TreeNode<Integer> reBuildTree(int[] preOrder, int preStart, int preEnd,
                                                 int[] inOrder,
                                                 int inStart, int inEnd) {

        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }

        // 前序遍历获取到根节点
        int pre = preOrder[preStart];
        TreeNode<Integer> root = new TreeNode<>(pre);

        for (int i = inStart; i <= inEnd; i++) {
            if (pre == inOrder[i]) {//找到根节点
//                System.out.println("获取到的跟节点的数" + pre + "在中序遍历中的位置为：" + i);
                //为中序排序中左子树的个数
                int temp = i - inStart;
                //左节点
                TreeNode<Integer> left = reBuildTree(preOrder, preStart + 1, preStart + temp, inOrder
                        , inStart, inEnd);

                root.setLeft(left);

                //右子树
                TreeNode<Integer> mRight = reBuildTree(preOrder, temp + preStart + 1, preEnd, inOrder, i + 1,
                        inEnd);
                root.setRight(mRight);
                if (mRight != null) {
                    System.out.println(mRight.toString());
                }
            }

        }

        return root;

    }

}
