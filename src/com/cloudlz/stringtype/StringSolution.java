package com.cloudlz.stringtype;

import java.util.*;
import java.util.stream.Collectors;

public class StringSolution {
    /**
     * 滑动窗口
     *
     * @param s
     * @return
     */
    public int countHomogenous(String s) {
        int counts = 0;
        int left = 0;
        int right = 0;
//        Map<Character, Integer> window = new HashMap<>();
        while (right < s.length()) {
//            if(window.containsKey(s.charAt(right))) {
//                window.put(s.charAt(right), window.get(s.charAt(right))+1);
//            } else {
//                window.put(s.charAt(right), 1);
//            }
            if (s.charAt(right) != s.charAt(left)) {
                left = right;
            }
            counts = (counts + right - left + 1) % 1000000007;
            right++;
        }
//        for(Map.Entry<Character, Integer> ele : window.entrySet()) {
//            ele.getKey();
//            ele.getValue();
//        }
        return counts;
    }

    /**
     * 回文串分割
     *
     * @param s
     * @return
     */
    public boolean checkPartitioning(String s) {
//动态规划
        int len = s.length();
        // 初始化默认false
        boolean[][] dp = new boolean[len][len];
        // dp[i][j]要用到dp[i+1][j-1]，所以i从大到小
        for (int i = len - 1; i >= 0; i--) {
            for (int j = i; j < len; j++) {
                if (s.charAt(i) == s.charAt(j) && (j - i < 2 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                }
            }
        }
        //分割字符串成3段
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len - 1; j++) {
                if (!dp[0][i]) {
                    break;
                }
                if (dp[0][i] && dp[i + 1][j] && dp[j + 1][len - 1]) {
                    return true;
                }
            }
        }
        return false;

    }


    public boolean exist(char[][] board, String word) {
        Set<String> wordSet = new HashSet<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                StringBuilder sb = new StringBuilder();
                if (board[i][j] == word.charAt(0)) {
                    sb.append(board[i][j]);
                    String s = "i:" + i + "j:" + j;
                    wordSet.add(s);
                    if (goBT(sb, i, j, word, board, wordSet)) {
                        return true;
                    }
                    wordSet.remove(s);
                }
            }
        }
        return false;
    }

    /**
     * 回溯
     *
     * @param input
     * @param word
     * @param tmp
     * @return
     */
    public boolean goBT(StringBuilder input, int i, int j, String word, char[][] board, Set<String> tmp) {
        if (input.length() == word.length()) {
            if (input.toString().charAt(input.length() - 1) == word.charAt(input.length() - 1)) {
                return true;
            }
            return false;
        }
        int len = input.length();
        Set<String> newSet = new HashSet<>();
        Iterator<String> it = tmp.iterator();
        while (it.hasNext()) {
            newSet.add(it.next());
        }
        //往左找到
        if (j > 0 && board[i][j - 1] == word.charAt(input.length()) && newSet.add("i:" + i + "j:" + (j - 1))) {
            input.append(board[i][j - 1]);
            if (goBT(input, i, j - 1, word, board, newSet)) {
                return true;
            }
            input.delete(len, input.length());
            newSet.remove("i:" + i + "j:" + (j - 1));
        }
        //往上找到
        if (i > 0 && board[i - 1][j] == word.charAt(input.length()) && newSet.add("i:" + (i - 1) + "j:" + j)) {
            input.append(board[i - 1][j]);
            if (goBT(input, i - 1, j, word, board, newSet)) {
                return true;
            }
            input.delete(len, input.length());
            newSet.remove("i:" + (i - 1) + "j:" + j);
        }
        //往右找到
        if (j < board[0].length - 1 && board[i][j + 1] == word.charAt(input.length()) && newSet.add("i:" + i + "j:" + (j + 1))) {
            input.append(board[i][j + 1]);
            if (goBT(input, i, j + 1, word, board, newSet)) {
                return true;
            }
            input.delete(len, input.length());
            newSet.remove("i:" + i + "j:" + (j + 1));
        }
        //往下找到
        if (i < board.length - 1 && board[i + 1][j] == word.charAt(input.length()) && newSet.add("i:" + (i + 1) + "j:" + j)) {
            input.append(board[i + 1][j]);
            if (goBT(input, i + 1, j, word, board, newSet)) {
                return true;
            }
            input.delete(len, input.length());
            newSet.remove("i:" + (i + 1) + "j:" + j);
        }
        return false;
    }

    public int findLUSlength(String[] strs) {
        //按长度降序排
        Arrays.sort(strs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length() - o1.length();
            }
        });
        for (int i = 0, j; i < strs.length; i++) {
            boolean isSpecial = true;
            for (j = 0; j < strs.length; j++) {
                if (j == i) {
                    continue;
                }
                if (isSubsequence(strs[i], strs[j])) {
                    isSpecial = false;
                    break;
                }
            }
            if (isSpecial) {
                return strs[i].length();
            }
        }
        return -1;

    }

    /**
     * 生成一个字符串的所有子序列
     *
     * @param s
     */
    public void generateSubsequence(String s) {
        HashSet<String> a = new HashSet<>();
        for (int i = 0; i < (1 << s.length()); i++) {
            String t = "";
            for (int j = 0; j < s.length(); j++) {
                if (((i >> j) & 1) != 0)
                    t += s.charAt(j);
            }
            a.add(t);
        }
        int c = 0;
        System.out.println(c);
    }

    /**
     * s是不是t的子序列
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isSubsequence(String s, String t) {
        int j = 0;
        for (int i = 0; i < t.length() && j < s.length(); i++) {
            if (s.charAt(j) == t.charAt(i)) {
                j++;
            }
        }
        return j == s.length();

    }

    /**
     * @param s
     * @param k
     * @return
     */
    public String reverseStr(String s, int k) {
        char[] charS = s.toCharArray();
        int posStart = 0;
        while (posStart < charS.length) {
            int posEnd = posStart + 2 * k - 1;
            if (posEnd > charS.length - 1) {
                posEnd = charS.length - 1;
            }
            if (posEnd - posStart < k) {
                reverseHelp(charS, posStart, posEnd);
            } else {
                reverseHelp(charS, posStart, posStart + k - 1);
            }
            posStart = posEnd + 1;
        }
        return String.valueOf(charS);
    }

    public void reverseHelp(char[] str, int start, int end) {
        int mid = (end - start) / 2;
        for (int i = 0; i <= mid; i++) {
            char tmp = str[end - i];
            str[end - i] = str[start + i];
            str[start + i] = tmp;
        }
    }

    /**
     * 下一个更大的数
     *
     * @param n
     * @return
     */
    public int nextGreaterElement(int n) {
        //从右往左找第一个a[i-1]<a[i]
        char[] nC = String.valueOf(n).toCharArray();
        int i = nC.length - 2;
        while (i >= 0 && nC[i] >= nC[i + 1]) {
            i--;
        }
        if (i == -1) {
            return -1;
        }
        //
        int j = nC.length - 1;
        while (j >= 0 && nC[j] <= nC[i]) {
            j--;
        }
        //交换i和j
        char tmp = nC[i];
        nC[i] = nC[j];
        nC[j] = tmp;
        //将位置i+1到len反转
        int m = i + 1;
        int k = nC.length - 1;
        while (m < k) {
            char tmp2 = nC[m];
            nC[m] = nC[k];
            nC[k] = tmp2;
            m++;
            k--;
        }
        String tc = String.valueOf(nC);
        String tc2 = nC.toString();
        return Integer.valueOf(String.valueOf(nC));
    }

    /**
     * 两个字符串的删除操作
     *
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2) {
        //动态规划求最长公共子序列
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 0; i <= word1.length(); i++) {
            for (int j = 0; j <= word2.length(); j++) {
                if (i == 0 || j == 0) {
                    continue;
                }
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return word1.length() + word2.length() - 2 * dp[word1.length()][word2.length()];
    }

    public boolean checkValidString(String s) {
        // 记录（ 的位置
        Stack<Integer> leftS = new Stack<>();
        // 记录 * 的位置
        Stack<Integer> startS = new Stack<>();
        int i = 0;
        while (i < s.length()) {
            if (s.charAt(i) == '(') {
                leftS.add(i);
            } else if (s.charAt(i) == '*') {
                startS.add(i);
            } else {
                //出栈
                if (leftS.isEmpty()) {
                    if (startS.isEmpty()) {
                        return false;
                    }
                    startS.pop();
                } else {
                    leftS.pop();
                }
            }
            i++;
        }
        if (!leftS.isEmpty() && !startS.isEmpty()) {
            if (leftS.size() > startS.size()) {
                return false;
            }
            while (!leftS.isEmpty() && !startS.isEmpty()) {
                if (leftS.peek() < startS.peek()) {
                    leftS.pop();
                    startS.pop();
                } else {
                    return false;
                }
            }
        }
        if (!leftS.isEmpty() && startS.isEmpty()) {
            return false;
        } else if (leftS.isEmpty() && !startS.isEmpty()) {
            return true;
        }
        return true;
    }

    public boolean validPalindrome(String s) {
        boolean isDel = false;
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            // 如果删除过
            if (isDel) {
                if (s.charAt(left) != s.charAt(right)) {
                    return false;
                }
                left++;
                right--;
            } else {
                if (s.charAt(left) != s.charAt(right)) {
                    if (s.charAt(left + 1) == s.charAt(right) && s.charAt(left) == s.charAt(right - 1)) {
                        if (left + 2 < s.length() && s.charAt(left + 2) == s.charAt(right - 1)) {
                            isDel = true;
                            left++;
                        } else if (right - 2 >= 0 && s.charAt(left + 1) == s.charAt(right - 2)) {
                            isDel = true;
                            right--;
                        } else {
                            isDel = true;
                            left++;
                        }
                    } else if (s.charAt(left + 1) == s.charAt(right)) {
                        isDel = true;
                        left++;
                    } else if (s.charAt(left) == s.charAt(right - 1)) {
                        isDel = true;
                        right--;
                    } else {
                        return false;
                    }
                } else {
                    left++;
                    right--;
                }
            }
        }
        return true;
    }


    /**
     * @param a
     * @param b
     * @return
     */
    public int repeatedStringMatch(String a, String b) {
        // 如果a>b,只有两种情况，b是a的子串，b是2个a子串，不然a再循环也没用
        if (b.length() < a.length()) {
            if (a.contains(b)) {
                return 1;
            } else {
                a = a + a;
                if (a.contains(b)) {
                    return 2;
                } else {
                    return -1;
                }
            }
        }
        // 如果b>=a,最多b/a+2,将b按a的长度切割
        int n = b.length() / a.length();
        if (b.length() % a.length() != 0) {
            n += 1;
        }
        for (int i = 0; i < n - 1; i++) {
            a += a;
        }
        if (a.contains(b)) {
            return n;
        }
        a += a;
        if (a.contains(b)) {
            return n + 1;
        }
        a += a;
        if (a.contains(b)) {
            return n + 2;
        }
        return -1;
    }

    /**
     * 重构字符串使相邻字符不同
     *
     * @param S
     * @return
     */
    public String reorganizeString(String S) {
        //按字符重复的次数排序
        Map<Character, Integer> smap = new HashMap<>();
        for (int i = 0; i < S.length(); i++) {
            Character word = S.charAt(i);
            if (smap.containsKey(word)) {
                smap.put(word, smap.get(word) + 1);
            } else {
                smap.put(word, 1);
            }
        }
        List<Map.Entry<Character, Integer>> smsort = new ArrayList<>();
        smsort.addAll(smap.entrySet());
        Collections.sort(smsort, new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                return o1.getValue() - o2.getValue();
            }
        });
        StringBuilder sb = new StringBuilder();
        int lastCount = smsort.get(smsort.size() - 1).getValue();
        if (lastCount > S.length() - lastCount + 1) {
            return "";
        }


        for (int i = 0; i < smsort.size(); i++) {
            Character word = smsort.get(i).getKey();
            int count = smsort.get(i).getValue();
            if (count > sb.length() + 1) {
                int m = sb.length();
                int pos = 0;
                int headCount = count - sb.length();
                for (int k = 0; k < m; k++) {
                    sb.insert(pos, word);
                    pos += 2;
                }
                for (int j = 0; j < headCount; j++) {
                    sb.insert(0, word);
                }
            } else {
                int pos = 0;
                for (int k = 0; k < count; k++) {
                    sb.insert(pos, word);
                    pos += 2;
                }
            }
        }
        return sb.toString();

    }

    public int expressiveWords(String S, String[] words) {
        int can = 0;
        if (S.length() == 0) {
            return can;
        }

        for (String word : words) {
            //指向word的指针
            int b = 0;
            //指向S
            int p = 0;
            while (b < word.length() && p < S.length()) {
                Character w = word.charAt(b);
                if (w == S.charAt(p)) {
                    //计算w串的长度
                    int sc = 0;
                    int cp = p;
                    while (cp < S.length()) {
                        if (S.charAt(cp) == w) {
                            sc++;
                            cp++;
                        } else {
                            break;
                        }
                    }
                    int wc = 0;
                    int wp = b;
                    while (wp < word.length()) {
                        if (word.charAt(wp) == w) {
                            wc++;
                            wp++;
                        } else {
                            break;
                        }
                    }
                    if (sc - wc == 0 || (sc >= 3 && sc - wc > 0)) {
                        b += wc;
                        p += sc;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            if (p == S.length() && b == word.length()) {
                can += 1;
            }
        }
        return can;
    }

    public String mostCommonWord(String paragraph, String[] banned) {
        String p = paragraph.toLowerCase();
        // 切割出单词
        List<String> pList = new ArrayList<>();
        int start = 0;
        int end = 0;
        while (start < p.length()) {
            if (p.charAt(start) < 'a' || p.charAt(start) > 'z') {
                start++;
                end = start;
            } else {
                if (end == p.length()) {
                    String np = p.substring(start, end);
                    start = end;
                    pList.add(np);
                } else {
                    if (p.charAt(end) < 'a' || p.charAt(end) > 'z') {
                        String np = p.substring(start, end);
                        start = end;
                        pList.add(np);
                    } else {
                        end++;
                    }
                }
            }
        }
        //单词出现的次数
        Map<String, Integer> wm = new HashMap<>();
        //去掉标点符号
        for (int i = 0; i < pList.size(); i++) {
            String ep = pList.get(i);
            if (ep.charAt(ep.length() - 1) < 'a' || ep.charAt(ep.length() - 1) > 'z') {
                ep = ep.substring(0, ep.length() - 1);
            }
            if (wm.containsKey(ep)) {
                wm.put(ep, wm.get(ep) + 1);
            } else {
                wm.put(ep, 1);
            }
        }
        List<Map.Entry<String, Integer>> ws = new ArrayList<>();
        ws.addAll(wm.entrySet());
        Collections.sort(ws, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        for (int i = 0; i < ws.size(); i++) {
            String e = ws.get(i).getKey();
            boolean isFund = false;
            for (int j = 0; j < banned.length; j++) {
                if (e.equals(banned[j])) {
                    isFund = true;
                    break;
                }
            }
            if (isFund) {
                continue;
            }
            return e;
        }
        return "";
    }


    /**
     * case:   int[] input2 = {3,5,1};
     * String[] input3 = {"kg","ggq","mo"};
     * String[] input4 = {"s","so","bfr"};
     * indexes是无序的，需要做排序处理
     *
     * @param S
     * @param indexes
     * @param sources
     * @param targets
     * @return
     */
    public String findReplaceString(String S, int[] indexes, String[] sources, String[] targets) {
        StringBuilder sb = new StringBuilder();
        List<String> tmp = new ArrayList<>();
        for (int i = 0; i < indexes.length; i++) {
            int indexS = indexes[i];
            String sw = sources[i];
            int len = sw.length();
            String cw = S.substring(indexS, indexS + len);
            if (cw.equals(sw)) {
                sb.append(targets[i]);
            } else {
                sb.append(cw);
            }
            //被替换的部分到下一个索引的字符串
            if (i != indexes.length - 1) {
                if (indexS + len < indexes[i + 1]) {
                    sb.append(S.substring(indexS + len, indexes[i + 1]));
                }
            } else {
                if (indexS + len < S.length()) {
                    sb.append(S.substring(indexS + len));
                }
            }
        }
        return sb.toString();
    }

    /**
     * 把字符串分割为斐波拉序列
     * 回溯，当前值为前两个相加则加入结果队列
     *
     * @param S
     * @return
     */
    public List<Integer> splitIntoFibonacci(String S) {
        List<Integer> res = new ArrayList<>();
        return res;
    }

    public String shiftingLetters(String S, int[] shifts) {
        char[] sc = S.toCharArray();
        // 前i个数和
        long[] preS = new long[shifts.length + 1];
        preS[shifts.length] = 0;
        for (int i = shifts.length - 1; i >= 0; i--) {
            preS[i] = preS[i + 1] + shifts[i];
        }

        for (int j = 0; j < sc.length; j++) {
            int wordi = (int) sc[j];
            long shift = preS[j] % 26;
            int pi = wordi + (int) shift;
            if (pi > 122) {
                pi = pi - 123 + 97;
            }
            sc[j] = (char) pi;
        }

        return String.valueOf(sc);
    }

    /**
     * 移除无效括号
     * 用一个set存储需要删除的位置
     * 栈存储左括号的位置，遇到右括号则弹出一个左括号
     *
     * @param s
     * @return
     */
    public String minRemoveToMakeValid(String s) {
        return "";
    }

    public boolean isValid(String s) {
        List<Character> clist = s.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        return help(clist);
    }

    public boolean help(List<Character> sc) {
        if (sc.size() == 0) {
            return true;
        }
        Set<Integer> delInd = new HashSet<>();
        int index = 0;
        while (index < sc.size() - 2) {
            if ((String.valueOf(sc.get(index)) + sc.get(index + 1) + sc.get(index + 2)).equals("abc")) {
                delInd.add(index);
                delInd.add(index + 1);
                delInd.add(index + 2);
                index += 3;
            } else {
                index++;
            }
        }
        if (delInd.size() == 0) {
            return false;
        }
        List<Character> nc = new ArrayList<>();
        for (int i = 0; i < sc.size(); i++) {
            if (!delInd.contains(i)) {
                nc.add(sc.get(i));
            }
        }
        return help(nc);
    }

    /**
     * 1081，316 返回 s 字典序最小的子序列，该子序列包含 s 的所有不同字符，且只包含一次。
     * 单调栈，从前往后扫字符串，如果栈顶的元素大于s[i]，则删除
     * 每个字符要有且仅出现一次，用一个boolean数组记录是否出现过，没出现过才加入栈
     * 用int数组记录每个字母剩余的个数，在弹出栈时，如果次数等于0，则不能删除
     *
     * @param s
     * @return
     */
    public String smallestSubsequence(String s) {
        boolean[] isAppear = new boolean[26];
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            // 没有出现过
            if (!isAppear[c - 'a']) {
                //循环和结果的最后一个字符比较
                while (res.length() > 0 && res.charAt(res.length() - 1) > c) {
                    Character last = res.charAt(res.length() - 1);
                    if (count[last - 'a'] > 0) {
                        isAppear[last - 'a'] = false;
                        res.deleteCharAt(res.length() - 1);
                    } else {
                        break;
                    }
                }
                res.append(c);
                isAppear[c - 'a'] = true;
            }
            // 把当前字符的数量减一
            count[c - 'a']--;
        }
        return res.toString();
    }

    public int countGoodSubstrings(String s) {
        int count = 0;
        for (int i = 0; i < s.length() - 2; i++) {
            String sub = s.substring(i, i + 3);
            if (sub.charAt(0) != sub.charAt(1) && sub.charAt(0) != sub.charAt(2) && sub.charAt(1) != sub.charAt(2)) {
                count++;
            }
        }
        return count;
    }

    public boolean match(char[] str, char[] pattern) {
        int ptr = 0;
        int qtr = 0;
        for (ptr = 0; ptr < str.length; ptr++) {
            char sChar = str[ptr];

            while (qtr < pattern.length) {
                if (sChar == pattern[qtr]) {
                    qtr++;
                    break;
                } else if (pattern[qtr] == '.') {
                    qtr++;
                    break;
                } else {
                    while (qtr < pattern.length) {
                        if (pattern[qtr] == '*') {
                            break;
                        }
                        qtr++;
                    }
                    if (qtr == pattern.length && ptr != str.length) {
                        return false;
                    }
                    qtr++;
                    break;
                }

            }


        }
        if ((ptr == str.length && qtr != pattern.length) || (ptr != str.length && qtr == pattern.length)) {
            return false;
        }
        return true;
    }

}

