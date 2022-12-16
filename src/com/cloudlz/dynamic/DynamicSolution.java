package com.cloudlz.dynamic;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态规划
 */
public class DynamicSolution {
    //题型分类：1.子序列问题；2.回文；3路径；4.背包

    /**
     * 5. 最长回文子串
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        //令dp[i][j]表示s[i...j] = 1是回文字符串
        int n = s.length();
        String res = "";
        boolean[][] dp = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                dp[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 2 || dp[i + 1][j - 1]); //j - i 代表长度减去 1
                if (dp[i][j] && j - i + 1 > res.length()) {
                    res = s.substring(i, j + 1);
                }
            }
        }
        return res;
    }

    /**
     * 516. 最长回文子序列
     *
     * @param s
     * @return
     */
    public int longestPalindromeSubseq(String s) {
        //子序列可以不连续的，可以跳过某些单词
        //dp[i][j]表示s的第 i 个字符到第 j 个字符组成的子串中，最长的回文序列长度
        //i 从最后一个字符开始往前遍历，j 从 i + 1 开始往后遍历，这样可以保证每个子问题都已经算好了
        int len = s.length();
        int[][] dp = new int[len][len];
        for (int i = len - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < len; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
                }
            }
        }
        return dp[0][len - 1];
    }

    /**
     * 32. 最长有效括号
     *
     * @param s
     * @return
     */
    public int longestValidParentheses(String s) {
        //dp 数组，其中第i个元素表示以下标为i的字符结尾的最长有效子字符串的长度。
        //只判断右括号
        //两种情况，s[i]=')'，s[i-1]='('
        //s[i]=')'，s[i-1]=')',前面肯定有'('
        int maxans = 0;
        int dp[] = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
                maxans = Math.max(maxans, dp[i]);
            }
        }
        return maxans;
    }

    /**
     * 647. 回文子串
     * 计算字符串中有多少个回文子串。
     * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被计为是不同的子串。
     *
     * @param s
     * @return
     */
    public int countSubstrings(String s) {
        //dp[i][j]表示s[i...j]是否是回文
        //dp[i][j]是回文的情况dp[i+i][j-1]为回文且s[i]==s[j]
        //再把所有的加上
        if (s == null || s.length() == 0) {
            return 0;
        }
        int count = 0;
        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int j = 0; j < s.length(); j++) {
            for (int i = 0; i <= j; i++) {
                if (i == j) {
                    dp[i][j] = true;
                } else {
                    dp[i][j] = (s.charAt(i) == s.charAt(j)) && (j - i <= 1 || dp[i + 1][j - 1]);
                }
            }
        }
        for (int j = 0; j < dp.length; j++) {
            for (int i = 0; i <= j; i++) {
                if (dp[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 53. 最大子序和
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int ans = nums[0];
        int sum = 0;
        for (int num : nums) {
            //如果前面是正值，就加上当前的值
            if (sum > 0) {
                sum += num;
            } else {
                sum = num;
            }
            ans = Math.max(ans, sum);
        }
        return ans;
    }

    /**
     * 152. 乘积最大子序列
     * 给定一个整数数组 nums ，找出一个序列中乘积最大的连续子序列
     *
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int res = Integer.MIN_VALUE;
        //记录最小值
        int imin = 1;
        int imax = 1;
        for (int n : nums) {
            //如果是负数，最大值和最小值先交换，再乘这个负数
            if (n < 0) {
                int tmp = imax;
                imax = imin;
                imin = tmp;
            }
            imax = Math.max(imax * n, n);
            imin = Math.min(imin * n, n);
            res = Math.max(res, imax);
        }
        return res;
    }

    /**
     * 300. 最长上升子序列
     * 给定一个无序的整数数组，找到其中最长上升子序列的长度。
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        //动态规划加二分查找，时间复杂度NlogN，空间复杂度N
        if (nums.length == 0) {
            return 0;
        }
        int len = 0;
        //dp用于存储当前遇到的元素形成的上升子序列
        int[] dp = new int[nums.length];
        for (int num : nums) {
            //返回索引值，从1开始计数，不在数组中为（-插入点）
            int i = Arrays.binarySearch(dp, 0, len, num);
            if (i < 0) {
                i = -i - 1;
            }
            dp[i] = num;
            if (i == len) {
                len++;
            }
        }
        return len;
    }

    /**
     * 673. 最长递增子序列的个数
     * 给定一个未排序的整数数组，找到最长递增子序列的个数。
     *
     * @param nums
     * @return
     */
    public int findNumberOfLIS(int[] nums) {
        //dp用于存储包含当前遇到的元素的最长递增子序列的长度
        //lc记录当前元素最长子序列长度的个数
        //外侧循环遍历[0,...,len]的元素
        //内层循环，遍历0到当前元素的前一个元素：分3种情况
        // 1.如果前面元素比当前元素小，而且长度更长，就替换
        //2.如果前面元素比当前元素小，而且长度一样，直接累加个数
        //3.如果前面的元素比当前元素大或者长度+1比当前元素的长度小，直接跳过
        if (nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        int[] lc = new int[nums.length];
        int maxLen = 0;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            //初始化
            dp[i] = 1;
            lc[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (nums[i] <= nums[j] || dp[j] + 1 < dp[i]) {
                    continue;
                }
                if (dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    lc[i] = lc[j];
                } else {
                    lc[i] += lc[j];
                }
            }
            if (dp[i] == maxLen) {
                count += lc[i];
            } else if (dp[i] > maxLen) {
                maxLen = dp[i];
                count = lc[i];
            }
        }
        return count;

    }

    /**
     * 410. 分割数组的最大值
     * 给定一个非负整数数组和一个整数 m，你需要将这个数组分成 m 个非空的连续子数组。
     * 设计一个算法使得这 m 个子数组各自和的最大值最小。
     *
     * @param nums
     * @param m
     * @return
     */
    public int splitArray(int[] nums, int m) {
        //首先我们把 dp[i][j] 定义为将 nums[0..i] 分成 j 份时能得到的最小的分割子数组最大值。
        //对于第 j 个子数组，它为数组中下标 k + 1 到 i 的这一段。因此，dp[i][j] 可以从 max(dp[k][j - 1], nums[k + 1] + ... + nums[i]) 这个公式中得到。
        int len = nums.length;
        int[][] dp = new int[len + 1][m + 1];
        int[] subSum = new int[len + 1];
        //初始化
        for (int i = 0; i <= len; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int i = 0; i < len; i++) {
            subSum[i + 1] = subSum[i] + nums[i];
        }
        dp[0][0] = 0;
        for (int i = 1; i <= len; i++) {
            for (int j = 1; j <= m; j++) {
                for (int k = 0; k < i; k++) {
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[k][j - 1], subSum[i] - subSum[k]));
                }
            }
        }
        return dp[len][m];
    }


    /**
     * 523. 连续的子数组和
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean checkSubarraySum(int[] nums, int k) {
        //暴力解法，两层for循环计算当前元素和当前元素后面元素依次形成的子序列的和
        if (nums.length < 2) {
            return false;
        }
        for (int i = 0; i < nums.length - 1; i++) {
            int sum = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                sum += nums[j];
                if ((k == 0 && sum == 0) || (k != 0 && sum % k == 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkSubarraySum2(int[] nums, int k) {
        //用HashMap保存sum对k取余数，如果前序有余数也为sum%k的位置，那么就存在连续子数组和为k的倍数。
        if (nums.length < 2) {
            return false;
        }
        //key是余数，
        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        map.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (k != 0) {
                sum %= k;
            }
            Integer preIndex = map.get(sum);
            if (preIndex != null) {
                if (i - preIndex > 1) {
                    return true;
                }
            } else {
                map.put(sum, i);
            }
        }
        return false;
    }

    /**
     * 718. 最长重复子数组
     * 给两个整数数组 A 和 B ，返回两个数组中公共的、长度最长的子数组的长度。
     *
     * @param A
     * @param B
     * @return
     */
    public int findLength(int[] A, int[] B) {
        //典型的最长公共子串问题
        //用dp(i,j)表示以A[i]和B[j]为结尾的相同子串的最大长度
        int m = A.length, n = B.length;
        if (m == 0 || n == 0) return 0;
        // 0的位置初始化为0，省的判断越界问题了
        int[][] dp = new int[m + 1][n + 1];
        int longest = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (A[i] != B[j]) {
                    dp[i + 1][j + 1] = 0;
                } else {
                    dp[i + 1][j + 1] = 1 + dp[i][j];
                    longest = Math.max(longest, dp[i + 1][j + 1]);
                }
            }
        }
        return longest;
    }

    /**
     * 62. 不同路径
     *
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * 64. 最小路径和
     *
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for (int i = 1; i < n; i++) {
            dp[0][i] = dp[0][i - 1] + grid[0][i];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = (dp[i - 1][j] + grid[i][j]) < (dp[i][j - 1] + grid[i][j]) ? dp[i - 1][j] + grid[i][j] : dp[i][j - 1] + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * 416. 分割等和子集
     * 给定一个只包含正整数的非空数组。是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
     *
     * @param nums
     * @return
     */
    public boolean canPartition(int[] nums) {
        //0-1 背包问题也是最基础的背包问题，它的特点是：待挑选的物品有且仅有一个，可以选择也可以不选择。
        //从这个数组中挑选出一些正整数，每个数只能用一次，使得这些数的和等于整个数组元素的和的一半。
        //dp[i][j] ：表示从数组的 [0, i] 这个子区间内挑选一些正整数，每个数只能用一次，使得这些数的和等于 j。
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        if (sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        int len = nums.length;
        boolean[][] dp = new boolean[len][target + 1];
        //初始化
        for (int i = 1; i <= target; i++) {
            if (nums[0] == i) {
                dp[0][i] = true;
            }
        }
        for (int i = 1; i < len; i++) {
            for (int j = 0; j <= target; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= nums[i]) {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
                }
            }
        }
        return dp[len - 1][target];
    }

    /**
     * 321. 拼接最大数
     * 给定长度分别为 m 和 n 的两个数组，其元素由 0-9 构成，表示两个自然数各位上的数字。
     * 现在从这两个数组中选出 k (k <= m + n) 个数字拼接成一个新的数，要求从同一个数组中取出的数字保持其在原数组中的相对顺序。
     * 求满足该条件的最大数。结果返回一个表示该最大数的长度为 k 的数组。
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        //贪心算法
        int[] res = new int[k];
        return res;
    }

    /**
     * @param books
     * @param shelf_width
     * @return
     */
    public int minHeightShelves(int[][] books, int shelf_width) {
        int n = books.length;
        //dp[i]表示放完前i本书的最小高度
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            int hight = 0;
            int width = 0;
            for (int j = i; j > 0; j--) {
                width += books[j - 1][0];
                if (width > shelf_width) {
                    break;
                }
                hight = Math.max(hight, books[j - 1][1]);
                dp[i] = Math.min(dp[i], dp[j - 1] + hight);
            }
        }
        return dp[n];
    }

    /**
     * 1626. 无矛盾的最佳球队
     *
     * @param scores
     * @param ages
     * @return
     */
    public int bestTeamScore(int[] scores, int[] ages) {
        // 先升序排序，问题转化为最大上升子序列和
        int len = scores.length;
        int[][] sortAsc = new int[len][2];
        for (int i = 0; i < len; i++) {
            sortAsc[i][0] = ages[i];
            sortAsc[i][1] = scores[i];
        }
        Arrays.sort(sortAsc, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                } else {
                    return o1[0] - o2[0];
                }
            }
        });
        // dp[i]表示以第i个元素结尾的最大上升子序列和
        int[] dp = new int[len];
        int res = sortAsc[0][1];
        for (int i = 0; i < len; i++) {
            dp[i] = sortAsc[i][1];
        }
        for (int i = 1; i < len; i++) {
            for (int j = 0; j < i; j++) {
                if (sortAsc[j][1] <= sortAsc[i][1]) {
                    dp[i] = Math.max(dp[j] + sortAsc[i][1], dp[i]);
                }
            }
            res = Math.max(dp[i], res);
        }
        return res;
    }
}
