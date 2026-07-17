import java.util.*;

class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        int max = 0;
        for (int x : nums) max = Math.max(max, x);

        int[] freq = new int[max + 1];
        for (int x : nums) freq[x]++;

        long[] gcdCount = new long[max + 1];

        // Count pairs whose gcd is exactly g
        for (int g = max; g >= 1; g--) {
            long cnt = 0;
            for (int m = g; m <= max; m += g) {
                cnt += freq[m];
                gcdCount[g] -= gcdCount[m];
            }
            gcdCount[g] += cnt * (cnt - 1) / 2;
        }

        // Prefix sums
        for (int i = 1; i <= max; i++) {
            gcdCount[i] += gcdCount[i - 1];
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            ans[i] = upperBound(gcdCount, queries[i]);
        }
        return ans;
    }

    private int upperBound(long[] prefix, long target) {
        int lo = 0, hi = prefix.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (prefix[mid] > target)
                hi = mid;
            else
                lo = mid + 1;
        }
        return lo;
    }
}