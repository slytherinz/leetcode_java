package com.cloudlz.hot;

import java.util.*;

public class HotSolution {
    /**
     * 128. 最长连续序列
     * 给定一个未排序的整数数组，找出最长连续序列的长度。
     * <p>
     * 要求算法的时间复杂度为 O(n)。
     *
     * @param nums
     * @return
     */
    public int longestConsecutive(int[] nums) {
        //hashset
        Set<Integer> numSet = new HashSet<>();
        for (int n : nums) {
            numSet.add(n);
        }
        int longest = 0;
        for (int num : nums) {
            //它的前一个值在不在set里，才开始累积连续序列的长度
            if (!numSet.contains(num - 1)) {
                int curNum = num;
                int curLen = 1;
                while (numSet.contains(curNum + 1)) {
                    curNum++;
                    curLen++;
                }
                longest = Math.max(longest, curLen);
            }
        }
        return longest;
    }

    /**
     * 3. 无重复字符的最长子串
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        //此题有优化的方案
        if (s == null || s.length() == 0) {
            return 0;
        }
        int maxLen = 1;
        for (int i = 0; i < s.length(); i++) {
            Set<Character> charSet = new HashSet<>();
            charSet.add(s.charAt(i));
            for (int j = i + 1; j < s.length(); j++) {
                if (!charSet.contains(s.charAt(j))) {
                    charSet.add(s.charAt(j));
                    if (charSet.size() > maxLen) {
                        maxLen = charSet.size();
                    }
                } else {
                    break;
                }
            }
        }
        return maxLen;
    }

    /**
     * 14. 最长公共前缀
     * 查找字符串数组中的最长公共前缀。
     * <p>
     * 如果不存在公共前缀，返回空字符串 ""。
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        //用到indexOf方法，两个比较之后再与第3个比较
        if (strs.length == 0) {
            return "";
        }
        String prefix = strs[0];
        for (int i = 1; i <= strs.length; i++) {
            //返回指定字符在字符串中第一次出现处的索引，如果此字符串中没有这样的字符，则返回 -1。
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }
        return prefix;
    }

    /**
     * 76. 最小覆盖子串
     * 一个字符串 S、一个字符串 T，请在字符串 S 里面找出：包含 T 所有字母的最小子串。
     *
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        //滑动窗口
        //在滑动窗口类型的问题中都会有两个指针。
        // 一个用于延伸现有窗口的 rightright指针，和一个用于收缩窗口的leftleft 指针。
        if (s.length() == 0 || t.length() == 0) {
            return "";
        }
        Map<Character, Integer> dictT = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            dictT.put(t.charAt(i), dictT.getOrDefault(t.charAt(i), 0));
        }
        int size = dictT.size();
        int l = 0;
        int r = 0;
        int formed = 0;
        Map<Character, Integer> window = new HashMap<>();
        //窗口大小，左右位置
        int[] ans = {-1, 0, 0};
        while (r < s.length()) {
            char c = s.charAt(r);
            int count = window.getOrDefault(c, 0);
            window.put(c, count);
            if (dictT.containsKey(c) && window.get(c).intValue() == dictT.get(c).intValue()) {
                formed++;
            }
            //如果找到符合条件的串，收缩左指针
            while (l <= r && formed == size) {
                //保存最小的窗口
                if (ans[0] == -1 || r - l + 1 < ans[0]) {
                    ans[0] = r - l + 1;
                    ans[1] = l;
                    ans[2] = r;
                }
                //左指针移动
                window.put(c, window.get(c).intValue() - 1);
                if (dictT.containsKey(c) && window.get(c).intValue() < dictT.get(c).intValue()) {
                    formed--;
                }
                l++;
            }
            r++;
        }
        return ans[0] == -1 ? "" : s.substring(ans[1], ans[2] + 1);
    }

    /**
     * 179. 最大数
     * 给定一组非负整数，重新排列它们的顺序使之组成一个最大的整数。
     *
     * @param nums
     * @return
     */
    public String largestNumber(int[] nums) {
        //int转String
        String[] asStrs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            asStrs[i] = String.valueOf(nums[i]);
        }
        //重写排序
        Arrays.sort(asStrs, new LargerNumberComparator());
        if (asStrs[0].equals("0")) {
            return "0";
        }
        String largestNumberStr = new String();
        for (String numAsStr : asStrs) {
            largestNumberStr += numAsStr;
        }

        return largestNumberStr;
    }

    private class LargerNumberComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            String order1 = a + b;
            String order2 = b + a;
            return order2.compareTo(order1);
        }
    }

    /**
     * 217. 存在重复元素
     * 给定一个整数数组，判断是否存在重复元素。
     *
     * @param nums
     * @return
     */
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> numSet = new HashSet<>();
        for (int n : nums) {
            if (numSet.contains(n)) {
                return true;
            } else {
                numSet.add(n);
            }
        }
        return false;
    }

    /**
     * 287. 寻找重复数
     * 给定一个包含 n + 1 个整数的数组 nums，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。
     * 假设只有一个重复的整数，找出这个重复的数。
     * 不能更改原数组（假设数组是只读的）。
     * 只能使用额外的 O(1) 的空间。
     * 时间复杂度小于 O(n2) 。
     * 数组中只有一个重复的数字，但它可能不止重复出现一次。
     *
     * @param nums
     * @return
     */
    public int findDuplicate(int[] nums) {
        //快慢指针，两次循环，第一次是在环内相遇，第二次是是在环的入口
        int fast = 0, slow = 0;
        while (true) {
            fast = nums[nums[fast]];
            slow = nums[slow];
            if (slow == fast) {
                fast = 0;
                while (nums[slow] != nums[fast]) {
                    fast = nums[fast];
                    slow = nums[slow];
                }
                return nums[slow];
            }
        }
    }

    /**
     * 315. 计算右侧小于当前元素的个数
     *
     * @param nums
     * @return
     */
    public List<Integer> countSmaller(int[] nums) {
        return null;
    }

    /**
     * 395. 至少有K个重复字符的最长子串
     * 找到给定字符串（由小写字符组成）中的最长子串 T ， 要求 T 中的每一字符出现次数都不少于 k 。输出 T 的长度
     *
     * @param s
     * @param k
     * @return
     */
    public int longestSubstring(String s, int k) {
        //以<k的字符分割，再递归左右的子串
        if (s == null || s.length() < k) {
            return 0;
        }
        return countLongSubStr(s, 0, s.length() - 1, k);
    }

    private int countLongSubStr(String s, int start, int end, int k) {
        if (end - start + 1 < k) {
            return 0;
        }
        int[] charTimes = new int[26];//26个字母
        for (int i = start; i <= end; i++) {
            charTimes[s.charAt(i) - 'a']++;
        }
        while (end - start + 1 >= k && charTimes[s.charAt(start) - 'a'] < k) {
            start++;
        }
        while (end - start + 1 >= k && charTimes[s.charAt(end) - 'a'] < k) {
            end--;
        }
        if (end - start + 1 < k) return 0;
        //  得到临时子串，再递归处理
        for (int i = start; i <= end; ++i) {
            //  如果第i个不符合要求，切分成左右两段分别递归求得
            if (charTimes[s.charAt(i) - 'a'] < k) {
                return Math.max(countLongSubStr(s, start, i - 1, k), countLongSubStr(s, i + 1, end, k));
            }
        }
        return end - start + 1;
    }
}
