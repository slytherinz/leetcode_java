package com.cloudlz.tree;


import java.util.*;

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
        if (root == null) {
            return false;
        }
        return nextPathSum(root, sum);
    }
    private boolean nextPathSum(TreeNode root, int sum) {
        if (root == null) {
            if (sum == 0) {
                return true;
            } else {
                return false;
            }
        }
        if (root.left == null && root.right == null) {
            if (sum-root.val == 0) {
                return true;
            } else {
                return false;
            }
        }
        if (root.left == null) {
            return nextPathSum(root.right, sum-root.val);
        }
        if (root.right == null) {
            return nextPathSum(root.left, sum-root.val);
        }
        boolean leftHas = nextPathSum(root.left, sum-root.val);
        boolean rightHas = nextPathSum(root.right, sum-root.val);
        return leftHas || rightHas;
    }

    /**
     * 113. 路径总和 II
     * 给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。
     * @param root
     * @param sum
     * @return
     */
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        //为空要输出[]
        if (root == null) {
            return res;
        }
        List<Integer> path = new ArrayList<Integer>();
        listPath(root, sum, res, path);
        return res;
    }

    private void listPath(TreeNode root, int sum, List<List<Integer>> res, List<Integer> pathList) {
        if (root == null) {
            if (sum == 0) {
                if (pathList.size() != 0) {
                    res.add(pathList);
                }
                return;
            } else {
                return;
            }
        }
        ////[0,1,1] 1的情况
        if (root.left == null && root.right == null) {
            if (sum-root.val == 0) {
                List<Integer> path = new ArrayList<Integer>();
                if (pathList.size() != 0) {
                    for (Integer i : pathList) {
                        path.add(i);
                    }
                }
                path.add(root.val);
                res.add(path);
                return;
            } else {
                return;
            }
        }
        List<Integer> path = new ArrayList<Integer>();
        if (pathList.size() != 0) {
            for (Integer i : pathList) {
                path.add(i);
            }
        }
        path.add(root.val);
        if (root.left == null || root.right == null) {
            if (root.left == null) {
                listPath(root.right, sum - root.val, res, path);
            }
            if (root.right == null) {
                listPath(root.left, sum - root.val, res, path);
            }
        } else {
            listPath(root.left, sum - root.val, res, path);
            listPath(root.right, sum - root.val, res, path);
        }
    }

    /**
     * 124. 二叉树中的最大路径和
     * @param root
     * @return
     */
    public int maxPathSum(TreeNode root) {
        return 0;
    }

    /**
     * 129. 求根到叶子节点数字之和
     * 给定一个二叉树，它的每个结点都存放一个 0-9 的数字，每条从根到叶子节点的路径都代表一个数字。
     *
     * 例如，从根到叶子节点路径 1->2->3 代表数字 123。
     *
     * 计算从根到叶子节点生成的所有数字之和。
     * @param root
     * @return
     */
    public int sumNumbers(TreeNode root) {
        /**
         * 思路：递归求所有的路径，再加上
         */
        return 0;
    }

    /**
     * 222. 完全二叉树的节点个数
     * @param root
     * @return
     */
    int nodesCount = 0;
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        List<TreeNode> nextLevel = new ArrayList<TreeNode>();
        nextLevel.add(root);
        levelOrder(nextLevel);
        return nodesCount;
    }
    private void levelOrder(List<TreeNode> curLevel) {
        List<TreeNode> nextLevel = new ArrayList<TreeNode>();
        for (TreeNode node : curLevel) {
            nodesCount++;
            if (node.left != null) {
                nextLevel.add(node.left);
            }
            if (node.right != null) {
                nextLevel.add(node.right);
            }
        }
        if (nextLevel.size() != 0) {
            levelOrder(nextLevel);
        }
    }

    /**
     * 226. 翻转二叉树
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    /**
     * 230. 二叉搜索树中第K小的元素
     * @param root
     * @param k
     * @return
     */
    List<Integer> nodeList = new ArrayList<Integer>();
    public int kthSmallest(TreeNode root, int k) {
        //二叉搜索树中序遍历递增
        smallest(root);
        return nodeList.get(k-1);
    }
    private void smallest(TreeNode root) {
        if (root == null) {
            return;
        }
        smallest(root.left);
        nodeList.add(root.val);
        smallest(root.right);
    }

    /**
     * 235. 二叉搜索树的最近公共祖先
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //pq都在root左边，或者一左一右，或者都在右边
        if(p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        }
        return root;
    }

    /**
     * 236. 二叉树的最近公共祖先
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        List<Integer> inorderList = new ArrayList<Integer>();
        inOrder(root, inorderList);
        int pPos=0;
        int qPos=0;
        for (int i=0;i<inorderList.size();i++) {
            if(inorderList.get(i) == p.val) {
                pPos = i;
                continue;
            }
            if(inorderList.get(i) == q.val) {
                qPos = i;
            }
        }
        if (pPos<qPos) {
            return commonAncestor(root, pPos, qPos, inorderList);
        } else {
            return commonAncestor(root, qPos, pPos, inorderList);
        }
    }
    private void inOrder(TreeNode root, List<Integer> inorderList) {
        if (root == null) {
            return;
        }
        inOrder(root.left, inorderList);
        inorderList.add(root.val);
        inOrder(root.right, inorderList);
    }
    private TreeNode commonAncestor(TreeNode root, int start, int end, List<Integer> inorderList) {
        if (root == null) {
            return null;
        }
        for (int i=start;i<=end;i++) {
            if (root.val == inorderList.get(i)) {
                return root;
            }
        }
        TreeNode leftCommon = commonAncestor(root.left, start, end, inorderList);
        TreeNode rightCommon = commonAncestor(root.right, start, end, inorderList);
        return leftCommon != null?leftCommon:rightCommon;
    }

    //二叉树的最近公共祖先
    public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root == p || root == q){
            return root;
        }
        //查看左子树中是否有目标结点，没有为null
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        //查看右子树是否有目标节点，没有为null
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        //都不为空，说明做右子树都有目标结点，则公共祖先就是本身
        if(left != null && right != null){
            return root;
        }
        //如果发现了目标节点，则继续向上标记为该目标节点
        return left == null ? right : left;
    }

    /**
     * 257. 二叉树的所有路径
     * @param root
     * @return
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<String>();
        constructPaths(root, "", paths);
        return paths;
    }
    private void constructPaths(TreeNode root, String path, List<String> paths) {
        if (root != null) {
            path += Integer.toString(root.val);
            if ((root.left == null) && (root.right == null))  // 当前节点是叶子节点
                paths.add(path);  // 把路径加入
            else {
                path += "->";  // 当前节点不是叶子节点，继续递归遍历
                constructPaths(root.left, path, paths);
                constructPaths(root.right, path, paths);
            }
        }
    }

    /**
     * 404. 左叶子之和
     * @param root
     * @return
     */
    int leftSum = 0;
    public int sumOfLeftLeaves(TreeNode root) {
        sumLeftLeaves(root);
        return leftSum;
    }
    private void sumLeftLeaves(TreeNode root) {
        if (root != null) {
            if (root.left != null && (root.left.left == null && root.left.right == null)) {
                leftSum += root.left.val;
            }
            sumLeftLeaves(root.left);
            sumLeftLeaves(root.right);
        }
    }


    /**
     *437. 路径总和 III
     * @param root
     * @param sum
     * @return
     */
    public int pathSum3(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }
        return dfsPath(root, sum) + pathSum3(root.left, sum) + pathSum3(root.right, sum);
    }
    private int dfsPath(TreeNode node, int sum) {
        if (node == null) {
            return 0;
        }
        int count = 0;
        if (node.val == sum) {
            count = 1;
        }
        return count + dfsPath(node.left, sum - node.val) + dfsPath(node.right, sum - node.val);
    }

    /**
     * 450. 删除二叉搜索树中的节点
     * @param root
     * @param key
     * @return
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        //找到删除的节点
        if (root.val == key) {
            //左右子树为空，则直接拼接不为空的一边
            if (root.left == null || root.right == null) {
                root = (root.left != null)?root.left : root.right;
            } else {
                //左右子树都不为空
                root.val = findMinNode(root.right).val;
                //删除该节点
                root.right = deleteNode(root.right, root.val);
            }
        } else if(key < root.val) {
            root.left = deleteNode(root.left, key);
        } else {
            root.right = deleteNode(root.right, key);
        }
        return root;
    }
    private TreeNode findMinNode(TreeNode root) {
        if (root == null) {
            return null;
        }
        if (root.left == null) {
            return root;
        }
        return findMinNode(root.left);
    }

    /**
     * 501. 二叉搜索树中的众数
     * @param root
     * @return
     */
    List<Integer> modeList = new ArrayList<Integer>();
    int maxVal = 0;
    int max = 0;
    int cur = 0;
    public int[] findMode(TreeNode root) {
        if (root == null) {
            return new int[] {};
        }
        modeInorder(root);
        int[] res = new int[modeList.size()];
        for(int i=0;i<modeList.size();i++) {
            res[i]=modeList.get(i);
        }
        return res;
    }
    //中序
    private void modeInorder(TreeNode root) {
        if (root == null) {
            return;
        }
        modeInorder(root.left);
        if (root.val == maxVal) {
            cur++;
        } else {
            cur = 1;
        }
        if (cur == max) {
            modeList.add(root.val);
        }
        if (cur > max) {
            max = cur;
            modeList.clear();
            modeList.add(root.val);
        }
        maxVal = root.val;
        modeInorder(root.right);
    }

    /**
     * 508. 出现次数最多的子树元素和
     * @param root
     * @return
     */
    Map<Integer, Integer> sumList = new HashMap<Integer, Integer>();
    int maxCount = 1;
    public int[] findFrequentTreeSum(TreeNode root) {
        if (root == null) {
            return new int[] {};
        }
        frequentTreeSum(root);
        List<Integer> sumList2 = getKey(sumList, maxCount);
        int[] res = new int[sumList2.size()];
        for(int i=0;i<sumList2.size();i++) {
            res[i]=sumList2.get(i);
        }
        return res;
    }
    private int frequentTreeSum(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int total = node.val;
        total += frequentTreeSum(node.left);
        total += frequentTreeSum(node.right);
        if (sumList.containsKey(total)) {
            int count = sumList.get(total)+1;
            if (count > maxCount) {
                maxCount = count;
            }
            sumList.put(total, sumList.get(total)+1);
        } else {
            sumList.put(total, 1);
        }
        return total;
    }
    //map根据value找key
    private List<Integer> getKey(Map<Integer, Integer> map, Integer value){
        List<Integer> keyList = new ArrayList<Integer>();
        for(Integer key: map.keySet()){
            if(map.get(key).equals(value)){
                keyList.add(key);
            }
        }
        return keyList;
    }

    /**
     * 513. 找树左下角的值
     * @param root
     * @return
     */
    int bottomLeftVal = 0;
    public int findBottomLeftValue(TreeNode root) {
        //层序遍历
        List<TreeNode> firstLevel = new ArrayList<TreeNode>();
        firstLevel.add(root);
        getLevelOrder(firstLevel);
        return bottomLeftVal;
    }
    private void getLevelOrder(List<TreeNode> curLevel) {
        List<TreeNode> nextLevel = new ArrayList<TreeNode>();
        for (TreeNode thisLevelNode : curLevel) {
            if (thisLevelNode.left != null) {
                nextLevel.add(thisLevelNode.left);
            }
            if (thisLevelNode.right != null) {
                nextLevel.add(thisLevelNode.right);
            }
        }
        if (nextLevel.size() > 0) {
            getLevelOrder(nextLevel);
        } else {
            bottomLeftVal = curLevel.get(0).val;
        }
    }

    /**
     * 515. 在每个树行中找最大值
     * @param root
     * @return
     */
    List<Integer> largeLevelVal = new ArrayList<Integer>();
    public List<Integer> largestValues(TreeNode root) {
        //层序
        if (root == null) {
            return largeLevelVal;
        }
        List<TreeNode> firstLevel = new ArrayList<TreeNode>();
        firstLevel.add(root);
        getLevelOrder2(firstLevel);
        return largeLevelVal;
    }
    private void getLevelOrder2(List<TreeNode> curLevel) {
        List<TreeNode> nextLevel = new ArrayList<TreeNode>();
        int maxLargeVal = curLevel.get(0).val;
        for (TreeNode thisLevelNode : curLevel) {
            if (thisLevelNode.left != null) {
                nextLevel.add(thisLevelNode.left);
            }
            if (thisLevelNode.right != null) {
                nextLevel.add(thisLevelNode.right);
            }
            if (thisLevelNode.val > maxLargeVal) {
                maxLargeVal = thisLevelNode.val;
            }
        }
        largeLevelVal.add(maxLargeVal);
        if (nextLevel.size() > 0) {
            getLevelOrder2(nextLevel);
        }
    }

    /**
     * 530. 二叉搜索树的最小绝对差
     * 给定一个所有节点为非负值的二叉搜索树，求树中任意两节点的差的绝对值的最小值。
     * @param root
     * @return
     */
    int minDiff = Integer.MAX_VALUE;
    public int getMinimumDifference(TreeNode root) {
        //中序遍历为递增
        inorder(root);
        for (int i=0;i<res.size()-1;i++) {
            if (res.get(i+1)-res.get(i) < minDiff) {
                minDiff = res.get(i+1)-res.get(i);
            }
        }
        return minDiff;
    }

    /**
     * 538. 把二叉搜索树转换为累加树
     * 给定一个二叉搜索树（Binary Search Tree），把它转换成为累加树（Greater Tree)，使得每个节点的值是原来的节点值加上所有大于它的节点值之和。
     * @param root
     * @return
     */
    public TreeNode convertBST(TreeNode root) {
        //右中左是从大到小
        rightToLeft(root, 0);
        return root;
    }
    private int rightToLeft(TreeNode root, int sum) {
        if (root == null) {
            return sum;
        }
        root.val += rightToLeft(root.right, sum);
        return rightToLeft(root.left, root.val);
    }

    /**
     * 543. 二叉树的直径
     * 一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过根结点。
     * @param root
     * @return
     */
    int diameter = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        computePath(root);
        return diameter;
    }
    private int computePath(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftPath =  computePath(root.left);
        int rightPath = computePath(root.right);
        if (leftPath + rightPath  > diameter) {
            diameter = leftPath + rightPath;
        }
        return leftPath > rightPath ? leftPath + 1 : rightPath + 1;
    }

    /**
     * 563. 二叉树的坡度
     * 一个树的节点的坡度定义即为，该节点左子树的结点之和和右子树结点之和的差的绝对值。空结点的的坡度是0。
     * 整个树的坡度就是其所有节点的坡度之和。
     * @param root
     * @return
     */
    int tiltAll = 0;
    public int findTilt(TreeNode root) {
        leftRightSum(root);
        return tiltAll;
    }
    private int leftRightSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftSum = leftRightSum(root.left);
        int rightSum = leftRightSum(root.right);
        tiltAll += Math.abs(leftSum-rightSum);
        return leftSum + rightSum + root.val;
    }

    /**
     * 572. 另一个树的子树
     * @param s
     * @param t
     * @return
     */
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s == null) {
            return false;
        }
        if (isSameTree(s, t)) {
            return true;
        }
        return isSubtree(s.left, t) || isSubtree(s.right, t);
    }

    /**
     * 617. 合并二叉树
     * @param t1
     * @param t2
     * @return
     */

    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        TreeNode mTree = new TreeNode(t1.val + t2.val);
        mTree.left = mergeTrees(t1.left, t2.left);
        mTree.right = mergeTrees(t1.right, t2.right);
        return mTree;
    }

    /**
     * 623. 在二叉树中增加一行
     * @param root
     * @param v
     * @param d
     * @return
     */
    public TreeNode addOneRow(TreeNode root, int v, int d) {
        if (root == null) {
            return root;
        }
        //在根节点之前的情况，单独处理
        if (d == 1) {
            TreeNode addNode1 = new TreeNode(v);
            addNode1.left = root;
            return addNode1;
        }
        int curD = 1;
        List<TreeNode> preList = new ArrayList<TreeNode>();
        preList.add(root);
        List<TreeNode> nextList = new ArrayList<TreeNode>();
        for (TreeNode preL : preList) {
            if (preL != null) {
                nextList.add(preL.left);
                nextList.add(preL.right);
            }
        }
        while (curD < d-1) {
            preList = nextList;
            nextList = new ArrayList<TreeNode>();
            for (TreeNode preL : preList) {
                if (preL != null) {
                    nextList.add(preL.left);
                    nextList.add(preL.right);
                }
            }
            curD++;
        }
        int j=0;
        for (int i=0;i < preList.size(); i++) {
            if (preList.get(i) != null) {
                TreeNode addNode1 = new TreeNode(v);
                TreeNode addNode2 = new TreeNode(v);
                preList.get(i).left = addNode1;
                preList.get(i).right = addNode2;
                if (nextList != null) {
                    addNode1.left = nextList.get(2 * j);
                    addNode2.right = nextList.get(2 * j + 1);
                }
                j++;
            }
        }
        return root;
    }

    /**
     * 637. 二叉树的层平均值
     * 给定一个非空二叉树, 返回一个由每层节点平均值组成的数组.
     * @param root
     * @return
     */
    public List<Double> averageOfLevels(TreeNode root) {
        //层序遍历
        List<Double> res = new ArrayList<Double>();
        if (root == null) {
            return res;
        }
        List<TreeNode> curLevel = new ArrayList<TreeNode>();
        curLevel.add(root);
        while (curLevel.size() != 0) {
            double levelSum = 0;
            List<TreeNode> nextLevel = new ArrayList<TreeNode>();
            for (TreeNode node : curLevel) {
                levelSum += node.val;
                if (node.left != null) {
                    nextLevel.add(node.left);
                }
                if (node.right != null) {
                    nextLevel.add(node.right);
                }
            }
            res.add(levelSum/curLevel.size());
            curLevel = nextLevel;
        }
        return res;
    }

    /**
     * 652. 寻找重复的子树
     * @param root
     * @return
     */
    List<TreeNode> dplcList = new ArrayList<TreeNode>();
    Map<String, Integer> sTreeStrs = new HashMap();
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        if (root == null) {
            return dplcList;
        }
        //遍历子树，组成字符串，并放到map里，重复的子树串一样
        buildSubTreeStr(root);
        return dplcList;
    }
    private String buildSubTreeStr(TreeNode node) {
        if (node == null) {
            return "#";
        }
        String val = node.val + buildSubTreeStr(node.left) + buildSubTreeStr(node.right);
        if(sTreeStrs.get(val)==null){
            sTreeStrs.put(val, 1);
        }else if(sTreeStrs.get(val)==1){
            dplcList.add(node);
            sTreeStrs.put(val, 2);
        }
        return val;
    }

    /**
     * 653. 两数之和 IV - 输入 BST
     * @param root
     * @param k
     * @return
     */
    public boolean findTarget(TreeNode root, int k) {
        //中序遍历，递增序列，双指针找
       return false;
    }

    /**
     * 654. 最大二叉树
     * @param nums
     * @return
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return maximumBinaryTree(nums, 0, nums.length-1);
    }
    private TreeNode maximumBinaryTree(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }
        int maxVal=nums[start];
        int pos = start;
        for (int i=start;i<=end;i++) {
            if (nums[i] > maxVal) {
                maxVal = nums[i];
                pos = i;
            }
        }
        TreeNode node = new TreeNode(maxVal);
        node.left = maximumBinaryTree(nums, start, pos-1);
        node.right = maximumBinaryTree(nums, pos+1, end);
        return node;
    }

    /**
     * 655. 输出二叉树
     * @param root
     * @return
     */
    public List<List<String>> printTree(TreeNode root) {
        //层序遍历
        return null;
    }

    /**
     * 662. 二叉树最大宽度
     * @param root
     * @return
     */
    int maxWidth = 0;
    public int widthOfBinaryTree(TreeNode root) {
        //层序遍历，求每一层的宽度
        List<TreeNode> curLevel = new ArrayList<TreeNode>();
        curLevel.add(root);
        //当前层不为空
        while (!isAllNull(curLevel)) {
            int width = 0;
            //两个非空节点的距离
            if (curLevel.size() == 1) {
                width = 1;
            } else {
                //
                int i = 0;
                int j = curLevel.size()-1;
                while (i<j) {
                    if (curLevel.get(i) != null && curLevel.get(j) != null) {
                        break;
                    }
                    if (curLevel.get(i) == null) {
                        i++;
                    }
                    if (curLevel.get(j) == null) {
                        j--;
                    }
                }
                width = j-i+1;
            }
            if (width > maxWidth) {
                maxWidth = width;
            }
            List<TreeNode> nextLevel = new ArrayList<TreeNode>();
            for (TreeNode node : curLevel) {
                if (node == null) {
                    nextLevel.add(null);
                    nextLevel.add(null);
                } else {
                    nextLevel.add(node.left);
                    nextLevel.add(node.right);
                }
            }
            curLevel = nextLevel;
        }
        return maxWidth;
    }
    private boolean isAllNull(List<TreeNode> nodes) {
        boolean isNull = true;
        for (TreeNode node : nodes) {
            if (node != null) {
                isNull = false;
                break;
            }
        }
        return isNull;
    }

    /**
     * 669. 修剪二叉搜索树
     * @param root
     * @param L
     * @param R
     * @return
     */
    public TreeNode trimBST(TreeNode root, int L, int R) {
        if (root == null) {
            return root;
        }
        if (root.val < L) {
            return trimBST(root.right, L, R);
        } else if (root.val > R) {
            return trimBST(root.left, L, R);
        }
        root.left = trimBST(root.left, L, R);
        root.right = trimBST(root.right, L, R);
        return root;
    }

    /**
     * 671. 二叉树中第二小的节点
     * @param root
     * @return
     */
    public int findSecondMinimumValue(TreeNode root) {
        //遍历，排序
        return 0;
    }

    /**
     * 687. 最长同值路径
     * 给定一个二叉树，找到最长的路径，这个路径中的每个节点具有相同值。 这条路径可以经过也可以不经过根节点。
     * @param root
     * @return
     */
    int longestPath = 0;
    public int longestUnivaluePath(TreeNode root) {
        univaluePath(root);
        return longestPath;
    }
    //子树中最长的路径
    private int univaluePath(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int path = 0;
        int left = univaluePath(root.left);
        int right = univaluePath(root.right);
        int leftAll = 0;
        int rightAll = 0;
        if (root.left != null && root.val == root.left.val) {
            leftAll += left + 1;
        }
        if (root.right != null && root.val == root.right.val) {
            rightAll += right + 1;
        }
        path = leftAll + rightAll;
        if (path > longestPath) {
            longestPath = path;
        }
        return leftAll>rightAll?leftAll:rightAll;
    }

    /**
     * 700. 二叉搜索树中的搜索
     * @param root
     * @param val
     * @return
     */
    public TreeNode searchBST(TreeNode root, int val) {
        //递归，如果小于root的值往左子树，大于root的值往右子树
        if (root == null) {
            return root;
        }
        if (val == root.val) {
            return root;
        } else if (val < root.val) {
            return searchBST(root.left, val);
        } else {
            return searchBST(root.right, val);
        }
    }

    /**
     * 701. 二叉搜索树中的插入操作
     * @param root
     * @param val
     * @return
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        if (val > root.val) {
            root.right = insertIntoBST(root.right, val);
        } else {
            root.left = insertIntoBST(root.left, val);
        }
        return root;
    }

    /**
     * 783. 二叉搜索树结点最小距离
     * @param root
     * @return
     */
    public int minDiffInBST(TreeNode root) {
        //中序遍历
        return 0;
    }

    /**
     * 814. 二叉树剪枝
     * 给定二叉树根结点 root ，此外树的每个结点的值要么是 0，要么是 1。
     *
     * 返回移除了所有不包含 1 的子树的原二叉树。
     * @param root
     * @return
     */
    public TreeNode pruneTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        //注意先递归，后处理根节点
        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);
        if (root.val == 0 && root.left == null && root.right == null) {
            return null;
        }
        return root;
    }

    /**
     * 863. 二叉树中所有距离为 K 的结点
     * 返回到目标结点 target 距离为 K 的所有结点的值的列表。
     * @param root
     * @param target
     * @param K
     * @return
     */

    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        //二叉树转图
        return null;
    }

    /**
     * 865. 具有所有最深结点的最小子树
     * 返回能满足“以该结点为根的子树中包含所有最深的结点”这一条件的具有最大深度的结点。
     * @param root
     * @return
     */
    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        //如果左子树高度==右子树高度，这个节点就是结果
        if (root == null) {
            return null;
        }
        int leftDepth = maxDepthOptimize(root.left);
        int rightDepth = maxDepthOptimize(root.right);
        if (leftDepth == rightDepth) {
            return root;
        }
        if (leftDepth > rightDepth) {
            return subtreeWithAllDeepest(root.left);
        } else {
            return subtreeWithAllDeepest(root.right);
        }
    }
    //求深度的最优写法
    private int maxDepthOptimize(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepthOptimize(root.left), maxDepthOptimize(root.right)) + 1;
    }

    /**
     * 872. 叶子相似的树
     * @param root1
     * @param root2
     * @return
     */
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        //叶子节点的序列
        List<Integer> leaf1 = new ArrayList<Integer>();
        leafNode(root1, leaf1);
        List<Integer> leaf2 = new ArrayList<Integer>();
        leafNode(root2, leaf2);
        if (leaf1.size() != leaf2.size()) {
            return false;
        }
        for (int i=0;i<leaf1.size();i++) {
            if (leaf1.get(i) != leaf2.get(i)) {
                return false;
            }
        }
        return true;
    }
    private void leafNode(TreeNode root, List<Integer> leafs) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            leafs.add(root.val);
        }
        leafNode(root.left, leafs);
        leafNode(root.right, leafs);
    }

    /**
     * 938. 二叉搜索树的范围和
     * 给定二叉搜索树的根结点 root，返回 L 和 R（含）之间的所有结点的值的和。
     * @param root
     * @param L
     * @param R
     * @return
     */
    public int rangeSumBST(TreeNode root, int L, int R) {
        if (root == null) {
            return 0;
        }
        if (root.val < L) {
            return rangeSumBST(root.right, L, R);
        }
        if (root.val > R) {
            return rangeSumBST(root.left, L, R);
        }
        return root.val + rangeSumBST(root.left, L, R) + rangeSumBST(root.right, L, R);
    }

    /**
     * 951. 翻转等价二叉树
     * @param root1
     * @param root2
     * @return
     */
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return true;
        }
        if (root1 == null || root2 == null || root1.val != root2.val) {
            return false;
        }
        return (flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right)) || (flipEquiv(root1.left, root2.right) && flipEquiv(root1.right, root2.left));
    }

    /**
     * 二叉树的左视图
     * 层序遍历，放的第一个就是
     * @return
     */
    public List<Integer> LeftSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> curLevel = new LinkedList<>();
        curLevel.add(root);
        res.add(root.val);
        boolean firstNode = false;
        Queue<TreeNode> nextLevel = new LinkedList<>();
        while (!curLevel.isEmpty()) {
            while (!curLevel.isEmpty()) {
                TreeNode node = curLevel.remove();
                if (firstNode) {
                    res.add(node.val);
                    firstNode = false;
                }
                if (node.left != null) {
                    nextLevel.add(node.left);
                }
                if (node.right != null) {
                    nextLevel.add(node.right);
                }
            }
            curLevel = nextLevel;
            firstNode = true;
            nextLevel = new LinkedList<>();
        }
        return res;
    }


}
