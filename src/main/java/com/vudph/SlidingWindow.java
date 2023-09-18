package com.vudph;

import java.util.*;

public class SlidingWindow {
    public int maxProfit121(int[] prices) {
        int profit = 0;
//        int minPrice = prices[0];
//        for (int i = 1; i < prices.length; i++) {
//            if (prices[i] < minPrice) {
//                minPrice = prices[i];
//            } else {
//                profit = Math.max(profit, prices[i] - minPrice);
//            }
//        }
        int l = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[l] < prices[i]) {
                profit = Math.max(profit, prices[i] - prices[l]);
            } else {
                l = i;
            }
        }
        return profit;
    }

    public int lengthOfLongestSubstring3(String s) {
//      pwwkew
        Set<Character> seen = new HashSet<>();
        char ch[] = s.toCharArray();
        int longest = 0;
        int left = 0;
        int right = 0;
        while (right < ch.length) {
            if (!seen.contains(ch[right])) {
                seen.add(ch[right]);
                longest = Math.max(longest, right - left + 1);
                right++;
            } else {
                seen.remove(ch[left++]);
            }
        }
        return longest;
    }

    public int characterReplacement424(String s, int k) {
//      AABCBBA k=1
//      ABABBA k=2
        int count[] = new int[26];
        int left = 0, right = 0;
        char ch[] = s.toCharArray();
        int longest = 0;
        while (right < ch.length) {
            count[ch[right] - 'A']++;
            int maxFreq = -1;
            for (int i = 0; i < 26; i++) {
                maxFreq = Math.max(maxFreq, count[i]);
            }
            int windowLen = right - left + 1;
            if (windowLen - maxFreq <= k) {
                longest = Math.max(longest, windowLen);
                right++;
            } else {
                count[ch[left] - 'A']--;
                count[ch[right] - 'A']--;
                left++;
            }
        }
        return longest;
    }
    public boolean checkInclusion567(String s1, String s2) {
//      s1 = "abd", s2 = "eidbwadbc" -> true
        if (s1.length() > s2.length()) {
            return false;
        }
        int count1[] = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            count1[s1.charAt(i) - 'a']++;
        }
        int count2[] = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            count2[s2.charAt(i) - 'a']++;
        }
        if (Arrays.equals(count1, count2)) {
            return true;
        }
        int left = 0;
        for (int right = s1.length(); right < s2.length(); right++) {
            count2[s2.charAt(left) - 'a']--;
            count2[s2.charAt(right) - 'a']++;
            if (Arrays.equals(count1, count2)) {
                return true;
            }
            left++;
        }
        return false;
    }

    public String minWindow76(String s, String t) {
//             0123456789012 len_s=13, len_t=3
//        s = "ADOBECODEBANC", t = "ABC"
        if (t.length() > s.length()) {
            return "";
        }
        Map<Character, Integer> count_t = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            count_t.put(t.charAt(i), count_t.getOrDefault(t.charAt(i), 0) + 1);
        }
        Map<Character, Integer> count_s = new HashMap<>();
        int left = 0, right = 0;
        String res = "";
        while (right <= s.length()) {
            if (validateMap(count_s, count_t)) {
                String r = s.substring(left, right);
                if (res.isEmpty() || r.length() < res.length()) {
                    res = r;
                }
                count_s.put(s.charAt(left), count_s.get(s.charAt(left)) - 1);
                left++;
            } else {
                if (right == s.length()) {
                    break;
                }
                count_s.put(s.charAt(right), count_s.getOrDefault(s.charAt(right), 0) + 1);
                right++;
            }
        }
        return res;
    }

    private boolean validateMap(Map<Character, Integer> count_s, Map<Character, Integer> count_t) {
        for (Map.Entry<Character, Integer> e : count_t.entrySet()) {
            if (e.getValue() > count_s.getOrDefault(e.getKey(), 0)) {
                return false;
            }
        }
        return true;
    }

    public String minWindow76Arr(String s, String t) {
        if (t.length() > s.length()) {
            return "";
        }
        int count_t[] = new int[128];
        for (int i = 0; i < t.length(); i++) {
            count_t[t.charAt(i)]++;
        }

        int count_s[] = new int[128];
        for (int i = 0; i < t.length(); i++) {
            count_s[s.charAt(i)]++;
            count_t[s.charAt(i)]--;
        }
        int left = 0, right = t.length() - 1;
        String res = "";
        while (left <= s.length() - t.length()) {
            if (validateArr(count_t, count_s)) {
                String r = s.substring(left, right + 1);
                if (res.isEmpty() || r.length() < res.length()) {
                    res = r;
                }
                count_t[s.charAt(left)]++;
                left++;
            } else {
                right++;
                if (right == s.length()) {
                    break;
                }
                count_s[s.charAt(right)]++;
                count_t[s.charAt(right)]--;
            }
        }
        return res;
    }

    private boolean validateArr(int[] count_t, int[] count_s) {
        for (int i = 0; i < 128; i++) {
            if (count_t[i] > 0) {
                return false;
            }
        }
        return true;
    }



    public static void main(String[] args) {
        SlidingWindow sw = new SlidingWindow();
        System.out.println("maxProfit121: " + sw.maxProfit121(new int[]{1,2,4,3,6}));
        System.out.println("lengthOfLongestSubstring3: " + sw.lengthOfLongestSubstring3(""));
        System.out.println("characterReplacement424: " + sw.characterReplacement424("AABCBBA", 1));
        System.out.println("checkInclusion567: " + sw.checkInclusion567("abd", "aeidbwadbc"));
        System.out.println("minWindow76: " + sw.minWindow76("ADOBECODEBANCABF", "ABC"));

    }
}
