package com.cloudlz.tree;


import java.util.ArrayList;
import java.util.List;

public class TreeSolution {

    /**
     * 94. 二叉树的中序遍历
     *
     * @param root
     * @return
     */
    public List<Integer> res = new ArrayList<Integer>();

    public List<Integer> inorderTraversal(TreeNode root) {
        inorder(root);
        return res;
    }

    private void inorder(TreeNode root) {
        if (root == null) {
            return;
        }
        inorder(root.left);
        res.add(root.val);
        inorder(root.right);
    }

    /**
     * 95. 不同的二叉搜索树 II
     *
     * @param n
     * @return
     */
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new ArrayList<TreeNode>();
        }
        return generateBT(1, n);
    }

    private List<TreeNode> generateBT(int start, int end) {
        List<TreeNode> res = new ArrayList<TreeNode>();
        //空节点
        if (start > end) {
            res.add(null);
            return res;
        }
        //递归终止条件，只有一个节点
        if (start == end) {
            TreeNode node = new TreeNode(start);
            res.add(node);
            return res;
        }
        //遍历，每个节点作为根节点，左右子树递归
        for (int i = start; i <= end; i++) {
            List<TreeNode> leftTree = generateBT(start, i - 1);
            List<TreeNode> rightTree = generateBT(i + 1, end);
            for (TreeNode ln : leftTree) {
                for (TreeNode rn : rightTree) {
                    TreeNode root = new TreeNode(i);
                    root.left = ln;
                    root.right = rn;
                    res.add(root);
                }
            }
        }
        return res;
    }

    /**
     * 96. 不同的二叉搜索树，递归超时，改用动态规划
     *
     * @param n
     * @return
     */
    public int numTrees(int n) {
        if (n == 0) {
            return 0;
        }
        return countBT(1, n);
    }

    private int countBT(int start, int end) {
        int count = 0;
        //空节点
        if (start >= end) {
            return 1;
        }
        for (int i = start; i <= end; i++) {
            int leftTreeNum = countBT(start, i - 1);
            int rightTreeNum = countBT(i + 1, end);
            count += leftTreeNum * rightTreeNum;
        }
        return count;
    }

    /**
     * 动态规划，卡特兰数
     *
     * @param n
     * @return
     */
    public int numTrees2(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i < n + 1; i++) {
            for (int j = 1; j < i + 1; j++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }

    /**
     * 98. 验证二叉搜索树
     *
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        //中序遍历是递增的
        List<Integer> tmp = new ArrayList<Integer>();
        return inorder(root, tmp);
    }

    private boolean inorder(TreeNode root, List<Integer> nodes) {
        boolean flag = true;
        if (root == null) {
            return true;
        }
        flag = inorder(root.left, nodes);
        if (flag == true) {
            if (nodes.size() > 0) {
                if (root.val > nodes.get(nodes.size() - 1)) {
                    nodes.add(root.val);
                } else {
                    return false;
                }
            } else {
                nodes.add(root.val);
            }
            flag = inorder(root.right, nodes);
        }
        return flag;
    }

    /**
     * 100. 相同的树
     *
     * @param p
     * @param q
     * @return
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        boolean isSame = true;
        if (p == null && q == null) {
            return true;
        }
        //p、q任意为空或者值不相等则不同
        if ((p == null && q != null) || (p != null && q == null) || p.val != q.val) {
            return false;
        }
        isSame = isSameTree(p.left, q.left);
        if (isSame) {
            isSame = isSameTree(p.right, q.right);
        }
        return isSame;
    }

    /**
     * 101. 对称二叉树
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        List<TreeNode> nextLevel = new ArrayList<TreeNode>();
        if (root == null) {
            return true;
        }
        nextLevel.add(root.left);
        nextLevel.add(root.right);
        return sequence(nextLevel);
    }

    private boolean sequence(List<TreeNode> curLevel) {
        boolean isNull = true;
        List<TreeNode> nextLevel = new ArrayList<TreeNode>();
        for (int i = 0; i < curLevel.size(); i++) {
            if (curLevel.get(i) != null) {
                nextLevel.add(curLevel.get(i).left);
                nextLevel.add(curLevel.get(i).right);
                isNull = false;
            }
        }
        if (isNull) {
            return true;
        }
        for (int i = 0; i < curLevel.size() / 2; i++) {
            if (curLevel.get(i) == null && curLevel.get(curLevel.size() - i - 1) == null) {
                continue;
            }
            if ((curLevel.get(i) == null && curLevel.get(curLevel.size() - i - 1) != null) ||
                    (curLevel.get(i) != null && curLevel.get(curLevel.size() - i - 1) == null) ||
                    (curLevel.get(i).val != curLevel.get(curLevel.size() - i - 1).val)) {
                return false;
            }
        }

        return sequence(nextLevel);
    }

    /**
     * 102. 二叉树的层次遍历
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (root == null) {
            return res;
        }
        List<TreeNode> firstLevel = new ArrayList<TreeNode>();
        firstLevel.add(root);
        level(firstLevel, res);
        return res;
    }
    private void level(List<TreeNode> curLevel, List<List<Integer>> res) {
        boolean isNull = true;
        List<TreeNode> nextLevel = new ArrayList<TreeNode>();
        List<Integer> curList = new ArrayList<Integer>();
        for (int i = 0; i < curLevel.size(); i++) {
            if (curLevel.get(i) != null) {
                nextLevel.add(curLevel.get(i).left);
                nextLevel.add(curLevel.get(i).right);
                isNull = false;
                curList.add(curLevel.get(i).val);
            }
        }
        if (isNull) {
            return;
        }
        res.add(curList);
        level(nextLevel, res);
    }
}
