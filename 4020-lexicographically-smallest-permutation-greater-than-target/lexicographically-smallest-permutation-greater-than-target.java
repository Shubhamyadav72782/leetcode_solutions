class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        StringBuilder prefix = new StringBuilder();

        for (int i = 0; i < n; i++) {
            int t = target.charAt(i) - 'a';

            // Try to keep same character
            if (freq[t] > 0) {
                freq[t]--;
                prefix.append(target.charAt(i));
            } else {
                // Same character unavailable,
                // try a bigger character at this position
                for (int c = t + 1; c < 26; c++) {
                    if (freq[c] > 0) {
                        StringBuilder ans = new StringBuilder(prefix);
                        ans.append((char) ('a' + c));
                        freq[c]--;

                        addRemaining(ans, freq);
                        return ans.toString();
                    }
                }

                // Need to backtrack
                break;
            }
        }

        // target prefix matched completely.
        // Now find the next greater permutation.
        while (prefix.length() > 0) {
            int pos = prefix.length() - 1;
            int old = prefix.charAt(pos) - 'a';

            freq[old]++;
            prefix.deleteCharAt(pos);

            // Find smallest character greater than old
            for (int c = old + 1; c < 26; c++) {
                if (freq[c] > 0) {
                    StringBuilder ans = new StringBuilder(prefix);
                    ans.append((char) ('a' + c));
                    freq[c]--;

                    addRemaining(ans, freq);
                    return ans.toString();
                }
            }
        }

        return "";
    }

    private void addRemaining(StringBuilder ans, int[] freq) {
        for (int i = 0; i < 26; i++) {
            while (freq[i] > 0) {
                ans.append((char) ('a' + i));
                freq[i]--;
            }
        }
    }
}