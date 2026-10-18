import java.util.*;

class Solution {
    private Map<Integer, List<Integer>> graph;
    private int target;
    private int t;

    public double frogPosition(int n, int[][] edges, int t, int target) {
        this.target = target;
        this.t = t;

        // Special case: single node
        if (n == 1) {
            return target == 1 ? 1.0 : 0.0;
        }

        // Build adjacency list (1-indexed)
        graph = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            graph.put(i, new ArrayList<>());
        }
        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }

        return dfs(1, -1, 0, 1.0);
    }

    private double dfs(int node, int parent, int time, double prob) {
        // At exactly time t: either we're here or not
        if (time == t) {
            return node == target ? prob : 0.0;
        }

        // Find unvisited neighbors (all except parent)
        List<Integer> neighbors = new ArrayList<>();
        for (int v : graph.get(node)) {
            if (v != parent) {
                neighbors.add(v);
            }
        }

        // Dead end (leaf): frog stays here forever
        if (neighbors.isEmpty()) {
            return node == target ? prob : 0.0;
        }

        // Jump uniformly to each unvisited neighbor
        double res = 0.0;
        double nextProb = prob / neighbors.size();
        for (int v : neighbors) {
            res += dfs(v, node, time + 1, nextProb);
        }
        return res;
    }
}