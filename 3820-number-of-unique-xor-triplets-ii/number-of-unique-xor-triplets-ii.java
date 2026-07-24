import java.util.*;

class Solution {
    public int uniqueXorTriplets(int[] nums) {
        Set<Integer> pairXor = new HashSet<>();
        Set<Integer> ans = new HashSet<>();

        int n = nums.length;

        // XOR of all pairs (i <= j)
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                pairXor.add(nums[i] ^ nums[j]);
            }
        }

        // Form triplets using pair XORs
        for (int px : pairXor) {
            for (int num : nums) {
                ans.add(px ^ num);
            }
        }

        return ans.size();
    }
}