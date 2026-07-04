
//DFS



/*class Solution {
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
}*/





//BFS




class Solution {
    public int minScore(int n, int[][] roads) {
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] road : roads) {
            graph[road[0]].add(new int[]{road[1], road[2]});
            graph[road[1]].add(new int[]{road[0], road[2]});
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n + 1];

        queue.offer(1);
        visited[1] = true;

        int ans = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            int cur = queue.poll();

            for (int[] edge : graph[cur]) {
                int next = edge[0];
                int dist = edge[1];

                ans = Math.min(ans, dist);

                if (!visited[next]) {
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }

        return ans;
    }
}