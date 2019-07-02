package com.cloudlz.list;

public class ListSolution {
    /**
     * 160. 相交链表
     * 找到两个单链表相交的起始节点。
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p1 = headA;
        ListNode p2 = headB;
        //求出长度
        int aLen = 0;
        while (p1 != null) {
            aLen++;
            p1 = p1.next;
        }
        int bLen = 0;
        while (p2 != null) {
            bLen++;
            p2 = p2.next;
        }
        int diff;
        if (aLen < bLen) {
            p1 = headA;
            p2 = headB;
            diff = bLen - aLen;
        } else {
            p1 = headB;
            p2 = headA;
            diff = aLen - bLen;
        }
        //移动到相同长度，再一起向后移动，有相同的即为相交的起始节点
        while (diff > 0 && p2 != null) {
            p2 = p2.next;
            diff--;
        }
        if (p2 == p1) {
            return p2;
        } else {
            while (p1 != null && p2 != null) {
                p1 = p1.next;
                p2 = p2.next;
                if (p2 == p1) {
                    return p2;
                }
            }
        }
        return null;
    }

    /**
     * 203. 移除链表元素
     * 删除链表中等于给定值 val 的所有节点。
     * @param head
     * @param val
     * @return
     */
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return head;
        }
        //注意判断不为空
        while (head != null && head.val == val) {
            head = head.next;
        }
        if (head == null) {
            return head;
        }
        ListNode pre = head;
        while (pre != null && pre.next != null) {
            if (pre.next.val == val) {
                pre.next = pre.next.next;
                //此处pre不要移动
            } else {
                pre = pre.next;
            }
        }
        return head;
    }
    
}
