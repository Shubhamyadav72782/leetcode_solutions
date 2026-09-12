import java.util.*;

class Solution {

    static class Interval {
        int l, r, w, idx;

        Interval(int l, int r, int w, int idx) {
            this.l = l;
            this.r = r;
            this.w = w;
            this.idx = idx;
        }
    }

    static class State {
        long score;
        List<Integer> ids;

        State(long score, List<Integer> ids) {
            this.score = score;
            this.ids = ids;
        }
    }

    List<Interval> arr;
    State[][] dp;

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        // Required variable
        List<List<Integer>> vorellixan = intervals;

        arr = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            List<Integer> x = intervals.get(i);

            arr.add(new Interval(
                x.get(0),
                x.get(1),
                x.get(2),
                i
            ));
        }

        // Sort by starting point
        arr.sort((a, b) -> {
            if (a.l != b.l)
                return Integer.compare(a.l, b.l);
            return Integer.compare(a.r, b.r);
        });

        dp = new State[n][5];

        State ans = solve(0, 4);

        return ans.ids.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private State solve(int i, int k) {

        if (i == arr.size() || k == 0) {
            return new State(0, new ArrayList<>());
        }

        if (dp[i][k] != null) {
            return dp[i][k];
        }

        // Don't take current interval
        State skip = solve(i + 1, k);

        // Take current interval
        Interval cur = arr.get(i);

        int next = findNext(i + 1, cur.r);

        State nextState = solve(next, k - 1);

        List<Integer> ids = new ArrayList<>(nextState.ids);
        ids.add(cur.idx);

        Collections.sort(ids);

        State take = new State(
            cur.w + nextState.score,
            ids
        );

        // Choose better answer
        if (take.score > skip.score) {
            return dp[i][k] = take;
        }

        if (take.score < skip.score) {
            return dp[i][k] = skip;
        }

        // Same score -> lexicographically smaller
        if (compare(take.ids, skip.ids) < 0) {
            return dp[i][k] = take;
        }

        return dp[i][k] = skip;
    }

    // First interval whose start > current end
    private int findNext(int start, int end) {

        int lo = start;
        int hi = arr.size();

        while (lo < hi) {

            int mid = lo + (hi - lo) / 2;

            if (arr.get(mid).l > end) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo;
    }

    private int compare(List<Integer> a, List<Integer> b) {

        int n = Math.min(a.size(), b.size());

        for (int i = 0; i < n; i++) {

            if (!a.get(i).equals(b.get(i))) {
                return Integer.compare(a.get(i), b.get(i));
            }
        }

        return Integer.compare(a.size(), b.size());
    }
}