/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {

        int first = -1;      // First critical point
        int last = -1;       // Previous critical point

        int minDist = Integer.MAX_VALUE;
        int maxDist = 0;

        int index = 1;

        ListNode prev = head;
        ListNode curr = head.next;

        while (curr.next != null) {

            // Check local maximum or local minimum
            boolean critical =
                (curr.val > prev.val && curr.val > curr.next.val) ||
                (curr.val < prev.val && curr.val < curr.next.val);

            if (critical) {

                // First critical point
                if (first == -1) {
                    first = index;
                } else {
                    // Distance from previous critical point
                    minDist = Math.min(minDist, index - last);

                    // Distance from first critical point
                    maxDist = index - first;
                }

                last = index;
            }

            prev = curr;
            curr = curr.next;
            index++;
        }

        // Fewer than 2 critical points
        if (first == last) {
            return new int[]{-1, -1};
        }

        return new int[]{minDist, maxDist};
    }
}