package com.cloudlz.array;

import java.util.*;

public class ArraySolution {

    List<String> res = new ArrayList<>();
    int[] section = new int[4];
    public List<String> restoreIpAddresses(String s) {
        backtrack(s, 0, 0);
        return res;
    }

    public void backtrack(String s, int id, int start) {
        // 如果已经4段了
        if (id == 4) {
            // 字符串遍历完
            if (start == s.length()) {
                StringBuffer ipAddr = new StringBuffer();
                for (int i=0; i < 4; i++){
                    ipAddr.append(section[i]);
                    if (i != 3) {
                        ipAddr.append(".");
                    }
                }
                res.add(ipAddr.toString());

            }
            return;
        }

        if (start == s.length()) {
            return;
        }

        // 处理0的情况
        if (s.charAt(start) == '0') {
            section[id] = 0;
            backtrack(s, id+1, start+1);
        }

        int addr = 0;
        for (int end = start; end < s.length(); end++) {
            addr = addr * 10 + s.charAt(end) - '0';
            if (addr > 0 && addr <= 255) {
                section[id] = addr;
                backtrack(s, id+1, end+1);
            } else {
                return;
            }
        }
    }

    public int[] findErrorNums(int[] nums) {
        int[] res = new int[2];
        Set<Integer> tmp = new HashSet<>();
        for (int i=0;i<nums.length;i++) {
            if (tmp.contains(nums[i])) {
                res[0] = nums[i];
            } else {
                tmp.add(nums[i]);
            }
        }
        for (int i=1;i<=nums.length;i++) {
            if (!tmp.contains(i)) {
                res[1] = i;
                break;
            }
        }
        return res;
    }

    /**
     * 1423. 可获得的最大点数
     * dfs遍历超时，取左取右得最大值，反过来就是求中间连续子数组得和最小，滑动窗口计算
     * @param cardPoints
     * @param k
     * @return
     */
    int maxScore = 0;
    public int maxScore(int[] cardPoints, int k) {
        dfsMaxScore(cardPoints, k, 0, cardPoints.length-1, 0, 0);
        return maxScore;
    }

    public void dfsMaxScore(int[] cards, int k, int left, int right, int count, int sumScore) {
        // 结束
        if (count == k) {
            if (sumScore>maxScore) {
                maxScore = sumScore;
            }
            return;
        }
        sumScore += cards[left];
        dfsMaxScore(cards, k, left+1, right, count+1, sumScore);
        sumScore = sumScore - cards[left] + cards[right];
        dfsMaxScore(cards, k, left, right-1, count+1, sumScore);
    }

    // 滑动窗口解法
    public int maxScoreOptimize(int[] cardPoints, int k) {
       int left = 0;
       int right = cardPoints.length - k -1;
       int slideSum = 0; //滑动窗口里的总和
       int totalSum = 0; //整个数组的总和
       for (int i=0;i<cardPoints.length;i++) {
           if (i<=right) {
               slideSum += cardPoints[i];
           }
           totalSum += cardPoints[i];
       }
       int minSlideSum = slideSum;
       while (right<cardPoints.length-1) {
           slideSum = slideSum - cardPoints[left++] + cardPoints[++right];
           if (slideSum < minSlideSum) {
               minSlideSum = slideSum;
           }
       }
       return totalSum-minSlideSum;
    }

    /**
     * 检查一个字符串是否包含所有长度为 K 的二进制子串
     * 通过滑动窗口+HashSet 找出所有长度为k的去重后的组合,与2^k比较即可
     * @param s
     * @param k
     * @return
     */
    public boolean hasAllCodes(String s, int k) {
        HashSet<String> allBinaryStr = new HashSet<>();
        for (int i=0;i<=s.length()-k;i++) {
            allBinaryStr.add(s.substring(i, i+k));
        }
        return allBinaryStr.size() == Math.pow(2, k);
    }


