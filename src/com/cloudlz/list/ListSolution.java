package com.cloudlz.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

    /**
     * 206. 反转链表
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode p = head;
        ListNode q;
        while (p.next != null) {
            q = p.next;
            p.next = q.next;
            q.next = head;
            head = q;
        }
        return head;
    }

    /**
     * 234. 回文链表
     * 请判断一个链表是否为回文链表。
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        Stack<Integer> pdr = new Stack<Integer>();
        ListNode slow = head;
        ListNode fast = slow.next;
        pdr.push(slow.val);
        //快慢指针找出中心
        while (fast != null && fast.next != null) {
            slow = slow.next;
            pdr.add(slow.val);
            fast = fast.next;
            if (fast != null) {
                fast = fast.next;
            }
        }
        //单数
        if (fast == null) {
            pdr.pop();
        }
        slow = slow.next;
        while (slow != null) {
            if (!pdr.empty()) {
                int x = pdr.pop();
                if (x != slow.val) {
                    return false;
                }
                slow = slow.next;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 328. 奇偶链表
     * 给定一个单链表，把所有的奇数节点和偶数节点分别排在一起。请注意，这里的奇数节点和偶数节点指的是节点编号的奇偶性，而不是节点的值的奇偶性。
     * @param head
     * @return
     */
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        ListNode odd = head;
        ListNode even = head.next;
        while (even != null && even.next != null) {
            ListNode moveOdd = even.next;
            even.next = moveOdd.next;
            moveOdd.next = odd.next;
            odd.next = moveOdd;
            odd = moveOdd;
            even = even.next;
        }
        return head;
    }

    /**
     * 445. 两数相加 II
     * 给定两个非空链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储单个数字。将这两数相加会返回一个新的链表。
     * 不能对列表中的节点进行翻转。
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //栈
        Stack<Integer> l1Num = new Stack<Integer>();
        Stack<Integer> l2Num = new Stack<Integer>();
        while (l1 != null) {
            l1Num.push(l1.val);
            l1 = l1.next;
        }
        while (l2 != null) {
            l2Num.push(l2.val);
            l2 = l2.next;
        }
        int carry = 0;
        ListNode head = null;
        while (!l1Num.empty() && !l2Num.empty()) {
            int num1 = l1Num.pop();
            int num2 = l2Num.pop();
            int sum = (num1 + num2 + carry)%10;
            carry = (num1 + num2 + carry)/10;
            ListNode addNode = new ListNode(sum);
            addNode.next = head;
            head = addNode;
        }
        while (!l1Num.empty()) {
            int num = l1Num.pop();
            int sum = (num + carry)%10;
            carry = (num + carry)/10;
            ListNode addNode = new ListNode(sum);
            addNode.next = head;
            head = addNode;
        }
        while(!l2Num.empty()) {
            int num = l2Num.pop();
            int sum = (num + carry)%10;
            carry = (num + carry)/10;
            ListNode addNode = new ListNode(sum);
            addNode.next = head;
            head = addNode;
        }
        if (carry != 0) {
            ListNode addNode = new ListNode(carry);
            addNode.next = head;
            head = addNode;
        }
        return head;
    }

    /**
     * 725. 分隔链表
     * 给定一个头结点为 root 的链表, 编写一个函数以将链表分隔为 k 个连续的部分。
     *
     * 每部分的长度应该尽可能的相等: 任意两部分的长度差距不能超过 1，也就是说可能有些部分为 null。
     *
     * 这k个部分应该按照在链表中出现的顺序进行输出，并且排在前面的部分的长度应该大于或等于后面的长度。
     * @param root
     * @param k
     * @return
     */
    public ListNode[] splitListToParts(ListNode root, int k) {
        ListNode[] res = new ListNode[k];
        //除数是每个数组的基本长度，余数是加在前n个数组上
        if (root == null) {
            return res;
        }
        ListNode p = root;
        int len = 0;
        while (p != null) {
            len++;
            p = p.next;
        }
        p = root;
        int div = len / k;
        int remain = len % k;
        for (int i = 0; i < k; i++) {
            int num = div;
            ListNode q = null;
            ListNode head = q;
            while (num > 0) {
                q = p;
                if (head == null) {
                    head = q;
                }
                p = p.next;
                num--;

            }
            if (remain > 0) {
                q = p;
                if (head == null) {
                    head = q;
                }
                if (p.next != null) {
                    p = p.next;
                }
                remain--;
            }
            if (q != null) {
                q.next = null;
            }
            res[i] = head;
        }
        return res;
    }

    /**
     * 817. 链表组件
     * 给定一个链表（链表结点包含一个整型值）的头结点 head。
     *
     * 同时给定列表 G，该列表是上述链表中整型值的一个子集。
     *
     * 返回列表 G 中组件的个数，这里对组件的定义为：链表中一段最长连续结点的值（该值必须在列表 G 中）构成的集合。
     *
     * @param head
     * @param G
     * @return
     */
    public int numComponents(ListNode head, int[] G) {
        
    }
}
