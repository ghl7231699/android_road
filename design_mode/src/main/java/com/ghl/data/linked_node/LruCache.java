package com.ghl.data.linked_node;

import android.annotation.SuppressLint;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 类描述：Lru算法双向链表的实现
 * 创建人：ghl
 * 创建时间：2019-10-18
 */
public class LruCache {

    public static void main(String[] args) {

//        LinkedType();


        LRUCache obj = new LRUCache(3);
        obj.put(1, 1);
        obj.put(2, 2);
        obj.put(3, 3);


        int param_1 = obj.get(1);

//        obj.put(1, 4);
        obj.put(1, param_1);

        obj.put(4, 4);

        Set<Map.Entry<Integer, Integer>> entries = obj.entrySet();
        for (Map.Entry<Integer, Integer> entry : entries) {
            System.out.println(entry.getValue());
        }


        System.out.println("----------------------第二种方式-------------");

        lruCache2();
    }

    private static void LinkedType() {
//        Lru lru = new Lru(3);
//        lru.insert("1", "first");
//        lru.insert("2", "second");
//        lru.insert("3", "third");
//
//        lru.printFor();
//
//        System.out.println("----------------------插入前-------------");
//
//        lru.insert("4", "four");
//
//        lru.printFor();
//
//        lru.getNode("2");
//
//        lru.printFor();
    }

    private static void lruCache2() {

        LruCache obj = new LruCache(3);
//        obj.put(1, 1);
//        obj.put(2, 2);
//        obj.put(3, 3);

        int value = obj.get(3);
//        obj.put(3, value);

        obj.put(0, 43);

        DLinkedNode next = obj.head.next;
        while (next != null) {
            System.out.println(next.value);
            next = next.next;
        }
    }


    static class LRUCache extends LinkedHashMap<Integer, Integer> {
        private int capacity;

        public LRUCache(int capacity) {
            super(capacity, 0.75F, true);
            this.capacity = capacity;
        }

        @SuppressLint("NewApi")
        public int get(int key) {
            return super.getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            super.put(key, value);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > capacity;
        }
    }


    /**
     * 定义双向链表
     */
    static class LinkedNode {
        String key;
        Object val;
        LinkedNode preNode;
        LinkedNode postNode;
    }

    static class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;
    }

    private void addNode(DLinkedNode node) {
        /**
         * Always add the new node right after head.
         */
        node.prev = head;
        node.next = head.next;

        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DLinkedNode node) {
        /**
         * Remove an existing node from the linked list.
         */
        DLinkedNode prev = node.prev;
        DLinkedNode next = node.next;

        prev.next = next;
        next.prev = prev;
    }

    private void moveToHead(DLinkedNode node) {
        /**
         * Move certain node in between to the head.
         */
        removeNode(node);
        addNode(node);
    }

    private DLinkedNode popTail() {
        /**
         * Pop the current tail.
         */
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }

    private Hashtable<Integer, DLinkedNode> cache =
            new Hashtable<Integer, DLinkedNode>();
    private int size;
    private int capacity;
    private DLinkedNode head, tail;

    public LruCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;

        head = new DLinkedNode();
        // head.prev = null;

        tail = new DLinkedNode();
        // tail.next = null;

        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) return -1;

        // move the accessed node to the head;
        moveToHead(node);

        return node.value;
    }

    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);

        if (node == null) {
            DLinkedNode newNode = new DLinkedNode();
            newNode.key = key;
            newNode.value = value;

            cache.put(key, newNode);
            addNode(newNode);

            ++size;

            if (size > capacity) {
                // pop the tail
                DLinkedNode tail = popTail();
                cache.remove(tail.key);
                --size;
            }
        } else {
            // update the value.
            node.value = value;
            moveToHead(node);
        }
    }

//    /**
//     * Lru算法
//     */
//    static class Lru {
//        int size;
//        //存储容量，默认存3个
//        int capacity = 3;
//        //用作标记
//        LinkedNode head = new LinkedNode();
//
//        LinkedNode tail;
//
//        public Lru(int capacity) {
//            this.capacity = capacity;
//        }
//
//        public Lru() {
//        }
//
//        /**
//         * 查找key所在的节点
//         *
//         * @param key key
//         * @return LinkedNode
//         */
//        public LinkedNode getNode(String key) {
//            LinkedNode dumpNode = head.postNode;
//            while (dumpNode != null) {
//                if (dumpNode.key.equals(key)) {
//                    return dumpNode;
//                }
//                dumpNode = dumpNode.postNode;
//            }
//            return null;
//        }
//
//        public void removeNode(LinkedNode selectNode) {
//            //未插满
//            if (selectNode != null) {
//                //移除查到的节点
//                selectNode.postNode.preNode = selectNode.preNode;
//                selectNode.preNode.postNode = selectNode.postNode;
//                //如果是尾节点
//                if (selectNode.key.equals(tail.key)) {
//                    tail = tail.preNode;
//                }
//            }
//
//        }
//
//        /**
//         * 插入操作
//         */
//        public void insert(String key, Object val) {
//            LinkedNode newNode = new LinkedNode(key, val);
//            LinkedNode selectNode = getNode(key);
//
//            if (size + 1 > capacity) {//容量满时
//                //有重复的key，覆盖
//                if (selectNode != null) {
//                    removeNode(selectNode);
//                } else {
//                    //删除最有一个节点
//                    tail = tail.preNode;
//                    tail.postNode = null;
//                }
//                //插入到首位
//                newNode.postNode = head.postNode;
//                head.postNode.preNode = newNode;
//                newNode.preNode = head;
//                head.postNode = newNode;
//            } else {
//                //第一次插入
//                if (head.postNode == null) {
//                    head.postNode = newNode;
//                    newNode.preNode = head;
//                    tail = newNode;
//                } else {//中间插入
//                    removeNode(selectNode);
//                    newNode.postNode = head.postNode;
//                    head.postNode.preNode = newNode;
//                    newNode.preNode = head;
//                    head.postNode = newNode;
//                }
//            }
//            size++;
//        }
//
//
//        public void printFor() {
//
//            LinkedNode postNode = head.postNode;
//            while (postNode != null) {
//                System.out.println(postNode.val);
//                postNode = postNode.postNode;
//            }
//        }
//
//    }
}
