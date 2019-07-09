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

    /**
     * 103. 二叉树的锯齿形层次遍历
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (root == null) {
            return res;
        }
        List<TreeNode> firstLevel = new ArrayList<TreeNode>();
        firstLevel.add(root);
        zigzagLevel(firstLevel, res, 1);
        return res;
    }
    private void zigzagLevel(List<TreeNode> curLevel, List<List<Integer>> res, int order) {
        boolean isNull = true;
        List<TreeNode> nextLevel = new ArrayList<TreeNode>();
        List<Integer> curList = new ArrayList<Integer>();
        for (int i = 0; i < curLevel.size(); i++) {
            if (curLevel.get(i) != null) {
                nextLevel.add(curLevel.get(i).left);
                nextLevel.add(curLevel.get(i).right);
                isNull = false;

            }
        }
        if (isNull) {
            return;
        }
        if (order%2 == 1) {
            for (int i = 0; i < curLevel.size(); i++) {
                if (curLevel.get(i) != null) {
                    curList.add(curLevel.get(i).val);
                }
            }
        } else {
            for (int i = curLevel.size()-1; i >=0 ; i--) {
                if (curLevel.get(i) != null) {
                    curList.add(curLevel.get(i).val);
                }
            }
        }
        res.add(curList);
        zigzagLevel(nextLevel, res, order+1);
    }

    /**
     * 104. 二叉树的最大深度
     * @param root
     * @return
     */
    int maxLen = 0;
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        //有问题,全局变量会影响
//        int leftLen = maxDepth(root.left) + 1;
//        int rightLen = maxDepth(root.right) + 1;
//        if (leftLen > maxLen) {
//            maxLen = leftLen;
//        }
//        if (rightLen > maxLen) {
//            maxLen = rightLen;
//        }
//        return maxLen;
        int leftLen = maxDepth(root.left);
        int rightLen = maxDepth(root.right);
        if (leftLen > rightLen) {
            return leftLen + 1;
        } else {
            return rightLen + 1;
        }
    }

    /**
     * 105. 从前序与中序遍历序列构造二叉树
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return construnctTree(preorder, 0, preorder.length-1, inorder, 0, inorder.length-1);
    }
    private TreeNode construnctTree(int[] preorder, int preS, int preE, int[] inorder, int inS, int inE) {
        if (preS > preE || inS > inE) {
            return null;
        }
        TreeNode node = new TreeNode(preorder[preS]);
        //前序遍历的点在中序遍历中作为根节点，将序列分为左子树和右子树
        for (int i = inS;i<=inE;i++) {
            if (inorder[i] == preorder[preS]) {
                //左子树
                node.left = construnctTree(preorder, preS+1, preS+(i-inS), inorder, inS, i-1);
                //右子树
                node.right = construnctTree(preorder, preS+(i-inS)+1, preE, inorder, i+1, inE);
            }
        }
        return node;
    }

    /**
     *106. 从中序与后序遍历序列构造二叉树
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        return construnctTree2(inorder, 0, inorder.length-1, postorder, 0, postorder.length-1);
    }
    private TreeNode construnctTree2(int[] inorder, int inS, int inE, int[] postorder, int postS, int postE) {
        if (inS > inE || postS > postE) {
            return null;
        }
        //序列的最后一个节点是根节点
        TreeNode node = new TreeNode(postorder[postE]);
        for (int i=inS;i<=inE;i++) {
            if (inorder[i] == postorder[postE]) {
                node.left = construnctTree2(inorder, inS, i-1, postorder, postS, postS+(i-inS)-1);
                node.right = construnctTree2(inorder, i+1, inE, postorder, postS+(i-inS), postE-1);
            }
        }
        return node;
    }

    /**
     * 107. 二叉树的层次遍历 II
     * 给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        /**
         * 思路：先从上往下层次遍历再将结果反转
         */
        return null;
    }

    /**
     * 108. 将有序数组转换为二叉搜索树
     * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return construtBST(nums, 0, nums.length-1);
    }

    private TreeNode construtBST(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = start + (end-start)/2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = construtBST(nums, start, mid-1);
        node.right = construtBST(nums, mid+1, end);
        return node;
    }

    /**
     *110. 平衡二叉树
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        int leftLen = treeLength(root.left);
        int rightLen = treeLength(root.right);
        if (leftLen-rightLen>1 || rightLen-leftLen>1) {
            return false;
        }
        boolean leftB = isBalanced(root.left);
        boolean rightB = isBalanced(root.right);
        return leftB && rightB;
    }

    //求出树的高度
    private int treeLength(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = treeLength(root.left) + 1;
        int right = treeLength(root.right) + 1;
        if (left > right) {
            return left;
        } else {
            return right;
        }
    }

    /**
     * 111. 二叉树的最小深度
     * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
     * @param root
     * @return
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        //如果根节点的左右节点都为空
        if (root.left == null && root.right == null) {
            return 1;
        }
        //如果根节点的左节点为空，就算右边的长度
        if (root.left == null) {
            return minDepth(root.right) + 1;
        }
        if (root.right == null) {
            return minDepth(root.left) + 1;
        }
        int left = minDepth(root.left) + 1;
        int right = minDepth(root.right) + 1;
        if (left < right) {
            return left;
        } else {
            return right;
        }
    }

    /**
     * 112. 路径总和
     * 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
     * @param root
     * @param sum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        
    }

}
