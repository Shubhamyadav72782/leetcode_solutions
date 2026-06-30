class Solution {
    public int numberOfSubstrings(String s) {
        int n = s.length();
        int[] freq = new int[3]; // frequency of a, b, c
        int left = 0;
        int ans = 0;

        for (int right = 0; right < n; right++) {
            freq[s.charAt(right) - 'a']++;

            // If the current window has a, b and c,
            // then every substring ending from 'right' to 'n-1' is valid.
            while (freq[0] > 0 && freq[1] > 0 && freq[2] > 0) {
                ans += n - right;

                // Remove the leftmost character and shrink the window
                freq[s.charAt(left) - 'a']--;
                left++;
            }
        }

        return ans;
    }
}