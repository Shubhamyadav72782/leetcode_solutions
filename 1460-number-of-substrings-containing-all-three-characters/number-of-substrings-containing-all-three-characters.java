/*class Solution {
    public int numberOfSubstrings(String s) {
        int n = s.length();
        int[] freq = new int[3]; 
        int left = 0;
        int ans = 0;

        for (int right = 0; right < n; right++) {
            freq[s.charAt(right) - 'a']++;
            while (freq[0] > 0 && freq[1] > 0 && freq[2] > 0) {
                ans += n - right;
                freq[s.charAt(left) - 'a']--;
                left++;
            }
        }

        return ans;
    }
}*/



class Solution {
    public int numberOfSubstrings(String s) {
        int subStringsCount = 0;
        int[] aCountIdx = new int[50000];
        int[] bCountIdx = new int[50000];
        int[] cCountIdx = new int[50000];
        aCountIdx[0] = -1;
        bCountIdx[0] = -1;
        cCountIdx[0] = -1;
        int aCount = 0;
        int bCount = 0;
        int cCount = 0;
        for (int idx = 0; idx <= s.length() - 1; ++idx) {
            if (s.charAt(idx) == 'a') {
                ++aCount;
            } else if (s.charAt(idx) == 'b') {
                ++bCount;
            } else {
                ++cCount;
            }
            if (aCount >= 1 && bCount >= 1 && cCount >= 1) {
                int lastSeenA = aCountIdx[aCount - 1];
                int lastSeenB = bCountIdx[bCount - 1];
                int lastSeenC = cCountIdx[cCount - 1];
                int minIdx = Math.min(lastSeenA, Math.min(lastSeenB, lastSeenC));
                subStringsCount += (minIdx + 1) + 1;
            }
            aCountIdx[aCount] = idx;
            bCountIdx[bCount] = idx;
            cCountIdx[cCount] = idx;
        }
        return subStringsCount;
    }
}