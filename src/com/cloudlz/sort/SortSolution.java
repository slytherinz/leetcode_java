package com.cloudlz.sort;

import java.util.Arrays;
import java.util.PriorityQueue;

public class SortSolution {
    //快排
    //堆排
    /**
     * 215. 数组中的第K个最大元素
     * 在未排序的数组中找到第 k 个最大的元素。
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        //堆类型题，不是分治
        //小顶堆,将所有数组中的元素加入堆中
        //堆中保留了前 k 个最大的元素
        PriorityQueue<Integer> heap =
                new PriorityQueue<Integer>((n1, n2) -> n1 - n2);

        // keep k largest elements in the heap
        for (int n: nums) {
            heap.add(n);
            if (heap.size() > k)
                heap.poll();
        }
        // output
        return heap.poll();
    }
    //归并排序
}
