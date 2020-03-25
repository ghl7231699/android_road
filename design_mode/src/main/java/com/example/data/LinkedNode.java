package com.example.data;

import java.util.Stack;

/**
 * 类描述：单链表常见算法
 * 创建人：ghl
 * 创建时间：2019-10-17
 * <p>
 * 单链表相关的可查看
 * https://blog.csdn.net/qq_41999654/article/details/88962432
 */
public class LinkedNode {
    //头结点
    Node head = null;

    static int[] a = {1, 2, 5, 6, 8, 9};
    static int[] b = {0, 3, 4, 7, 10};

    public static void main(String[] args) {
        LinkedNode linkedNode = createLinkedNode();
        LinkedNode node1 = createLinkedNode();
        reversedLinkedList(linkedNode);
//        printReversedList(linkedNode.head);
//        reversedLinkedHead(linkedNode.head);
        System.out.println("\n递归反转：\n");
        log(reversedNodeR(linkedNode.head));

        midNode(node1.head);
        findKNode(node1.head, 2);

//        sortNode(linkedNode.head);
//        Node node = mergeNode(createNode(new Node(0), a), createNode(new Node(0), b));
//        log(node);
//        log(linkedNode.head);
    }

    private static LinkedNode createLinkedNode() {
        LinkedNode linkedNode = new LinkedNode();
        Node node = linkedNode.head = new Node(1);
        Node next = new Node(5);
        node.next = next;
        Node next1 = new Node(4);
        next.next = next1;
        Node next2 = new Node(3);
        next1.next = next2;
        next2.next = new Node(9);
        while (node != null) {
            System.out.print(node.data + "\t");
            node = node.next;
        }

//        deleteNode(next1);
//        insertNode(next1);
        System.out.println("\n");
        return linkedNode;
    }

