package com.vudph;

import java.util.*;

public class ArrayHashing {
    public boolean containsDuplicate217(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (!seen.add(nums[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean isAnagram242(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int count[] = new int[26];
        char cs[] = s.toCharArray();
        char ct[] = t.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            count[cs[i] - 'a']++;
            count[ct[i] - 'a']--;
        }
        for (int i = 0; i < count.length; i++) {
            if (count[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public int[] twoSum1(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>();
        seen.put(nums[0], 0);
        for (int i = 1; i < nums.length; i++) {
            if (seen.containsKey(target - nums[i])) {
                return new int[] {seen.get(target - nums[i]), i};
            }
            seen.put(nums[i], i);
        }
        return new int[2];
    }
    public List<List<String>> groupAnagrams49(String[] strs) {
        Map<String, List<String>> group = new HashMap<>();
        int count[] = new int[26];

        for (String s : strs) {
            Arrays.fill(count, 0);
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }
            String countToString = Arrays.toString(count);
            group.putIfAbsent(countToString, new ArrayList<>());
            group.get(countToString).add(s);
        }
        return new ArrayList<>(group.values());
    }

    public int[] topKFrequent347(int[] nums, int k) {
//      Input: nums = [1,3,1,2,1,2,4,4], k = 2
//      Output: [1,2]
        Map<Integer, CustomEleFreq> seen = new HashMap<>();
        PriorityQueue<CustomEleFreq> pq = new PriorityQueue<>();
        for (int i = 0; i < nums.length; i++) {
            CustomEleFreq c;
            if (seen.containsKey(nums[i])) {
                c = seen.get(nums[i]);
                c.freq++;
            } else {
                c = new CustomEleFreq(nums[i], 1);
                seen.put(nums[i], c);
            }

        }
        for (CustomEleFreq c : seen.values()) {
            pq.add(c);
        }
        int res[] = new int[k];
        for (int i = 0; i < k; i++) {
            CustomEleFreq c = pq.poll();
//            System.out.println(">> (" + c.val + ":" + c.freq + ")");
            res[i] = c.val;
        }
        return res;
    }

    static class CustomEleFreq implements Comparable<CustomEleFreq> {
        int val, freq;
        public CustomEleFreq(int v, int f) {
            this.val = v;
            this.freq = f;
        }

        @Override
        public int compareTo(CustomEleFreq o) {
            if (!(o instanceof CustomEleFreq)) {
                return -1;
            }
            return o.freq - this.freq;
        }
    }
    public int[] topKFrequent347Bucket(int[] nums, int k) {
//      Input: nums = [1,3,1,2,1,2,4,4], k = 2
//      freq[1]=3, freq[2]=2, freq[3]=1, freq[4]=2
//      bucket[1]=3; bucket[2]=2,4; bucket[3]=1

//      Input: nums = [-4,-4,6,7,6,7,8,8,6,8,8], k = 2
//      min=-4, max=8 -> freq size=8-(-4)+1=13
//          -> freq[-4-(-4)]=freq[0]=2, freq[1]=0, freq[2]=0,...,
//                  freq[6-(-4)]=freq[10]=3, freq[7-(-4)]=freq[11]=2, freq[8-(-4)]=freq[12]=4
//      maxFreq=4 -> bucket size=4+1=5
//      bucket[0]={}, bucket[1]={}, bucket[2]={-4,11}, bucket[3]={6}, bucket[4]={8}

//      Array style
//        int max = 0, min = 0;
//        for (int i = 0; i < nums.length; i++) {
//            max = Math.max(max, nums[i]);
//            min = Math.min(min, nums[i]);
//        }
//        int freq[] = new int[max - min + 1];
//        for (int i = 0; i < nums.length; i++) {
//            freq[nums[i] - min]++;
//        }
//        int maxFreq = 0;
//        for (int i = 0; i < freq.length; i++) {
//            maxFreq = Math.max(maxFreq, freq[i]);
//        }
//        List<Integer> bucketOfFreq[] = new ArrayList[maxFreq + 1];
//        for (int i = 0; i < bucketOfFreq.length; i++) {
//            bucketOfFreq[i] = new ArrayList<>();
//        }
//        for (int i = 0; i < freq.length; i++) {
//            if (freq[i] != 0) {
//                bucketOfFreq[freq[i]].add(i + min);
//            }
//        }
//        int ret[] = new int[k];
//        int n = bucketOfFreq.length - 1;
//        for (int i = n, j = 0; i >= 0 && j < k; i--) {
//            for (int l = 0; l < bucketOfFreq[i].size() && j < k; l++) {
//                ret[j++] = bucketOfFreq[i].get(l);
//            }
//        }
//        return ret;

//        map style
//        Input: nums = [-4,-4,6,7,6,7,8,8,6,8,8], k = 2
//        {-4:2, 6:3, 7:2, 8:4}
//        [0:{}, 1:{}, 2:{-4,7}, 3:{6}, 4:{8}, ..., 10:{}]

        Map<Integer, Integer> freq = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            freq.put(nums[i], freq.getOrDefault(nums[i], 0) + 1);
        }

        List<Integer> bucketOfFreq[] = new ArrayList[nums.length + 1];
        for (int i = 0; i < bucketOfFreq.length; i++) {
            bucketOfFreq[i] = new ArrayList<>();
        }
        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            bucketOfFreq[entry.getValue()].add(entry.getKey());
        }
        int ret[] = new int[k];
        int l = 0;
        for (int i = bucketOfFreq.length - 1; i >= 0; i--) {
            for (int j = 0; j <bucketOfFreq[i].size() && k > 0; j++, k--) {
                ret[l++] = bucketOfFreq[i].get(j);
            }
        }
        // worst case: [0,1,2,3,4,5,6], k=7
//        [1,1,1,1,1,1] -> {1:6} -> [6:{1}]
        return ret;
    }

    public int[] productExceptSelf238(int[] nums) {
//            2, 3,  4,  5
// product:   2, 6, 24, 120
// result:            , 24*1 x=1
//                ,6*5, 24   x=5

//        -1, 1, 0,-3, 3
//        -1,-1, 0, 0, 0
        int product[] = new int[nums.length];
        product[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            product[i] = product[i - 1] * nums[i];
        }
        int x = 1;
        for (int i = product.length - 1; i > 0; i--) {
            product[i] = product[i - 1] * x;
            x *= nums[i];
        }
        product[0] = x;
        return product;
//        int total = 1;
//        int zeroCount = 0;
//        for (int n : nums) {
//            if (n != 0) {
//                total *= n;
//            } else {
//                zeroCount++;
//            }
//        }
//        if (zeroCount > 1) {
//            Arrays.fill(nums, 0);
//            return nums;
//        }
//        for (int i = 0; i < nums.length; i++) {
//            if (nums[i] != 0 && zeroCount == 1) {
//                nums[i] = 0;
//            } else if (nums[i] == 0){
//                nums[i] = total;
//            } else {
//                nums[i] = total / nums[i];
//            }
//        }
//        return nums;
    }

    public boolean isValidSudoku36(char[][] board) {
        if (board.length == 0) return true;

        Set<Character> rows[] = new HashSet[9];
        Set<Character> cols[] = new HashSet[9];
        Set<Character> boxes[] = new HashSet[9];
        for (int i = 0; i < 9; i++) {
            rows[i] = new HashSet<>();
            cols[i] = new HashSet<>();
            boxes[i] = new HashSet<>();
        }
        int n = board.length; //nr of rows
        int m = board[0].length; //nor of columns
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] != '.') {
                    if (!rows[i].add(board[i][j]) || !cols[j].add(board[i][j]) ||
                            !boxes[((i/3)*3) + (j/3)].add(board[i][j])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public int longestConsecutive128(int[] nums) {
//        nums = [100, 4, 200, 3, 2, 1]
//        nums = [5, 4, 6, 3, 2, 1] => worst case: O(n+n), at ele 1 inner while will be called n times, other ele will be called once by outer loop only => O(2n) => O(n)
//        nums = [1, 4, 6, 3, 2, 5] => worst case: O(n+n), at ele 1 inner while will be called n times, other ele will be called once by outer loop only => O(2n) => O(n)
//        nums = [1, 3, 5, 7, 9, 11] => best case: O(n) (inner while will never be called)
        Set<Integer> seen = new HashSet<>();
        for (int n : nums) {
            seen.add(n);
        }
        int longest = 0;
        for (int n : nums) {
            if (!seen.contains(n - 1)) {
               int m = n + 1;
               while (seen.contains(m)) {
                   m++;
               }
               longest = Math.max(longest, m - n);
            }
        }
        return longest;
    }

    public static void main(String[] args) {
        ArrayHashing ah = new ArrayHashing();
        System.out.println("containsDuplicate217: " + ah.containsDuplicate217(new int[]{1, 2, 3, 1}));
        System.out.println("isAnagram242: " + ah.isAnagram242("abc", "cbd"));
        System.out.println("twoSum1: " + ah.twoSum1(new int[] {1,2,3,4,5},8));
        System.out.println("groupAnagrams49: " + ah.groupAnagrams49(new String[]{"eat","tea","tan","ate","nat","bat"}));
        System.out.println("topKFrequent347: " + ah.topKFrequent347(new int[] {1,3,1,2,1,2,4,4,4,4}, 2));
        System.out.println("topKFrequent347Bucket: " + ah.topKFrequent347Bucket(new int[]{-4,-4,6,7,6,7,8,8,6,8,8}, 2));
        System.out.println("productExceptSelf238: " + ah.productExceptSelf238(new int[]{2,3,4,5,0}));
        System.out.println("isValidSudoku36: " + ah.isValidSudoku36(new char[][]{}));
        System.out.println("longestConsecutive128: " + ah.longestConsecutive128(new int[] {1, 3, 5, 7, 9, 11}));
    }
}
