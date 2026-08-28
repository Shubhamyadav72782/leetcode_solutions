class Solution {
    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();

        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Check if palindrome is possible
        int odd = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) {
                odd++;
                middle = (char) ('a' + i);
            }
        }

        if (odd > 1) {
            return "";
        }

        // Frequency of left half
        int[] half = new int[26];

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
        }

        int m = n / 2;

        String best = null;

        /*
         * Try every position where our answer
         * becomes greater than target.
         */
        for (int pos = 0; pos < m; pos++) {

            int[] rem = half.clone();

            // Prefix must be exactly same as target
            boolean possible = true;

            for (int i = 0; i < pos; i++) {
                int x = target.charAt(i) - 'a';

                if (rem[x] == 0) {
                    possible = false;
                    break;
                }

                rem[x]--;
            }

            if (!possible) {
                continue;
            }

            int targetChar = target.charAt(pos) - 'a';

            // Put a character GREATER than target[pos]
            for (int c = targetChar + 1; c < 26; c++) {

                if (rem[c] == 0) {
                    continue;
                }

                StringBuilder left = new StringBuilder();

                // Same prefix
                left.append(target, 0, pos);

                // Greater character
                left.append((char) ('a' + c));

                rem[c]--;

                // Fill remaining left half with smallest chars
                for (int x = 0; x < 26; x++) {
                    while (rem[x] > 0) {
                        left.append((char) ('a' + x));
                        rem[x]--;
                    }
                }

                String candidate = makePalindrome(
                        left.toString(),
                        middle,
                        n
                );

                if (candidate.compareTo(target) > 0) {

                    if (best == null ||
                        candidate.compareTo(best) < 0) {
                        best = candidate;
                    }
                }

                // Only smallest greater char is needed
                break;
            }
        }

        /*
         * Special case:
         * Left half can be exactly equal to target's left half.
         *
         * The palindrome may still be greater because
         * its right half differs from target's right half.
         */
        int[] rem = half.clone();
        boolean possible = true;

        for (int i = 0; i < m; i++) {

            int x = target.charAt(i) - 'a';

            if (rem[x] == 0) {
                possible = false;
                break;
            }

            rem[x]--;
        }

        if (possible) {

            String left = target.substring(0, m);

            String candidate = makePalindrome(
                    left,
                    middle,
                    n
            );

            if (candidate.compareTo(target) > 0) {

                if (best == null ||
                    candidate.compareTo(best) < 0) {
                    best = candidate;
                }
            }
        }

        return best == null ? "" : best;
    }

    private String makePalindrome(
            String left,
            char middle,
            int n) {

        StringBuilder ans = new StringBuilder();

        ans.append(left);

        if (n % 2 == 1) {
            ans.append(middle);
        }

        for (int i = left.length() - 1; i >= 0; i--) {
            ans.append(left.charAt(i));
        }

        return ans.toString();
    }
}