    private static Node createNode(Node head, int[] datas) {
        Node cur = head;
        for (int data : datas) {
            cur.next = new Node(data);
            cur = cur.next;
        }

        log(head);
        return head;
    }

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
    public static boolean isCross1(Node head1, Node head2) {
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

    public static boolean isCross2(Node head1, Node head2) {
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
     * 一个链表是否带环
     */
    public static boolean isCross3(Node head) {
        Node fast = head, slow = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    /**
     * 链表环的长度
     */

    private static void lengthCross(Node node) {
        Node fast = node, slow = node;
        Node meet = null;
        Node cur;
        int len = 0;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {//相交点
                meet = fast;
                break;
            }
        }
        if (meet != null) {
            cur = meet.next;
            while (cur != meet) {
                len++;
                cur = cur.next;
            }
        }
    }

    /**
     * 链表的入口
     * <p>
     * 通过我们第一个利用开两个指针来判断链表是否带环的原理列出来的，我们得到的结果是 x = k * len - y,表示从开始到入口的结点个数等于 k 倍的环的长度减去入口到相遇结点的个数，那么我们用 cur 指向开始，cur 和 meet 同步向后遍历，当 cur == meet 时，两个指针所在位置即入口。
     */

    private static void findEntranceNode(Node node, Node meet) {
        Node cur = node;
        if (node == null) {
            return;
        }
        while (cur != meet) {
            cur = cur.next;
            meet = meet.next;
        }
    }


    void TestCircle() {
//        Node plist = null;
//        Node pos = null;
//        Node node = createNode(new Node(0), a);
//        findKNode(node, 1).next = findKNode(node, 3);
//        pos = isCross3(plist);
//        if (pos != null) {
//            System.out.println("带环，相遇点是：%d\n"+ pos.data);
//            System.out.println("环的长度：%d\n"+lengthCross(pos));
//            System.out.println("入口点是：%d\n", findEntranceNode(node, pos).data);
//        } else {
//            System.out.println("不带环\n");
//        }
    }


    /**
     * 求两个链表相交的第一个节点
     * 1、先判断两个链表是否相交
     * 2、求出两个链表的长度之差 len=length1-length2
     * 3、让长的链表先走len步
     * 4、然后两个链表同步向前移动，每次移动就比较节点是否相等，第一个相等的节点即为第一个相交点
     */

    public Node firstCrossPoint(LinkedNode list1, LinkedNode list2) {
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
        //list1 和 list2 同时移动，直到找个相交点
        while (temp1 != temp2) {
            temp1 = temp1.next;
            temp2 = temp2.next;
        }

        return temp1;
    }

    /**
     * 反向输出一个单链表
     * 1.遍历链表，将每个节点的内容存入到一个数组中，然后逆序输出数组
     * 2.使用栈来逆序输出
     * https://blog.csdn.net/alpgao/article/details/86509265
     */
    private static void reversedLinkedList(LinkedNode head) {
        if (head.head == null || head.head.next == null) {//空链表或只有一个数据，无需逆序
            return;
        }

        Stack<Node> stack = new Stack<>();
        Node node = head.head;
        if (node == null) {
            System.out.println("已经为null");
        } else {
            while (node != null) {
                stack.push(node);
                node = node.next;
            }
            System.out.print("反向输出结果：\n");
            while (!stack.isEmpty()) {
                System.out.print(stack.pop().data + "\t");
            }
        }
    }

    /**
     * 递归  逆序输出单链表
     */
    private static void printReversedList(Node node) {
        if (node == null) {
            return;
        }
        printReversedList(node.next);
        System.out.println("逆序输出：" + node.data);
    }

    private static void log(Node resultNode) {
        Node next = resultNode;
        while (next != null) {
            System.out.print(next.data + "\t");
            next = next.next;
        }
    }


    /**
     * 反转单链表
     * 头插法
     * <p>
     * 直接将链表逆序
     */
    private static void reversedLinkedHead(Node head) {
        if (head == null || head.next == null) {//空链表或只有一个数据，无需逆序
            return;
        }

        Node cur = head;
        Node newHead = null;
        Node temp = cur.next;
        while (cur != null) {
            //头插
            cur.next = newHead;
            newHead = cur;
            cur = temp;
            if (temp != null) {
                temp = temp.next;
            }
        }

        log(newHead);
    }

    /**
     * 递归反转单链表
     * <p>
     * 三个指针反转单链表
     */
    private static Node reversedNodeR(Node node) {
        if (node == null || node.next == null) {//空链表或只有一个数据，无需逆序
            return node;
        }
        Node tail = reversedNodeR(node.next);
        //下一个节点指向上一个结点
        node.next.next = node;
        node.next = null;
        return tail;
    }


    /**
     * 删除一个无头单链表的非尾节点（不能遍历单链表）
     * <p>
     * 1.因为单链表是无头的，所以不能从头开始，也不能遍历单链表。
     * 2.给定一个要删除的节点node
     * <p>
     * node.data=node.next.data
     * node.next=node.next.next
     */

    private static void deleteNode(Node node) {
        if (node == null || node.next == null) {
            return;
        }

        Node del = node.next;
        node.data = del.data;
        node.next = del.next;
        del = null;
    }

    /**
     * 在无头单链表的一个结点前插入一个结点（不能遍历链表）
     * <p>
     * <p>
     * 第一种方式：在节点后插入一个值，然后将该节点与此节点的值进行交换
     * <p>
     * 第二种方式：在节点后插入目标节点，目标节点赋值为该节点的值，然后被插入节点赋值为目标节点的值
     */

    private static void insertNode(Node node) {
        // 第一种方式
//        Node insert = new Node(10);
//        insert.next = node.next;
//        node.next = insert;
//        //交换值
//        int temp = insert.data;
//        insert.data = node.data;
//        node.data = temp;

        //第二种方式
        Node insert2 = new Node(node.data);
        insert2.next = node.next;
        node.next = insert2;
        node.data = 11;
    }

    /**
     * 单链表排序
     */

    private static void sortNode(Node node) {
        Node tail = null;
        if (node == null || node.next == null) {
            return;
        }

        while (node != tail) {//外层遍历每个节点
            Node cur = node;
            Node next = cur.next;
            while (next != tail) {//内层遍历将节点逐个进行比较
                if (cur.data > next.data) {
                    int tmp = cur.data;
                    cur.data = next.data;
                    next.data = tmp;
                }
                cur = next;
                next = cur.next;
            }
            tail = cur;
        }
        log(node);
    }

    /**
     * 合并两个有序链表, 合并后依然有序
     * <p>
     * 递归方式：将两个有序链表合并，就好像一个节点后链接两个有序链表合并后的新链表，以此类推
     */

    private static Node mergeNode(Node node1, Node node2) {
        Node first, second;
        if (node1 == node2) {
            return null;
        }
        if (node1 == null) {
            return node2;
        }

        if (node2 == null) {
            return node1;
        }

        if (node1.data > node2.data) {
            first = node1;
            first.next = mergeNode(node1.next, node2);
        } else {
            first = node2;
            first.next = mergeNode(node2.next, node1);
        }
        return first;
    }

    /**
     * 查找单链表中的中间节点，只能遍历一次
     * <p>
     * 定义两个节点，一个走的快fast，一个走的慢slow。
     * <p>
     * 当链表结点数为奇数时，fast->next = NULL时，slow所指结点为中间结点；
     * 当链表结点数为偶数时，fast = NULL时，slow所指结点为中间结点。
     */
    private static void midNode(Node node) {
        Node fast = node, slow = node;

        if (node == null || node.next == null) {
            return;
        }

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        System.out.println("中间节点为：" + slow.data);
    }

    /**
     * 查找单链表的倒数第k个节点，要求只能遍历一次链表
     * 跟查找中间节点类似：
     * 既然要查找倒数第k个结点，那么就让一个结点先走k 步，两个结点再同时一步一步走，当快的那个结点为空时，慢的结点所指位置就是倒数第k个结点所在。
     */

    private static void findKNode(Node node, int k) {
        Node fast = node, slow = node;

        if (node == null || node.next == null) {
            return;
        }

        for (int i = k; i >= 0; i--) {
            fast = fast.next;
        }
        while (fast != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        System.out.println("倒数第k个结点为：" + slow.data);
    }

}
