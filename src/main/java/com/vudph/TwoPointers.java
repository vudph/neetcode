package com.vudph;

import java.util.*;
import java.util.stream.Collectors;

public class TwoPointers {
    public boolean isPalindrome125(String s) {
        int l = 0, r = s.length() - 1;
        char ch[] = s.toCharArray();
        while (l < r) {
            while (l < ch.length && !Character.isLetterOrDigit(ch[l])) l++;
            while (r >= 0 && !Character.isLetterOrDigit(ch[r])) r--;
            if ((l < ch.length && r >= 0) && Character.toLowerCase(ch[l]) != Character.toLowerCase(ch[r])) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }

    public int[] twoSum167(int[] numbers, int target) {
//        1, 3, 4, 5, 7, 11  target = 9
        int res[] = new int[2];
        int l = 0, r = numbers.length - 1;
        while (l < r) {
            if (numbers[l] + numbers[r] == target) {
                break;
            }
            if (numbers[l] + numbers[r] > target) {
                r--;
            } else if (numbers[l] + numbers[r] < target) {
                l++;
            }
        }
        res[0] = l + 1;
        res[1] = r + 1;
        return res;
    }

    public List<List<Integer>> threeSum15Bruteforce(int[] nums) {
        Arrays.sort(nums);
        Set<CustomList> res = new HashSet<>();
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        res.add(new CustomList(nums[i], nums[j], nums[k]));
                    }
                }
            }
        }

        return res.stream().map(e -> List.of(e.ele[0], e.ele[1], e.ele[2])).toList();
    }

    static class CustomList {
        int[] ele = new int[3];
        public CustomList(int x, int y, int z) {
            ele[0] = x;
            ele[1] = y;
            ele[2] = z;
            Arrays.sort(ele);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof CustomList))
                return false;
            CustomList cl = (CustomList) obj;
            return ele[0] == cl.ele[0] && ele[1] == cl.ele[1] && ele[2] == cl.ele[2];
        }

        @Override
        public int hashCode() {
            int hash = Objects.hash(ele[0], ele[1], ele[2]);
            return hash;
        }
    }

    public List<List<Integer>> threeSum15Hash(int[] nums) {
//      -1, 0, 1, 2,-1,-4, 3, 3
//      -4,-1,-1, 0, 1, 2, 3, 3
//     [-4,1,3],[-4,1,3],[-1,0,1],[-1,0,1],[0,-1,1],[0,-1,1],[1,-1,2],....

        Set<CustomList> res = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            Set<Integer> seen = new HashSet<>();
            int target = -nums[i];
            for (int j = 0; j < nums.length && j != i; j++) {
                if (seen.contains(target - nums[j])) {
                    res.add(new CustomList(nums[i], nums[j], target - nums[j]));
                } else {
                    seen.add(nums[j]);
                }
            }
        }

        return res.stream().map(e -> List.of(e.ele[0], e.ele[1], e.ele[2])).toList();
    }

    public List<List<Integer>> threeSum15Sort(int[] nums) {
//      -3, 3, 4,-3, 1, 2
//      -3,-3, 1, 2, 3, 4
//      [-3, 1, 2]

//      -1, 0, 1, 2,-1,-4, 3, 3
//      -4,-1,-1, 0, 1, 2, 3, 3
//      [-4,1,3], [-1,-1,2], [-1,0,1]

//      -2,0,0,2,2
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                int target = -nums[i];
                int l = i + 1;
                int r = nums.length - 1;
                while (l < r) {
                    if (nums[l] + nums[r] == target) {
                        res.add(List.of(nums[i], nums[l], nums[r]));
                        l++;
//                        r--;
                        while (l < nums.length && nums[l - 1] == nums[l]) {
                            l++;
                        }
//                        while (r >= 0 && nums[r] == nums[r + 1]) {
//                            r--;
//                        }
                    } else if (l < nums.length && nums[l] + nums[r] < target) {
                        l++;
                    } else if (r >= 0 && nums[l] + nums[r] > target) {
                        r--;
                    }
                }
            }
        }

        return res;
    }

    public int maxArea11(int[] height) {
//      0,  1,  2,  3,  4,  5,  6,  7,  8,  9
//     [1,  8,  6,  2,  5,  4,  8,  3,  7,  1]
//      1, 10,  9,  6, 10, 10, 15, 11, 16, 11

//      1, 1, 1, 1, 8, 1, 7, 1, 1, 1
//      1, 2, 3, 4,13, 7,14, 9,10,11

//      1, 1, 1, 1, 8, 7, 1, 1, 1
//      1, 2, 3, 4,13,13, 8, 9,10

        int maxArea = 0;
//        for (int i = 0; i < height.length; i++) {
//            int l = i;
//            for (int j = height.length - 1; j > i; j--) {
//                int r = j;
//                int min = Math.min(height[l], height[r]);
//                int diff = r - l;
//                maxArea = Math.max(maxArea, min * diff);
//            }
//        }
//      0   1   2   3   4   5   6   7   8
//     [2,  6,  2,  5,  4,  8,  3,  7,  2]
        int l = 0, r = height.length - 1;
        while (l < r) {
            if (height[l] > height[r]) {
                maxArea = Math.max(maxArea, height[r] * (r - l));
                r--;
            } else if (height[l] < height[r]) {
                maxArea = Math.max(maxArea, height[l] * (r - l));
                l++;
            } else {
                maxArea = Math.max(maxArea, height[l] * (r - l));
                l++;
                r--;
            }
        }

        return maxArea;
    }

    public static void main(String[] args) {
        TwoPointers tp = new TwoPointers();
        System.out.println("isPalindrome125: " + tp.isPalindrome125("PP"));
//        System.out.println("threeSum15Bruteforce: " + tp.threeSum15Bruteforce(new int[]{-1,0,1,2,-1,-4,3,3}));
//        System.out.println("threeSum15Hash: " + tp.threeSum15Hash(new int[]{-1,0,1,2,-1,-4,3,3}));
        System.out.println("threeSum15Sort: " + tp.threeSum15Sort(new int[]{-2,0,0,2,2}));
        System.out.println("maxArea11: " + tp.maxArea11(new int[] {1, 1}));
    }
}
