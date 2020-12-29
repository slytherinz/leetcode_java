package com.cloudlz.binaryIndexedTree;

/**
 * 树状数组
 */
public class BinaryIndexTree {
    public int[] c;

    public int lowbit(int x) {
        return x & (-x);
    }

    // 单点更新
    public void update(int pos, int val) {
        while(pos <= c.length) {
            c[pos] += val;
            pos += lowbit(pos);
        }
    }

    // 查询前缀和
    public int query(int pos) {
        int sum = 0;
        while (pos > 0) {
            sum += c[pos];
            pos -= lowbit(pos);
        }
        return sum;
    }
}
