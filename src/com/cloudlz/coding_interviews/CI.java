package com.cloudlz.coding_interviews;

import com.cloudlz.array.ArraySolution;
import com.cloudlz.list.ListNode;
import com.cloudlz.tree.TreeNode;

import java.util.*;

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
            // 左边的大于中间和else或者左边的小于等于中间和else（有重复的数）
            if (array[mid] < array[l]) {
                r = mid-1;
            } else {
                l = mid+1;
            }
        }

        return array[l];
    }

    /**
     * 树的子结构
     * @param root1
     * @param root2
     * @return
     */
    public boolean HasSubtree(TreeNode root1,TreeNode root2) {
        if (root1== null || root2 == null) {
            return false;
        }
        return subTree(root1, root2);
    }
    private boolean subTree(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 != null) {
            return false;
        }
        if (root2 == null) {
            return true;
        }
        boolean isSubTree = false;
        //当前节点值相同，递归比较左右子树
        if (root1.val == root2.val) {
            isSubTree = subTree(root1.left, root2.left) && subTree(root1.right, root2.right);
        }
        //当前的结果与左子树与root2比较以及右子树与root2比较取或
        return isSubTree || subTree(root1.left, root2) || subTree(root1.right, root2);
    }

    /**
     * 二叉树的镜像
     * @param root
     */
    public void Mirror(TreeNode root) {
        if (root == null){
            return;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        Mirror(root.left);
        Mirror(root.right);

    }

    public boolean IsPopOrder(int [] pushA,int [] popA) {
        if (pushA.length == 0) {
            return true;
        }
        Stack<Integer> stack = new Stack<>();
        int push = 0;
        stack.push(pushA[0]);
        for (int i=0;i<popA.length;i++) {
            if (!stack.empty()) {
                while (push < pushA.length-1 && stack.peek() != popA[i]) {
                    push++;
                    stack.push(pushA[push]);
                }
                if (stack.peek() == popA[i]) {
                    stack.pop();
                }
            }
        }
        if (stack.empty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 二叉搜索树的后序遍历
     * @param sequence
     * @return
     */
    public boolean VerifySquenceOfBST(int [] sequence) {
        if (sequence.length == 0) {
            return false;
        }
        return verify(sequence, 0, sequence.length-1);
    }
    private boolean verify(int[] seq, int start, int end) {
        if (start >= end) {
            return true;
        }
        int pos = end;
        for (int i=end-1;i>=start;i--) {
            if (seq[i]<seq[end]) {
                pos = i;
                break;
            }
        }
        //没有找到，则都为右子树
        if (pos != end) {
            for (int i = pos; i >= start; i--) {
                if (seq[i] > seq[end]) {
                    return false;
                }
            }
        } else {
            pos = start;
        }
        return verify(seq, start, pos) && verify(seq, pos+1, end-1);
    }

    /**
     * 二叉树中和为某一值得路径
     * 输入一颗二叉树的根节点和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
     * 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
     * (注意: 在返回值的list中，数组长度大的数组靠前)
     * @param root
     * @param target
     * @return
     */

    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();
        computePath(res, path, 0, root, target);
        //排序
        for (int i=0;i<res.size();i++) {
            for (int j=0;j<i-1;j++) {
                if (res.get(j).size()<res.get(j+1).size()) {
                    ArrayList<Integer> temp = res.get(j);
                    res.set(j, res.get(j+1));
                    res.set(j+1, temp);
                }
            }
        }
        return res;
    }

    private void computePath(List<ArrayList<Integer>> res, ArrayList<Integer> path, int sum, TreeNode root,int target) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            if (sum + root.val == target) {
                path.add(root.val);
                res.add(path);
            }
        }
        if (root.val + sum > target) {
            return;
        }
        ArrayList<Integer> newPath = new ArrayList<>();
        if (path.size() != 0) {
            for (int i : path) {
                newPath.add(i);
            }
        }
        newPath.add(root.val);
        computePath(res, newPath, sum+root.val, root.left, target);

        computePath(res, newPath, sum+root.val, root.right, target);
    }

    class RandomListNode {
        int label;
        RandomListNode next = null;
        RandomListNode random = null;

        RandomListNode(int label) {
            this.label = label;
        }
    }

    /**
     * 复杂链表的复制
     * 输入一个复杂链表（每个节点中有节点值，以及两个指针，
     * 一个指向下一个节点，另一个特殊指针指向任意一个节点），
     * 返回结果为复制后复杂链表的head。
     * @param pHead
     * @return
     */
    public RandomListNode Clone(RandomListNode pHead)
    {
        if (pHead == null) {
            return null;
        }
        //先把next的节点复制出来
        RandomListNode p = pHead.next;
        RandomListNode cpHead = new RandomListNode(pHead.label);
        RandomListNode q = cpHead;
        while (p != null) {
            RandomListNode newNode = new RandomListNode(p.label);
            p = p.next;
            q.next = newNode;
            q = newNode;
        }
        p = pHead;
        q = cpHead;
        //遍历处理random的节点
        while (p != null) {
            RandomListNode randNode = p.random;
            RandomListNode r = pHead;
            RandomListNode s = cpHead;
            while (r != null && r != randNode) {
                r = r.next;
                s = s.next;
            }
            if (r == randNode) {
                q.random = s;
            }
            p = p.next;
            q = q.next;
        }
        return cpHead;
    }

    /**
     * 二叉搜索树与双向链表
     * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。
     * 要求不能创建任何新的结点，只能调整树中结点指针的指向。
     * @param pRootOfTree
     * @return
     */
    public TreeNode Convert(TreeNode pRootOfTree) {
        // 每次在递归遍历的时候设置一个pre，记录中序遍历时当前节点的前节点，
        // 然后将当前节点的左指针指向pre节点，然后如果pre节点不为空则将pre的右节点指向当前节点，
        // 由此就形成了一个双向链表的前后指针。
        if (pRootOfTree == null) {
            return null;
        }
        TreeNode[] pre = new TreeNode[1];
        convertNode(pRootOfTree, pre);
        TreeNode p = pRootOfTree;
        while (p != null && p.left != null) {
            p = p.left;
        }
        return p;
    }

    private void convertNode(TreeNode root, TreeNode[] pre) {
        if (root.left != null) {
            convertNode(root.left, pre);
        }
        ////设置left指向前一个节点
        root.left = pre[0];
        if (pre[0] != null) {
            //pre的right指向root
            pre[0].right = root;
        }
        //当前节点为双向链表尾节点
        pre[0] = root;
        if (root.right != null) {
            convertNode(root.right, pre);
        }
    }

    /**
     * 字符串的排列
     * 输入一个字符串,按字典序打印出该字符串中字符的所有排列。
     * @param str
     * @return
     */
    public ArrayList<String> Permutation(String str) {
        //全排列，递归
        //求所有可能出现在第一个位置的字符；将第一个字符和后面的字符依次交换
        //固定第一个字符，对第一个字符后面的所有字符求全排列。第一个字符后面的所有字符又可以分为两部分
        ArrayList<String> res = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return res;
        }
        char[] charArray = str.toCharArray();
        TreeSet<String> treeSet = new TreeSet();
        permutationCore(charArray, treeSet, 0);
        res.addAll(treeSet);
        return res;
    }

    private void permutationCore(char[] array, TreeSet<String> treeSet, int pos) {
        if (pos > array.length-1) {
            return;
        }
        if (pos == array.length-1) {
            treeSet.add(String.valueOf(array));
        }
        for (int i=pos;i<array.length;i++) {
            swap(array, i, pos);
            permutationCore(array, treeSet, pos+1);
            swap(array, i, pos);
        }
    }
    private void swap(char[] charArray,int i,int j) {
        char temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
    }

    /**
     * 数组中出现次数超过一半的数字
     * @param array
     * @return
     */
    public int MoreThanHalfNum_Solution(int [] array) {
        //排序，中位数肯定是这个数
        Arrays.sort(array);
        int half = array.length/2;
        int count = 0;
        for(int i=0; i<array.length; i++){
            if(array[i] == array[half])
                count ++;
        }
        if(count > half){
            return array[half];
        } else {
            return 0;
        }
    }

    /**
     * 最小的K个数
     * @param input
     * @param k
     * @return
     */
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        ArrayList<Integer> res = new ArrayList<>();
        if (input.length == 0 || k == 0 || k > input.length) {
            return res;
        }
        //大顶堆
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(k, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });
        for (int i=0;i<input.length;i++) {
            if (maxHeap.size() != k) {
                maxHeap.add(input[i]);
            } else if (maxHeap.peek() > input[i]) {
                maxHeap.poll();
                maxHeap.add(input[i]);
            }
        }
        for (Integer integer : maxHeap) {
            res.add(integer);
        }
        return res;
    }

    /**
     * 整数中1出现的次数
     * @param n
     * @return
     */
    public int NumberOf1Between1AndN_Solution(int n) {
        int count = 0;
        int i = 1;
        //遍历每个数
        while (i<=n) {
            //每一位是不是为1
            int tmp = i;
            while (tmp != 0) {
                if (tmp%10 == 1) {
                    count++;
                }
                tmp = tmp/10;
            }
            i++;
        }
        return count;
    }

    /**
     * 把数组排成最小的数
     * @param numbers
     * @return
     */
    public String PrintMinNumber(int [] numbers) {
        if(numbers.length==0||numbers==null)
            return "";
        Integer[] nums = new Integer[numbers.length];
        int k = 0;
        for (int val : numbers) {
            nums[k++] = Integer.valueOf(val);
        }
        Arrays.sort(nums,new Comparator<Integer>() {
            //对数组numbers用自定义方法排序
            @Override
            public int compare(Integer o1, Integer o2) {
                //重写compare方法来比较o1,o2的大小，当o1+""+o2和o2+""+o1
                //都是字符串，比较o1,o2的大小其实是比较两个子串的大小
                return (o1+""+o2).compareTo(o2+""+o1);
            }

        });
        String ss=new String();
        for(int i=0;i<nums.length;i++)
        {
            ss+=nums[i];
        }
        return ss;
    }

    class RepeatChar {
        int pos;
        int count;
    }
    /**
     * 第一次只出现一次的字符
     * @param str
     * @return
     */

    public int FirstNotRepeatingChar(String str) {
        HashMap<Character, RepeatChar> map = new HashMap<>();
        for (int i=0;i<str.length();i++) {
            Character ch = str.charAt(i);
            RepeatChar repeatChar = map.get(ch);
            if (repeatChar != null) {
                repeatChar.count++;
                map.put(ch, repeatChar);
            } else {
                repeatChar = new RepeatChar();
                repeatChar.pos = i;
                repeatChar.count = 1;
                map.put(ch, repeatChar);
            }
        }
        //entrySet输出是按key的ASCII码排序
        int firstPos = -1;
        for (Map.Entry<Character, RepeatChar> kv : map.entrySet()) {
            if (kv.getValue().count == 1) {
                if (firstPos == -1 || kv.getValue().pos < firstPos) {
                    firstPos = kv.getValue().pos;
                }
            }
        }
        return firstPos;
    }

    /**
     * 数组中的逆序对
     * 解题思路：归并排序是有阶段性结果的排序，有序的数组，逆序对不需要比较
     * @param array
     * @return
     */
    int pairsCount = 0;
    public int reversePairs(int[] array) {
        int[] tmpArr = new int[array.length];
        divide(array, 0, array.length-1, tmpArr);
        return pairsCount;
    }
    public void divide(int[] arr, int left, int right, int[] tmpArr) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left)/2;
        divide(arr, left, mid, tmpArr);
        divide(arr, mid+1, right, tmpArr);
        merge(arr, left, right, mid, tmpArr);
    }

    public void merge(int[] arr, int left, int right, int mid, int[] tmpArr) {
        int l = left;
        int r = mid + 1;
        int k = 0;
        while (l <= mid && r <= right) {
            // 右区间归并的时候，当前值比左区间所有值都小，说明逆序对的个数就是左区间剩余的个数
            if (arr[l] > arr[r]) {
                pairsCount += mid - l + 1;
                tmpArr[k++] = arr[r++];
            } else {
                tmpArr[k++] = arr[l++];
            }
        }
        while (l <= mid) {
            tmpArr[k++] = arr[l++];
        }
        while (r <= left) {
            tmpArr[k++] = arr[r++];
        }
        for (int i=0;i<k;i++) {
            arr[left+i] = tmpArr[i];
        }
    }

    /**
     * 面试题 17.26. 稀疏相似度
     * @param docs
     * @return
     */
    public List<String> computeSimilarities(int[][] docs) {
        // 元素交集除以并集
        // 元素的交集可以通过map记录元素出现在哪些数组，并集可以通过两个数组的总数-交集里的总数
        List<String> res = new ArrayList<>();
        Map<Integer, List<Integer>> map = new HashMap<>();
        // 记录交集
        int[][] nums = new int[docs.length][docs.length];
        for (int i=0; i< docs.length; i++) {
            for (int j=0; j<docs[i].length; j++) {
                // list表示docs[i][j]出现的数组
                List<Integer> list = map.get(docs[i][j]);
                if (list == null) {
                    list = new ArrayList<>();
                    map.put(docs[i][j], list);
                } else {
                    for (Integer k : list) {
                        nums[i][k]++;
                    }
                }
                list.add(i);
            }
            for (int k=0; k<docs.length; k++) {
                if (nums[i][k] > 0) {
                    res.add(k + "," + i + ": " +
                            String.format("%.4f", (double) nums[i][k]/(docs[i].length+docs[k].length-nums[i][k])));
                }
            }
        }
        return res;
    }

    /**
     * 面试题 17.23. 最大黑方阵
     * @param matrix
     * @return
     */
    public int[] findSquare(int[][] matrix) {
        if (matrix.length == 0) {
            return new int[0];
        }
        // a数组记录（i，j）向上和向左的最大值
        int[][] leftMax = new int[matrix.length][matrix.length];
        int[][] upMax = new int[matrix.length][matrix.length];
        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j< matrix.length; j++) {
                if (matrix[i][j] == 0) {
                    if (i==0 && j==0) {
                        leftMax[i][j] = 1;
                        upMax[i][j] = 1;
                    } else if (i==0 && j>0) {
                        upMax[i][j] = 1;
                        leftMax[i][j] = leftMax[i][j-1] + 1;
                    } else if (i>0 && j==0) {
                        upMax[i][j] = upMax[i-1][j] + 1;
                        leftMax[i][j] = 1;
                    } else {
                        upMax[i][j] = upMax[i-1][j] + 1;
                        leftMax[i][j] = leftMax[i][j-1] + 1;
                    }
                }
            }
        }
        // 结果集
        int[] res = new int[]{-1,-1,-1};
        // 从右下角开始遍历
        for (int i=matrix.length-1; i>=0; i--) {
            for (int j=matrix.length-1; j>=0; j--) {
                // 方阵的边长
                int edge = Math.min(upMax[i][j], leftMax[i][j]);
                while (edge > 0) {
                    if (res[2] > edge) {
                        break;
                    }
                    int x = i-edge+1;
                    int y = j-edge+1;
                    // 左下角的点upMax的值大于边长判断左边是否满足，右上角点的leftMax值大于边长判断上边是否满足
                    if(upMax[i][y]>=edge && leftMax[x][j]>=edge) {
                        if (edge > res[2]) {
                            res[0] = x;
                            res[1] = y;
                            res[2] = edge;
                        } else if (edge == res[2] && x<=res[0]) {
                            if (x < res[0]) {
                                res[0] = x;
                                res[1] = y;
                            } else if(x == res[0] && y < res[1]) {
                                res[1] = y;
                            }
                        }
                    }
                    edge--;
                }
            }
        }
        if (res[0] !=-1) {
            return res;
        } else {
            return new int[0];
        }
    }

    /**
     * 统计全1子矩形
     * @param mat
     * @return
     */
    public int numSubmat(int[][] mat) {
        int count = 0;
        // 记录一个点的向左的个数
        int[][] leftCount = new int[mat.length][mat[0].length];
        for (int i=0; i< mat.length; i++) {
            for (int j=0; j< mat[0].length; j++) {
                if (mat[i][j] == 1) {
                    if (j==0) {
                        leftCount[i][j] = 1;
                    } else {
                        leftCount[i][j] = leftCount[i][j-1] + 1;
                    }
                }
            }
        }

        for (int i=0; i< mat.length; i++) {
            for (int j=0; j< mat[0].length; j++) {
                if (mat[i][j]==0) {
                    continue;
                }
                int leftLen = mat[0].length;
                for (int k=i; k>=0 && mat[k][j]==1; k--) {
                    leftLen = Math.min(leftLen, leftCount[k][j]);
                    // 向左的个数就是矩形的个数
                    count += leftLen;
                }
            }
        }
        return count;
    }

    /**
     * 1505. 最多 K 次交换相邻数位后得到的最小整数
     * @param num
     * @param k
     * @return
     */
    public String minInteger(String num, int k) {

        return "";
    }

    public String pushDominoes(String dominoes) {
        if (dominoes.length() <= 1) {
            return dominoes;
        }
        StringBuilder tmp = new StringBuilder(dominoes);
        Character startChar = dominoes.charAt(0);
        int startPos = 0;
        int len = 0;
        if (startChar == '.') {
            len = 1;
        }
        for (int i=1;i<dominoes.length();i++) {
            if (dominoes.charAt(i) == 'L') {
                if (startChar == 'R' && len > 1) {
                    //朝中间推
                    if (len%2 == 0) {
                        int halflen = len/2;
                        int j;
                        for (j = startPos+1 ;j<=startPos+halflen;j++) {
                            tmp.setCharAt(j, 'R');
                        }
                        for (j=startPos+halflen+1;j<i;j++) {
                            tmp.setCharAt(j, 'L');
                        }
                    } else {
                        int halflen = len/2;
                        int j;
                        for (j = startPos+1 ;j<startPos+halflen+1;j++) {
                            tmp.setCharAt(j, 'R');
                        }
                        for (j=startPos+halflen+2;j<i;j++) {
                            tmp.setCharAt(j, 'L');
                        }
                    }
                } else if (startChar == 'L' || startChar == '.') { // 朝左边推
                    for (int j=startPos;j<i;j++) {
                        tmp.setCharAt(j, 'L');
                    }
                }
                startChar = 'L';
                startPos = i;
                len = 0;
            } else if (dominoes.charAt(i) == 'R') {
                if (startChar == 'R') {
                    for (int j=startPos;j<i;j++) {
                        tmp.setCharAt(j, 'R');
                    }
                }
                startChar = 'R';
                startPos = i;
                len = 0;
            } else {
                len++;
            }
        }
        if (len != 0 && dominoes.charAt(dominoes.length()-1) == '.' && startChar == 'R') {
            for (int i=startPos; i<dominoes.length();i++) {
                tmp.setCharAt(i, 'R');
            }
        }
        return tmp.toString();
    }

    public static void main (String[] args) {
        String s = "0123";
        String ss = s.substring(0,2);
        StringBuilder sb = new StringBuilder(s);
        System.out.println(ss);
    }



}
