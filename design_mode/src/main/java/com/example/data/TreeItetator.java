package com.example.data;

public class TreeItetator {

    static int preOrder[] = {1, 2, 4, 7, 3, 5, 6, 8};

    public static void main(String[] args) {
        TreeNode next = next();

        check(next);

    }

    private static TreeNode next() {
        TreeNode root = new TreeNode<>(1);
        root.setMLeft(new TreeNode<>(2));
        root.setMRight(new TreeNode<>(3));
        root.getMLeft().setMLeft(new TreeNode<>(4));
        root.getMLeft().setMRight(new TreeNode<>(5));
        root.getMLeft().getMLeft().setMLeft(new TreeNode<>(6));

        System.out.println(root);

        return root;
    }


    private static void check(TreeNode root) {
        if (root == null) {
            return;
        }

        System.out.println(root.getContent());

        check(root.getMLeft());
        check(root.getMRight());

    }
}
