package fr.umlv.ir2.graphs;

import java.util.*;


public final class Graphs {

    private static <V> V[] createVisitedArray(int vertices, V defaultValue) {
        @SuppressWarnings("unchecked") V[] result = (V[]) new Object[vertices];

        for (int i = 0; i < result.length; i++) {
            result[i] = defaultValue;
        }

        return result;
    }

    // Parcours en profondeur
    public static List<Integer> dfs(Graph g, int s0) {
        Boolean[] visited =
                Graphs.<Boolean>createVisitedArray(g.numberOfVertices(), false);
        List<Integer> result = new ArrayList<>(g.numberOfVertices());

        visited[s0] = true;
        result.add(s0);
        g.forEachEdge(s0, neighbour -> {
            if (!visited[neighbour.getVertex()]) {
                dfs(g, neighbour.getVertex(), visited, result);
            }
        });

        return result;
    }

    private static void dfs(Graph g, int vertex, Boolean[] visited, List<Integer> result) {
        visited[vertex] = true;
        result.add(vertex);

        g.forEachEdge(vertex, neighbour -> {
            if (!visited[neighbour.getVertex()]) {
                dfs(g, neighbour.getVertex(), visited, result);
            }
        });
    }

    // Parcours en largeur
    public static List<Integer> bfs(Graph g, int s0) {
        List<Integer> result = new ArrayList<>(g.numberOfVertices());
        Queue<Integer> queue = new ArrayDeque<>(g.numberOfVertices());
        Boolean[] visited = Graphs.<Boolean>createVisitedArray(g.numberOfVertices(), false);

        queue.add(s0);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            if (visited[vertex]) {
                continue;
            }

            result.add(vertex);
            g.forEachEdge(vertex, neighbour -> queue.add(neighbour.getVertex()));
        }

        return result;
    }

    public static TimedVertix[] timedDepthFirstSearch(Graph g, int s0) {
        Countdown counter = new Countdown();

        TimedVertix[] result = (TimedVertix[]) new Object[g.numberOfVertices()];
        for (int i = 0; i < result.length; i++) {
            result[i] = new TimedVertix(0, 0);
        }

        g.forEachEdge(s0, neighbour -> {
            if (result[neighbour.getVertex()].start == 0) {
                timedDepthFirstSearch(g, neighbour.getVertex(), result, counter);
            }
        });

        return result;
    }

    private static void timedDepthFirstSearch(Graph g, int vertex, TimedVertix[] timedVertices, Countdown counter) {
        timedVertices[vertex].start = counter.count;
        counter.count++;

        g.forEachEdge(vertex, neighbour -> {
            if (timedVertices[neighbour.getVertex()].start == 0) {
                timedDepthFirstSearch(g, neighbour.getVertex(), timedVertices, counter);
            }
        });

        timedVertices[vertex].end = counter.count;
        counter.count++;
    }

    public static List<Integer> topologicalSort(Graph g, boolean cycleDetected) {
        // TODO
        return null;
    }

    public static ShortestPathFromOneVertex bellmanFord(Graph g, int source) {
        int vertices = g.numberOfVertices();
        int[] dist = new int[vertices];
        int[] previous = new int[vertices];

        for (int i = 0; i < vertices; i++) {
            dist[i] = Integer.MAX_VALUE;
            previous[i] = -1;
        }

        dist[source] = 0;

        for (int l = 1; l <= vertices - 1; l++) {
            g.forEachVertices((x, y) -> {
                if (dist[x] != Integer.MAX_VALUE && dist[y.getVertex()] > dist[x] + y.getValue()) {
                    dist[y.getVertex()] = dist[x] + y.getValue();
                    previous[y.getVertex()] = x;
                }
            });
        }

        g.forEachVertices((x, y) -> {
            if (dist[x] != Integer.MAX_VALUE && dist[y.getVertex()] > dist[x] + y.getValue()) {
                throw new IllegalStateException("Edge with negative value");
            }
        });

        return new ShortestPathFromOneVertex(source, dist, previous);
    }

    public static ShortestPathFromOneVertex dijkstra(Graph g, int source) {
        int nbVertices = g.numberOfVertices();
        int[] d = new int[nbVertices];
        int[] p = new int[nbVertices];
        BitSet f = new BitSet(nbVertices);
        int fSize = nbVertices;

        for (int i = 0; i < nbVertices; i++) {
            d[i] = Integer.MAX_VALUE;
            p[i] = -1;
        }

        d[source] = 0;

        while (fSize > 0) {
            int x = f.nextClearBit(0);
            f.set(x);
            fSize--;

            g.forEachEdge(x, y -> {
                if (d[x] != Integer.MAX_VALUE && d[y.getVertex()] > d[x] + y.getValue()) {
                    d[y.getVertex()] = d[x] + y.getValue();
                    p[y.getVertex()] = x;
                }
            });
        }

        return new ShortestPathFromOneVertex(source, d, p);
    }

    public static ShortestPathFromAllVertices floydWarshall(Graph g) {
        int nbVertices = g.numberOfVertices();
        int[][] d = new int[nbVertices][nbVertices];
        int[][] p = new int[nbVertices][nbVertices];

        return new ShortestPathFromAllVertices(d, p);
    }

    private static class Countdown {
        int count = 0;
    }

    public static class TimedVertix {
        int start;
        int end;

        TimedVertix(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
