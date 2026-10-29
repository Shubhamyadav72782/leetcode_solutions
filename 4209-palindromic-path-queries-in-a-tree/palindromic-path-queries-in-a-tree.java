
import java.util.*;

class Solution {
    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        int LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        int[] parent = new int[n];
        int[] depth = new int[n];
        int[] tin = new int[n];
        int[] tout = new int[n];
        int[] order = new int[n];

        Arrays.fill(parent, -1);

        // Iterative DFS
        int[] stack = new int[n];
        int top = 0, timer = 0;
        stack[top++] = 0;
        parent[0] = 0;

        while (top > 0) {
            int u = stack[--top];
            tin[u] = timer;
            order[timer++] = u;

            for (int v : graph[u]) {
                if (v == parent[u]) continue;

                parent[v] = u;
                depth[v] = depth[u] + 1;
                stack[top++] = v;
            }
        }

        // Calculate subtree sizes and exit times
        int[] size = new int[n];
        Arrays.fill(size, 1);

        for (int i = n - 1; i > 0; i--) {
            int u = order[i];
            size[parent[u]] += size[u];
        }

        for (int u = 0; u < n; u++) {
            tout[u] = tin[u] + size[u] - 1;
        }

        // Binary lifting table
        int[][] up = new int[LOG][n];

        for (int u = 0; u < n; u++) {
            up[0][u] = parent[u];
        }

        for (int j = 1; j < LOG; j++) {
            for (int u = 0; u < n; u++) {
                up[j][u] = up[j - 1][up[j - 1][u]];
            }
        }

        char[] chars = s.toCharArray();
        Fenwick bit = new Fenwick(n);

        // XOR each character over its subtree range
        for (int u = 0; u < n; u++) {
            int mask = 1 << (chars[u] - 'a');
            bit.rangeXor(tin[u], tout[u], mask);
        }

        List<Boolean> answer = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int delta = (1 << (chars[u] - 'a'))
                          ^ (1 << (c - 'a'));

                if (delta != 0) {
                    bit.rangeXor(tin[u], tout[u], delta);
                    chars[u] = c;
                }
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int ancestor = lca(u, v, depth, up);

                int mask = bit.query(tin[u])
                         ^ bit.query(tin[v])
                         ^ (1 << (chars[ancestor] - 'a'));

                // At most one character has odd frequency
                answer.add((mask & (mask - 1)) == 0);
            }
        }

        return answer;
    }

    private int lca(int u, int v, int[] depth, int[][] up) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < up.length; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) return u;

        for (int j = up.length - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return up[0][u];
    }

    static class Fenwick {
        int n;
        int[] tree;

        Fenwick(int n) {
            this.n = n;
            tree = new int[n + 1];
        }

        void update(int index, int mask) {
            for (int i = index + 1; i <= n; i += i & -i) {
                tree[i] ^= mask;
            }
        }

        void rangeXor(int left, int right, int mask) {
            update(left, mask);

            if (right + 1 < n) {
                update(right + 1, mask);
            }
        }

        int query(int index) {
            int result = 0;

            for (int i = index + 1; i > 0; i -= i & -i) {
                result ^= tree[i];
            }

            return result;
        }
    }
}
