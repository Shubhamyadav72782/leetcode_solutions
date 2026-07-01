import java.util.*;

class Solution {
    public int maximumSafenessFactor(List<List<Integer>> grid) {
        int n = grid.size();

        // Step 1: Compute distance to nearest thief using Multi-Source BFS
        int[][] dist = new int[n][n];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        Queue<int[]> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) == 1) {
                    dist[i][j] = 0;
                    queue.offer(new int[]{i, j});
                }
            }
        }

        int[] dir = {0, 1, 0, -1, 0};

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0];
            int y = curr[1];

            for (int k = 0; k < 4; k++) {
                int nx = x + dir[k];
                int ny = y + dir[k + 1];

                if (nx >= 0 && ny >= 0 && nx < n && ny < n &&
                    dist[nx][ny] > dist[x][y] + 1) {

                    dist[nx][ny] = dist[x][y] + 1;
                    queue.offer(new int[]{nx, ny});
                }
            }
        }

        // Step 2: Max Heap to find the safest path
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);

        int[][] best = new int[n][n];
        for (int[] row : best) {
            Arrays.fill(row, -1);
        }

        pq.offer(new int[]{dist[0][0], 0, 0});
        best[0][0] = dist[0][0];

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();

            int safe = curr[0];
            int x = curr[1];
            int y = curr[2];

            if (x == n - 1 && y == n - 1) {
                return safe;
            }

            if (safe < best[x][y]) continue;

            for (int k = 0; k < 4; k++) {
                int nx = x + dir[k];
                int ny = y + dir[k + 1];

                if (nx >= 0 && ny >= 0 && nx < n && ny < n) {
                    int newSafe = Math.min(safe, dist[nx][ny]);

                    if (newSafe > best[nx][ny]) {
                        best[nx][ny] = newSafe;
                        pq.offer(new int[]{newSafe, nx, ny});
                    }
                }
            }
        }

        return 0;
    }
}