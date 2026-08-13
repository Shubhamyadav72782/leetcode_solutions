class Solution {

    int[] left, right, best;
    char[] s;

    public int[] longestRepeating(String str, String queryCharacters, int[] queryIndices) {
        int n = str.length();
        s = str.toCharArray();

        left = new int[4 * n];
        right = new int[4 * n];
        best = new int[4 * n];

        build(1, 0, n - 1);

        int q = queryIndices.length;
        int[] ans = new int[q];

        for (int i = 0; i < q; i++) {
            int index = queryIndices[i];
            char ch = queryCharacters.charAt(i);

            update(1, 0, n - 1, index, ch);

            ans[i] = best[1];
        }

        return ans;
    }

    void build(int node, int l, int r) {
        if (l == r) {
            left[node] = right[node] = best[node] = 1;
            return;
        }

        int mid = (l + r) / 2;

        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);

        merge(node, l, r);
    }

    void update(int node, int l, int r, int idx, char ch) {
        if (l == r) {
            s[idx] = ch;
            left[node] = right[node] = best[node] = 1;
            return;
        }

        int mid = (l + r) / 2;

        if (idx <= mid) {
            update(node * 2, l, mid, idx, ch);
        } else {
            update(node * 2 + 1, mid + 1, r, idx, ch);
        }

        merge(node, l, r);
    }

    void merge(int node, int l, int r) {
        int mid = (l + r) / 2;

        int leftNode = node * 2;
        int rightNode = node * 2 + 1;

        left[node] = left[leftNode];
        right[node] = right[rightNode];

        best[node] = Math.max(best[leftNode], best[rightNode]);

        // If boundary characters are same, combine them
        if (s[mid] == s[mid + 1]) {
            best[node] = Math.max(
                best[node],
                right[leftNode] + left[rightNode]
            );

            // Entire left part has same character
            if (left[leftNode] == mid - l + 1) {
                left[node] += left[rightNode];
            }

            // Entire right part has same character
            if (right[rightNode] == r - mid) {
                right[node] += right[leftNode];
            }
        }
    }
}
