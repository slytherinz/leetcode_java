package com.cloudlz.binary_search;

/**
 * 二分查找
 */
public class BinarySearchSolution {
    /**
     * 4. 寻找两个有序数组的中位数
     * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        return 0;
    }

    /**
     * 33. 搜索旋转排序数组
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        //1.找到旋转的下标 rotation_index ，也就是数组中最小的元素。
        //2.在选中的数组区域中再次使用二分查找。
        return 0;
    }

    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        int[] res = {-1, -1};
        return res;
    }

    /**
     * 81. 搜索旋转排序数组 II(可能包含重复元素)
     *
     * @param nums
     * @param target
     * @return
     */
    public boolean search2(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int pivot = left + ((right - left) >> 1);
            if (nums[pivot] == target) {
                return true;
            }
            if (nums[left] == nums[pivot] && nums[pivot] == nums[right]) {
                left++;
                right--;
            } else if (nums[left] <= nums[pivot]) {
                if (nums[left] <= target && target < nums[pivot]) right = pivot - 1;
                else left = pivot + 1;
            } else {
                if (nums[pivot] < target && target <= nums[right]) left = pivot + 1;
                else right = pivot - 1;
            }
        }
        return false;
    }

    /**
     * 153. 寻找旋转排序数组中的最小值
     *
     * @param nums
     * @return
     */
    public int findMin(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int left = 0;
        int right = nums.length - 1;
        if (nums[right] > nums[left]) {
            return nums[0];
        }
        while (left <= right) {
            int pivot = left + ((right - left) >> 1);
            if (nums[pivot] > nums[pivot + 1]) {
                return nums[pivot + 1];
            } else {
                if (nums[pivot] < nums[left]) {
                    right = pivot - 1;
                } else {
                    left = pivot + 1;
                }
            }
        }
        return 0;
    }

    /**
     * 162. 寻找峰值
     *
     * @param nums
     * @return
     */
    public int findPeakElement(int[] nums) {
        return search(nums, 0, nums.length - 1);
    }

    public int search(int[] nums, int l, int r) {
        if (l == r)
            return l;
        int mid = (l + r) / 2;
        if (nums[mid] > nums[mid + 1])
            return search(nums, l, mid);
        return search(nums, mid + 1, r);
    }


}
