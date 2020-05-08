package com.example.data.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数组相关算法
 */
public class ArrayAlgorithmJava {
    public static void main(String[] args) {
        int[] sum = TwoSum();
        ThreeSum();

        System.out.println("result is " + sum[0] + sum[1]);

        ListNode L1 = new ListNode(2);
        ListNode l1 = new ListNode(4);
        ListNode l2 = new ListNode(7);
        L1.next = l1;
        l1.next = l2;

        ListNode L2 = new ListNode(7);
        ListNode l3 = new ListNode(8);
        ListNode l4 = new ListNode(4);
        L2.next = l3;
        l3.next = l4;

        ListNode listNode = TwoAdd(L1, L2);
        System.out.println("addTwo result is " + listNode.val);
    }

    //给定数组，是否包含有重复数据
    private static void repetitionData() {
        int[] nums = new int[]{2, 3, 4, 8, 10, 2};
        int length = nums.length;
        // 暴力拆解   时间复杂度 O(n2) 空间复杂度O(1)
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (nums[i] == nums[j]) {
                    System.out.println("存在重复数据" + nums[i]);
                    break;
                }
            }
        }

//        // 扫描数组，判断下标为i的值是否等于i，若不等于i，则判断下标为numbers[i]的值是否等于numbers[nums[i]]，
//        // 若等于则重复，不等于则交换，时间复杂度O(n)，空间复杂度O(1) 数组内容为0-n-1范围内的数字
//
//        int[] duplication = new int[]{};
//        for (int i = 0; i < nums.length; i++) {
//            //判断i是否等于numbers[i]
//            while (i != nums[i]) {//不等于，则判断m=nums[i]是否等于numbers[m]
//                int m = nums[i];
//                if (m == nums[m]) {//如果m下标的值等于i下标的值，就是重复的
//                    duplication[0] = m;
//                    break;
//                } else {//如果m下标的值不等于i下标的值，就进行调换，将i下标的值调到m的位置，使得numbers[m]==m
//                    nums[i] = nums[m];
//                    nums[m] = m;
//                }
//            }
//        }

    }

    /**
     * 两数之和
     */
    private static int[] TwoSum() {
        int target = 10;
        int[] nums = new int[]{1, 5, 3, 9, 0, 2, 5};
        int length = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < length; i++) {
            int others = target - nums[i];
            if (map.containsKey(others)) {
                return new int[]{map.get(others), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }

    /**
     * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
     * <p>
     * 注意：答案中不可以包含重复的三元组。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/3sum
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    private static List<List<Integer>> ThreeSum() {
        int[] nums = new int[]{1, 5, 3, 9, 0, 2, 5};

        Arrays.sort(nums);
        List<List<Integer>> ls = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {  // 跳过可能重复的答案

                int l = i + 1, r = nums.length - 1, sum = 10 - nums[i];
                while (l < r) {
                    if (nums[l] + nums[r] == sum) {
                        ls.add(Arrays.asList(nums[i], nums[l], nums[r]));
                        while (l < r && nums[l] == nums[l + 1]) l++;
                        while (l < r && nums[r] == nums[r - 1]) r--;
                        l++;
                        r--;
                    } else if (nums[l] + nums[r] < sum) {
                        while (l < r && nums[l] == nums[l + 1]) l++;   // 跳过重复值
                        l++;
                    } else {
                        while (l < r && nums[r] == nums[r - 1]) r--;
                        r--;
                    }
                }
            }
        }
        for (List<Integer> l :
                ls) {
            System.out.println("符合三数之和的为" + l);
        }
        return ls;
    }


    /**
     * 两数相加
     * <p> 链表中的值<10
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     * <p>
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     * <p>
     * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     * <p>
     * 247
     * 784
     */
    private static ListNode TwoAdd(ListNode L1, ListNode L2) {
//        ListNode dummyHead = new ListNode(0);
//        ListNode p = L1, q = L2, curr = dummyHead;
//        int temp = 0;// 进位
//        while (p != null || q != null) {
//            int x = p != null ? p.val : 0;
//            int y = q != null ? q.val : 0;
//            System.out.println("[" + x + "," + y + "]");
//
//            int sum = temp + x + y;
//            temp = sum / 10;//不是1就是0
//            //
//            curr.next = new ListNode(sum % 10);
//            curr = curr.next;
//
//            if (p != null) p = p.next;
//            if (q != null) q = q.next;
//        }
//
//        if (temp > 0) {
//            curr.next = new ListNode(temp);
//        }
//        return dummyHead.next;
        ListNode dummyHead = new ListNode(0);
        ListNode p = L1, q = L2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }

    /**
     * 读取一段英文字符串里的，输出重复字符的次数(C++写 perhaps)
     */
    private static void readEnglish() {
        String[] chars = new String[]{"jfoauhoangaogyoqputnlnoignalsgnerh"};

    }
}

