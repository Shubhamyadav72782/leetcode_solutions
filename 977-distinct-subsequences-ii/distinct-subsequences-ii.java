class Solution {
    public int distinctSubseqII(String s) {
        final int MOD = 1_000_000_007;

        long[] dp = new long[26];
        long total = 0;

        for (char ch : s.toCharArray()) {
            int index = ch - 'a';

            // New subsequences ending with current character
            long add = (total + 1) % MOD;

            // Replace old subsequences ending with this character
            total = (total - dp[index] + add + MOD) % MOD;

            dp[index] = add;
        }

        return (int) total;
    }
}