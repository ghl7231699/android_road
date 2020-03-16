package com.example.data;

/**
 * 类描述：Lru算法双向链表的实现
 * 创建人：ghl
 * 创建时间：2019-10-18
 */
public class CustomLru {

    /**
     * 定义双向链表
     */
    static class LinkedNode {
        String key;
        Object val;
        LinkedNode preNode;
        LinkedNode postNode;

        public LinkedNode(String key, Object val) {
            this.key = key;
            this.val = val;
        }
    }

    /**
     * Lru算法
     */
    static class Lru {
        int size;
        //存储容量，默认存3个
        int capacity = 3;
        //用作标记
        LinkedNode head = new LinkedNode("head", new Object());

        LinkedNode tail;


        public Lru(int capacity) {
            this.capacity = capacity;
        }

        /**
         * 查找key所在的节点
         *
         * @param key key
         * @return LinkedNode
         */
        public LinkedNode getNode(String key) {
            LinkedNode dumpNode = head.postNode;
            while (dumpNode != null) {
                if (dumpNode.key.equals(key)) {
                    return dumpNode;
                }
                dumpNode = dumpNode.postNode;
            }
            return null;
        }

        public void removeNode(LinkedNode selectNode) {
            //未插满
            if (selectNode != null) {
                //移除查到的节点
                selectNode.postNode.preNode = selectNode.preNode;
                selectNode.preNode.postNode = selectNode.postNode;
                //如果是尾节点
                if (selectNode.key.equals(tail.key)) {
                    tail = tail.preNode;
                }
            }

        }

        /**
         * 插入操作
         */
        public void insert(String key, Object val) {
            LinkedNode newNode = new LinkedNode(key, val);
            LinkedNode selectNode = getNode(key);

            if (size + 1 > capacity) {//容量满时
                //有重复的key，覆盖
                if (selectNode != null) {
                    removeNode(selectNode);
                } else {
                    //删除最有一个节点
                    tail = tail.preNode;
                    tail.postNode = null;
                }
                //插入到首位
                newNode.postNode = head.postNode;
                head.postNode.preNode = newNode;
                newNode.preNode = head;
                head.postNode = newNode;
            } else {
                //第一次插入
                if (head.postNode == null) {
                    head.postNode = newNode;
                    newNode.preNode = head;
                    tail = newNode;
                } else {//中间插入
                    removeNode(selectNode);
                    newNode.postNode = head.postNode;
                    head.postNode.preNode = newNode;
                    newNode.preNode = head;
                    head.postNode = newNode;
                }
            }
            size++;
        }

    }
}
