class Solution {
    public int minScore(int n, int[][] roads) {
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] road : roads) {
            int u = road[0], v = road[1], d = road[2];
            graph[u].add(new int[]{v, d});
            graph[v].add(new int[]{u, d});
        }

        boolean[] visited = new boolean[n + 1];
        return dfs(1, graph, visited, Integer.MAX_VALUE);
    }

    private int dfs(int node, List<int[]>[] graph, boolean[] visited, int ans) {
        visited[node] = true;

        for (int[] next : graph[node]) {
            int nei = next[0];
            int dist = next[1];

            ans = Math.min(ans, dist);

            if (!visited[nei]) {
                ans = dfs(nei, graph, visited, ans);
            }
        }

        return ans;
    }
}