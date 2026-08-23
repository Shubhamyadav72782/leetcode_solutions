class Solution {
    public boolean sumGame(String num) {
        int leftSum = 0;
        int rightSum = 0;

        int leftQ = 0;
        int rightQ = 0;

        int n = num.length();

        for (int i = 0; i < n / 2; i++) {
            if (num.charAt(i) == '?') {
                leftQ++;
            } else {
                leftSum += num.charAt(i) - '0';
            }
        }

        for (int i = n / 2; i < n; i++) {
            if (num.charAt(i) == '?') {
                rightQ++;
            } else {
                rightSum += num.charAt(i) - '0';
            }
        }
    if ((leftQ + rightQ) % 2 == 1) {
            return true;
        }

        int diff = leftSum - rightSum;

        // Difference in number of ? on both sides
        int qDiff = leftQ - rightQ;

        return diff + (qDiff / 2) * 9 != 0;
    }
}