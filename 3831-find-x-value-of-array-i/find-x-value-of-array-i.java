class Solution {
    public long[] resultArray(int[] nums, int k) {

        long[] ans = new long[k];

        // dp[r] = current position par end hone wale
        // subarrays jinka product % k = r
        long[] dp = new long[k];

        for (int num : nums) {

            int mod = num % k;

            long[] next = new long[k];

            // Start a new subarray with current number
            next[mod] = 1;

            // Extend previous subarrays
            for (int r = 0; r < k; r++) {

                int newR = (int) ((long) r * mod % k);

                next[newR] += dp[r];
            }

            // Add all current subarrays to answer
            for (int r = 0; r < k; r++) {
                ans[r] += next[r];
            }

            dp = next;
        }

        return ans;
    }
}