class Solution {
    private static final long LIMIT = 1000000L;

    public String smallestPalindrome(String s, int k) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;

        int[] half = new int[26];
        StringBuilder mid = new StringBuilder();

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            if ((freq[i] & 1) == 1)
                mid.append((char) ('a' + i));
        }

        int len = 0;
        for (int x : half) len += x;

        if (count(half) < k) return "";

        StringBuilder left = new StringBuilder();

        while (len-- > 0) {
            for (int i = 0; i < 26; i++) {
                if (half[i] == 0) continue;

                half[i]--;
                long ways = count(half);

                if (ways >= k) {
                    left.append((char) ('a' + i));
                    break;
                }

                k -= ways;
                half[i]++;
            }
        }

        String right = left.reverse().toString();
        left.reverse();

        return left.toString() + mid + right;
    }

    private long count(int[] half) {
        int total = 0;
        for (int x : half) total += x;

        long ans = 1;
        int rem = total;

        for (int x : half) {
            if (x == 0) continue;
            ans *= comb(rem, x);
            if (ans > LIMIT) return LIMIT;
            rem -= x;
        }

        return Math.min(ans, LIMIT);
    }

    private long comb(int n, int r) {
        if (r > n - r) r = n - r;

        long res = 1;
        for (int i = 1; i <= r; i++) {
            res = res * (n - r + i) / i;
            if (res > LIMIT) return LIMIT;
        }
        return res;
    }
}