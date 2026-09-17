class Solution {
    public int minSumOfLengths(int[] arr, int target) {

        int n = arr.length;
        int INF = 1000000;

        int[] dp = new int[n + 1];
        Arrays.fill(dp, INF);

        HashMap<Integer, Integer> map = new HashMap<>();

        map.put(0, -1);

        int prefix = 0;
        int ans = INF;

        for (int i = 0; i < n; i++) {

            prefix += arr[i];

            // Need prefix - target
            int need = prefix - target;

            if (map.containsKey(need)) {

                int start = map.get(need) + 1;
                int len = i - start + 1;

                // Previous non-overlapping subarray
                if (start > 0 && dp[start] != INF) {
                    ans = Math.min(ans, len + dp[start]);
                }

                // Store shortest subarray ending here
                dp[i + 1] = Math.min(dp[i + 1], len);
            }

            // Carry previous best
            dp[i + 1] = Math.min(dp[i + 1], dp[i]);

            map.put(prefix, i);
        }

        return ans == INF ? -1 : ans;
    }
}