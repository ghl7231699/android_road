package com.example.data.tree;

public class TreeIterator {

    static int preOrder[] = {1, 2, 4, 7, 3, 5, 6, 8};

    public static void main(String[] args) {
        TreeNode next = next();

        preCheck(next);

    }

    private static TreeNode next() {
        TreeNode root = new TreeNode<>(1);
        root.setLeft(new TreeNode<>(2));
        root.setRight(new TreeNode<>(3));
        root.getLeft().setLeft(new TreeNode<>(4));
        root.getLeft().setRight(new TreeNode<>(5));
        root.getLeft().getLeft().setLeft(new TreeNode<>(6));

        System.out.println(root);

        return root;
    }



    private static void preCheck(TreeNode root) {
        if (root == null) {
            return;
        }

        System.out.println(root.getContent());

        preCheck(root.getLeft());
        preCheck(root.getRight());

    }
}
