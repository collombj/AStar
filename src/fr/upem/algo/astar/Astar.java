package fr.upem.algo.astar;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jeremie on 04/05/2016.
 */
public class Astar {
    @Nullable
    public static ShortestPath astar(Graph graph, Vertex start, Vertex target) {
        int verticesQuantity = graph.numberOfVertices();
        int maxX = graph.getMaxX();
        int maxY = graph.getMaxY();

        // Distance réel
        int[] g = createIntegerArray(verticesQuantity, Integer.MAX_VALUE);
        // Distance simulée avec l'heuristique
        int[] f = createIntegerArray(verticesQuantity, Integer.MAX_VALUE);
        // Prédécesseur
        Vertex[] pi = new Vertex[verticesQuantity];

        // Sommets voisin
        List<Vertex> border = new ArrayList<>(8);
        border.add(start);

        // Sommets visités
        List<Vertex> computed = new ArrayList<>(verticesQuantity);

        int weighTotal = -1;

        while (!border.isEmpty()) {
            Vertex x = findLowerVertexWeight(graph, f, border);

            if (x.equals(target)) {
                weighTotal = f[x.getPosition(maxX)];
                break;
            }
            border.remove(x);

            graph.forEachEdge(x, edge -> {
                Vertex y = edge.destination;

                int positionX = x.getPosition(maxX);
                int positionY = y.getPosition(maxX);

                if (computed.contains(edge.destination)) {
                    if (g[positionY] > g[positionX] + edge.weigh) { // Meilleur chemin
                        g[positionY] = g[positionX] + edge.weigh;
                        f[positionY] = g[positionY] + heuristic(y, target, maxX, maxY);
                        pi[positionY] = x;

                        if (!border.contains(y)) {
                            border.add(y);
                        }
                    }
                } else {
                    g[positionY] = g[positionX] + edge.weigh;
                    f[positionY] = g[positionY] + heuristic(y, target, maxX, maxY);
                    pi[positionY] = x;

                    border.add(y);
                    computed.add(y);
                }
            });
        }

        if (weighTotal == -1) {
            return null;
        }

        return new ShortestPath(start, target, toPath(graph, start, target, pi, g), weighTotal);
    }

    private static List<Edge> toPath(Graph graph, Vertex start, Vertex target, Vertex[] pi, int[] g) {
        LinkedList<Edge> path = new LinkedList<>();

        Vertex current = target;
        while (!current.equals(start)) {
            int currentPosition = current.getPosition(graph.getMaxX());

            Vertex previous = pi[currentPosition];
            int weigh = g[currentPosition] - g[previous.getPosition(graph.getMaxX())];

            path.addFirst(new Edge(previous, current, weigh));

            current = previous;
        }

        return path;
    }

    /**
     * TODO : improve perf
     */
    private static Vertex findLowerVertexWeight(Graph graph, int[] f, List<Vertex> border) {
        Vertex result = border.get(0);
        int cost = f[result.getPosition(graph.getMaxX())];

        for (Vertex vertex : border) {
            if (cost > f[vertex.getPosition(graph.getMaxX())]) {
                cost = f[vertex.getPosition(graph.getMaxX())];
                result = vertex;
            }
        }
        return result;
    }

    @Contract(pure = true)
    private static int[] createIntegerArray(int size, int defaultValue) {
        int[] array = new int[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = defaultValue;
        }
        return array;
    }

    @Contract(pure = true)
    private static int heuristic(Vertex source, Vertex destination, int limitX, int limitY) {
        int result = 0;
        int x = source.x;
        int y = source.y;

        while (destination.x != x && destination.y != y) {
            if (x > destination.x && x > 0) {
                x--;
            } else if (x < destination.x && x < limitX) {
                x++;
            }

            if (y > destination.y && y > 0) {
                y--;
            } else if (y < destination.y && y < limitY) {
                y++;
            }

            result++;
        }

        return result;
    }

    public static class ShortestPath {
        public final Vertex source;
        public final Vertex target;

        public final List<Edge> path;
        public final int weigh;

        private ShortestPath(Vertex source, Vertex target, List<Edge> path, int weigh) {
            this.source = source;
            this.target = target;
            this.path = Collections.unmodifiableList(path);
            this.weigh = weigh;
        }
    }
}
