package com.cloudlz.coding_interviews;

import com.cloudlz.list.ListNode;
import com.cloudlz.tree.TreeNode;

import java.util.ArrayList;
import java.util.Stack;

public class CI {
    /**
     *在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
     * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     * @param target
     * @param array
     * @return
     */
    public boolean Find(int target, int [][] array) {
        //选取右上角的元素
        //当target小于元素a[row][col]时，那么target必定在元素a所在行的左边,
        //即col--；
        //当target大于元素a[row][col]时，那么target必定在元素a所在列的下边,
        //即row++；
        int row = 0;
        int col = array[0].length-1;
        while (row<array.length && col>=0) {
            if (target == array[row][col]) {
                return true;
            } else if (target>array[row][col]) {
                row++;
            } else {
                col--;
            }
        }
        return false;
    }

    /**
     * 替换空格
     * 将一个字符串中的每个空格替换成“%20”
     * @param str
     * @return
     */
    public String replaceSpace(StringBuffer str) {
        for (int i=0;i<str.length();i++) {
            if (str.charAt(i) == ' ') {
                str.replace(i,i+1, "%20");
            }
        }
        return str.toString();
    }

    /**
     * 从尾到头打印链表
     * 输入一个链表，按链表值从尾到头的顺序返回一个ArrayList。
     * @param listNode
     * @return
     */
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        Stack<Integer> stack = new Stack<>();
        ArrayList<Integer> res = new ArrayList<>();
        while (listNode != null) {
            stack.push(listNode.val);
            listNode = listNode.next;
        }
        while (!stack.empty()) {
            res.add(stack.pop());
        }
        return res;
    }

    /**
     * 输入某二叉树的前序遍历和中序遍历的结果，重建出该二叉树
     * @param pre
     * @param in
     * @return
     */
    public TreeNode reConstructBinaryTree(int [] pre, int [] in) {
        return assistConstruct(pre,0, pre.length-1, in,0, in.length-1);
    }
    private TreeNode assistConstruct(int[] pre,int preS, int preE, int[] in, int inS, int inE) {
        if (preS > preE || inS > inE) {
            return null;
        }
        TreeNode newNode = new TreeNode(pre[preS]);
        for (int i=inS;i<=inE;i++) {
            if (in[i] == pre[preS]) {
                newNode.left = assistConstruct(pre, preS+1, preS + i-inS, in, inS, i-1);
                newNode.right = assistConstruct(pre, preS + i - inS + 1, preE, in, i+1, inE);
            }
        }
        return newNode;
    }

    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        while (!stack2.empty()) {
            stack1.push(stack2.pop());
        }
        stack1.push(node);
    }

    public int pop() {
        while (!stack1.empty()) {
            stack2.push(stack1.pop());
        }
        return stack2.pop();
    }

    /**
     * 旋转数组的最小数字
     * @param array
     * @return
     */
    public int minNumberInRotateArray(int [] array) {
        if (array.length == 0) {
            return 0;
        }
        int l=0;
        int r=array.length-1;
        while (l<=r) {
            int mid = l + (r-l)/2;
            if (array[mid] > array[mid+1]) {
                return array[mid+1];
            }
            //左边的大于中间和else或者左边的小于等于中间和else（有重复的数）
            if (array[mid] < array[l]) {
                r = mid-1;
            } else {
                l = mid+1;
            }
        }

        return array[l];
    }


    public static void main (String[] args) {
        CI ci = new CI();
        int[] arr = {1};
        System.out.println(ci.minNumberInRotateArray(arr));
        int n =10;
        n = n>>1;
    }



}
