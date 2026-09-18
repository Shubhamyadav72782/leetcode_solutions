class Solution {

    public List<String> maxNumOfSubstrings(String s) {

        int n = s.length();

        int[] first = new int[26];
        int[] last = new int[26];

        Arrays.fill(first, n);
        Arrays.fill(last, -1);

        // First and last occurrence
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';

            first[c] = Math.min(first[c], i);
            last[c] = i;
        }

        // Store valid intervals: {end, start}
        List<int[]> intervals = new ArrayList<>();

        for (int c = 0; c < 26; c++) {

            if (last[c] == -1)
                continue;

            int left = first[c];
            int right = last[c];

            boolean valid = true;

            // Expand interval
            for (int i = left; i <= right; i++) {

                int x = s.charAt(i) - 'a';

                // Character occurs before our left boundary
                if (first[x] < left) {
                    valid = false;
                    break;
                }

                right = Math.max(right, last[x]);
            }

            if (valid) {
                intervals.add(new int[]{right, left});
            }
        }

        // Sort by ending position
        intervals.sort((a, b) -> Integer.compare(a[0], b[0]));

        List<String> ans = new ArrayList<>();

        int prevEnd = -1;

        // Greedy: choose interval with smallest end
        for (int[] interval : intervals) {

            int right = interval[0];
            int left = interval[1];

            if (left > prevEnd) {

                ans.add(s.substring(left, right + 1));

                prevEnd = right;
            }
        }

        return ans;
    }
}