class Solution {

    int m, n;
    boolean[][] visited;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public boolean containsCycle(char[][] grid) {

        m = grid.length;
        n = grid[0].length;

        visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (!visited[i][j]) {
                    if (dfs(grid, i, j, -1, -1)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean dfs(char[][] grid, int r, int c,
                        int parentR, int parentC) {

        visited[r][c] = true;

        for (int d = 0; d < 4; d++) {

            int nr = r + dr[d];
            int nc = c + dc[d];

            // Out of bounds
            if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                continue;
            }

            // Different character
            if (grid[nr][nc] != grid[r][c]) {
                continue;
            }

            // Don't go immediately back to parent
            if (nr == parentR && nc == parentC) {
                continue;
            }

            // Already visited -> cycle
            if (visited[nr][nc]) {
                return true;
            }

            if (dfs(grid, nr, nc, r, c)) {
                return true;
            }
        }

        return false;
    }
}