    List<String> resAllBinaryStr = new ArrayList<>();
    public List<String> getAllBinaryStr(int k) {
        StringBuilder sb = new StringBuilder();
        dfsAllBinaryStr(sb, 0, k);
        return resAllBinaryStr;
    }
    public void dfsAllBinaryStr(StringBuilder inputStr, int i, int k) {
        if (inputStr.length() == k) {
            resAllBinaryStr.add(inputStr.toString());
            return;
        }
        for (int n=0;n<=1;n++) {
            inputStr.append(n);
            dfsAllBinaryStr(inputStr, i+1, k);
            inputStr.deleteCharAt(i);
        }

    }

    public List<List<Integer>> groupThePeople(int[] groupSizes) {

        ArrayList[] tmp = new ArrayList[groupSizes.length+1];
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        for (int i=0;i<=groupSizes.length;i++) {
            tmp[i] = new ArrayList<>();
        }
        for (int i=0;i<groupSizes.length;i++) {
            if (tmp[groupSizes[i]].size() != groupSizes[i]) {
                tmp[groupSizes[i]].add(i);
            } else {
                res.add(tmp[groupSizes[i]]);
                tmp[groupSizes[i]] = new ArrayList();
                tmp[groupSizes[i]].add(i);
            }
        }
        for (int i=1;i<=groupSizes.length;i++) {
            if (tmp[i].size() == i) {
                res.add(tmp[i]);
            }
        }
        return res;
    }

    /**
     * 单词拆分 II
     * 暴力回溯超时，在回溯时记录下中间计算过程
     * 存储字符串 ss 的每个下标和从该下标开始的部分可以组成的句子列表，在回溯过程中如果遇到已经访问过的下标，则可以直接从哈希表得到结果，
     * 而不需要重复计算。
     * @param s
     * @param wordDict
     * @return
     */
    List<String> wordsRes = new ArrayList<>();
    public List<String> wordBreak(String s, List<String> wordDict) {
        //用
        StringBuilder findWordStr = new StringBuilder();
        List<String> word = new ArrayList<>();
        dfsFindWord(s, word, 0, wordDict);
        return wordsRes;
    }

    public void dfsFindWord(String s, List<String> word, int startpos, List<String> wordDict) {
        // 结束
        if (startpos == s.length()) {
            String compare = "";
            for (String eword:word) {
                compare += eword;
            }
            if (compare.equals(s)) {
                StringBuilder sb = new StringBuilder();
                for (int i=0;i<word.size();i++) {
                    sb.append(word.get(i));
                    if (i != word.size()-1) {
                        sb.append(" ");
                    }
                }
                wordsRes.add(sb.toString());
            }
            return;
        }
        for (int i=startpos;i<s.length();i++) {
            String tryWord = s.substring(startpos, i+1);
            if (wordDict.contains(tryWord)) {
                word.add(tryWord);
                int index = word.lastIndexOf(tryWord);
                dfsFindWord(s, word, i+1, wordDict);
                word.remove(index);
            }
        }
    }

    //一个map记录
    Map<Integer, List<List<String>>> mem = new HashMap<>();
    public List<String> wordBreak2(String s, List<String> wordDict) {
        List<String> wb = new ArrayList<>();
        List<List<String>> wordsRes2 = dfsFindWords2(s,0, wordDict);
        for (List<String> i : wordsRes2) {
            wb.add(String.join(" ", i));
        }
        return wb;
    }

    public List<List<String>> dfsFindWords2(String s, int startPos, List<String> wordDict) {
        if (!mem.containsKey(startPos)) {
            List<List<String>> tmpRes = new LinkedList<>();
            if (startPos == s.length()) {
                tmpRes.add(new LinkedList<>());
            }
            for (int i=startPos+1;i<=s.length();i++) {
                String curWord = s.substring(startPos, i);
                if (wordDict.contains(curWord)) {
                    List<List<String>> nextWordList = dfsFindWords2(s, i, wordDict);
                    for (List<String> k:nextWordList) {
                        LinkedList<String> tmpK = new LinkedList<>(k);
                        tmpK.offerFirst(curWord);
                        tmpRes.add(tmpK);
                    }
                }
            }
            mem.put(startPos, tmpRes);
        }
        return mem.get(startPos);
    }

}
