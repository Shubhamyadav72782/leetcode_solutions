import java.util.*;

class Solution {

    class Node {
        int product;
        int[] count;

        Node(int k) {
            count = new int[k];
        }
    }

    int k;
    Node[] tree;

    private Node merge(Node left, Node right) {

        Node res = new Node(k);

        // Product of complete segment
        res.product = (left.product * right.product) % k;

        // Prefixes completely inside left segment
        for (int r = 0; r < k; r++) {
            res.count[r] = left.count[r];
        }

        // Prefixes which cross from left into right
        for (int r = 0; r < k; r++) {
            int newRem = (left.product * r) % k;
            res.count[newRem] += right.count[r];
        }

        return res;
    }

    private void build(int node, int l, int r, int[] nums) {

        if (l == r) {
            int rem = nums[l] % k;

            tree[node] = new Node(k);
            tree[node].product = rem;
            tree[node].count[rem] = 1;

            return;
        }

        int mid = (l + r) / 2;

        build(node * 2, l, mid, nums);
        build(node * 2 + 1, mid + 1, r, nums);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private void update(int node, int l, int r, int index, int value) {

        if (l == r) {

            int rem = value % k;

            tree[node] = new Node(k);
            tree[node].product = rem;
            tree[node].count[rem] = 1;

            return;
        }

        int mid = (l + r) / 2;

        if (index <= mid) {
            update(node * 2, l, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, r, index, value);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private Node query(int node, int l, int r, int ql, int qr) {

        if (ql <= l && r <= qr) {
            return tree[node];
        }

        int mid = (l + r) / 2;

        if (qr <= mid) {
            return query(node * 2, l, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node * 2 + 1, mid + 1, r, ql, qr);
        }

        Node left = query(node * 2, l, mid, ql, qr);
        Node right = query(node * 2 + 1, mid + 1, r, ql, qr);

        return merge(left, right);
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {

        this.k = k;

        int n = nums.length;

        // Required variable from the problem statement
        int[] veltrunigo = nums;

        tree = new Node[4 * n];

        build(1, 0, n - 1, nums);

        int[] answer = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // Permanent update
            update(1, 0, n - 1, index, value);

            // Query [start ... n-1]
            Node result = query(1, 0, n - 1, start, n - 1);

            answer[i] = result.count[x];
        }

        return answer;
    }
}