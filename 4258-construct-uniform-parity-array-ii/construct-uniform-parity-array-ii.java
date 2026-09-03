class Solution {
    public boolean uniformArray(int[] nums1) {

        int min = Integer.MAX_VALUE;

        // Find minimum element
        for (int x : nums1) {
            min = Math.min(min, x);
        }

        // If minimum is odd, answer is always true
        if (min % 2 == 1) {
            return true;
        }

        // Minimum is even.
        // Then every element must also be even.
        for (int x : nums1) {
            if (x % 2 == 1) {
                return false;
            }
        }

        return true;
    }
}