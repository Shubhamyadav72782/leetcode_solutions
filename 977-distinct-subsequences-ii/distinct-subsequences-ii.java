class Solution {
    public int distinctSubseqII(String s) {
        final int mod = 1_000_000_007;

        long[] dp = new long[26];

        for (char ch : s.toCharArray()) {
            int index = ch - 'a';

            long total = 1; // current character itself

            for (long count : dp) {
                total = (total + count) % mod;
            }

            // All subsequences ending with this character
            dp[index] = total;
        }

        long answer = 0;

        for (long count : dp) {
            answer = (answer + count) % mod;
        }

        return (int) answer;
    }
}