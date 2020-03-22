package com.example.data;

/**
 * 类描述：单链表常见算法
 * 创建人：ghl
 * 创建时间：2019-10-17
 */
public class LinkedList {
    //头结点
    Node head = null;

    /**
     * 求链表的长度
     *
     * @return
     */
    public int length() {
        int length = 0;
        Node curNode = head;
        while (curNode != null) {
            length++;
            curNode = curNode.next;
        }
        return length;
    }

    /**
     * 链表添加结点
     * <p>
     * 找打链表的末尾结点，将新添加的数据作为末尾结点的后续结点
     */

    public void addNode(int data) {
        Node newNode = new Node(data);
        if (head == null) {//如果为空链表
            head = newNode;
            return;
        }
        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = newNode;
    }

    /**
     * 链表删除结点(1.指定位置的)
     * 将要删除的结点的前结点指向要删除的结点的后结点，
     */
    public boolean deleteNode(int index) {
        if (index < 1 || index > length()) {//结点不存在
            return false;
        }
        if (index == 1) {//删除头结点
            head = head.next;
            return true;
        }
        Node preNode = head;
        Node curNode = preNode.next;
        int i = 1;
        while (curNode != null) {
            if (i == index) {//待删除结点
                preNode.next = curNode.next;
                return true;
            }
            //前结点和当前结点同时后移
            preNode = preNode.next;
            curNode = curNode.next;
            i++;
        }
        return true;
    }

    /**
     * 链表结点排序，并返回排序后的头结点
     * 选择排序算法，即每次都选出未排序结点中最小的结点，与第一个未排序结点交换
     */
    public Node linkSort() {
        Node curNode = head;
        while (curNode != null) {
            Node nextNode = curNode.next;
            while (nextNode != null) {
                if (curNode.data > nextNode.data) {
                    int temp = curNode.data;
                    curNode.data = nextNode.data;
                    nextNode.data = temp;
                }
                nextNode = nextNode.next;
            }
            curNode = curNode.next;
        }
        return head;
    }


    /**
     * 判断两个链表是否相交
     * 两个链表相交，则它们的尾结点一定是相同的,1、同时遍历两个链表到结尾，进行比较 2、使用栈  类似于1，只不过是根据栈顶元素来判断是否相同，原理相同 3、暴力，嵌套循环遍历
     */
    public boolean isCross1(Node head1, Node head2) {
        Node temp1 = head1;
        Node temp2 = head2;

        while (temp1.next != null) {
            temp1 = temp1.next;
        }
        while (temp2.next != null) {
            temp2 = temp2.next;
        }
        return temp1 == temp2;
    }

    public boolean isCross2(Node head1, Node head2) {
        Node temp1 = head1;
        Node temp2 = head2;

        while (temp1.next != null) {
            temp1 = temp1.next;
        }
        while (temp2.next != null) {
            temp2 = temp2.next;
        }
        return temp1 == temp2;
    }

    /**
     * 求两个链表相交的第一个节点
     * 1、先判断两个链表是否相交
     * 2、求出两个链表的长度之差 len=length1-length2
     * 3、让长的链表先走len步
     * 4、然后两个链表同步向前移动，每次移动就比较节点是否相等，第一个相等的节点即为第一个相交点
     */

    public Node firstCrossPoint(LinkedList list1, LinkedList list2) {
        if (!isCross1(list1.head, list2.head))
            return null;
        int len1 = list1.length();
        int len2 = list2.length();

        int len = len1 - len2;

        Node temp1 = list1.head;
        Node temp2 = list2.head;
        //长的列表先运行len
        if (len > 0) {//链表1的长度大于链表2的
            for (int i = 0; i < len; i++) {
                temp1 = temp1.next;
            }
        } else {//链表2的长度大于链表1的
            for (int i = 0; i < Math.abs(len); i++) {
                temp2 = temp2.next;
            }
        }
        //list1 和 list2 同时移动，直到找个相交点9
        while (temp1 != temp2) {
            temp1 = temp1.next;
            temp2 = temp2.next;
        }

        return temp1;
    }

    /**
     * 逆序一个单链表
     * 1.遍历链表，将每个节点的内容存入到一个数组中，然后逆序输出数组
     * 2.使用栈来逆序输出
     * 3.直接将链表逆序
     *
     * https://blog.csdn.net/alpgao/article/details/86509265
     */
    public void reversedLinkedList(LinkedList list1) {

    }
}
