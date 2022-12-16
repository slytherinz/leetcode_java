package com.cloudlz.sort;

import java.util.Arrays;
import java.util.PriorityQueue;

public class SortSolution {
    //快排、堆排、归并都是时间复杂度为O(N*logN)，效率比较高在算法中比较常用

    //快排
    //快速选择排序主要思路是：
    //“挖坑填数+分治法”，首先令i =L; j = R; 将a[i]挖出形成第一个坑，称a[i]为基准数。
    // 然后j--由后向前找比基准数小的数，找到后挖出此数填入前一个坑a[i]中，
    // 再i++由前向后找比基准数大的数，找到后也挖出此数填到前一个坑a[j]中。
    // 重复进行这种“挖坑填数”直到i==j。再将基准数填入a[i]中，
    // 这样i之前的数都比基准数小，i之后的数都比基准数大。因此将数组分成二部分再分别重复上述步骤就完成了排序。
    public void quickSort(int s[], int start, int end) {
        //挖坑填坑
        if (start < end) {
            int left = start, right = end, base = s[left];//x为第一个坑
            while (left < right) {
                while (left < right && s[right] >= base) // 从右向左找第一个小于x的数
                    right--;
                if (left < right) {
                    s[left] = s[right];
                    left++;
                }

                while (left < right && s[left] <= base) // 从左向右找第一个大于x的数
                    left++;
                if (left < right) {
                    s[right] = s[left];
                    right--;
                }

            }
            s[left] = base;
            quickSort(s, start, left - 1); // 递归调用
            quickSort(s, left + 1, end);
        }
    }

    //归并排序
    public static void mergeSort(int[] arr) {
        //在排序前，先建好一个长度等于原数组长度的临时数组，避免递归中频繁开辟空间
        int[] temp = new int[arr.length];
        mergesort(arr, 0, arr.length - 1, temp);
    }

    private static void mergesort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergesort(arr, left, mid, temp);//左边归并排序，使得左子序列有序
            mergesort(arr, mid + 1, right, temp);//右边归并排序，使得右子序列有序
            mergeArray(arr, left, mid, right, temp);//将两个有序子数组合并操作
        }
    }

    private static void mergeArray(int[] arr, int first, int mid, int last, int[] temp) {
        int i = first;//左序列指针
        int j = mid + 1;//右序列指针
        int t = 0;//临时数组指针
        while (i <= mid && j <= last) {
            if (arr[i] <= arr[j]) {
                temp[t++] = arr[i++];
            } else {
                temp[t++] = arr[j++];
            }
        }
        while (i <= mid) {//将左边剩余元素填充进temp中
            temp[t++] = arr[i++];
        }
        while (j <= last) {//将右序列剩余元素填充进temp中
            temp[t++] = arr[j++];
        }

        //将temp中的元素全部拷贝到原数组中
        for (i = 0; i < t; i++)
            arr[first + i] = temp[i];
    }

    //堆排
    //每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆；
    // 或者每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆。


    /**
     * 215. 数组中的第K个最大元素
     * 在未排序的数组中找到第 k 个最大的元素。
     *
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
        for (int n : nums) {
            heap.add(n);
            if (heap.size() > k)
                heap.poll();
        }
        // output
        return heap.poll();
    }


}
