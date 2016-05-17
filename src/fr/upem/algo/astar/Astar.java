package fr.upem.algo.astar;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Astar {
    @Nullable
    public static ShortestPath astar(Graph graph, Vertex start, Vertex target) {
        int verticesQuantity = graph.numberOfVertices();
        int maxY = graph.getMaxY();

        // Distance réel
        int[] g = createIntegerArray(verticesQuantity, Integer.MAX_VALUE);
        g[start.getPosition(maxY)] = 0;
        // Distance simulée avec l'heuristique
        int[] f = createIntegerArray(verticesQuantity, Integer.MAX_VALUE);
        f[start.getPosition(maxY)] = heuristic(start, target);
        // Prédécesseur
        Vertex[] pi = new Vertex[verticesQuantity];

        // Sommets voisin
        List<Vertex> border = new ArrayList<>(8);
        border.add(start);

        // Sommets visités
        List<Vertex> computed = new ArrayList<>(verticesQuantity);

        int weighTotal = -1;
        int step = 0;
        while (!border.isEmpty()) {
            step++;
            Vertex x = findLowerVertexWeight(graph, f, border);

            if (x.equals(target)) {
                weighTotal = f[x.getPosition(maxY)];
                break;
            }

            border.remove(x);

            graph.forEachEdge(x, edge -> {
                Vertex y = edge.destination;

                int positionX = x.getPosition(maxY);
                int positionY = y.getPosition(maxY);

                if (computed.contains(edge.destination)) {
                    if (g[positionY] > g[positionX] + edge.weigh) { // Meilleur chemin
                        g[positionY] = sum(g[positionX], edge.weigh);
                        f[positionY] = sum(g[positionY], heuristic(y, target));
                        pi[positionY] = x;

                        if (!border.contains(y)) {
                            border.add(y);
                        }
                    }
                } else {
                    g[positionY] = sum(g[positionX], edge.weigh);
                    f[positionY] = sum(g[positionY], heuristic(y, target));
                    pi[positionY] = x;

                    border.add(y);
                    computed.add(y);
                }
            });
        }

        if (weighTotal == -1) {
            return null;
        }

        return new ShortestPath(start, target, toPath(graph, start, target, pi, g), weighTotal, step);
    }

    private static List<Edge> toPath(Graph graph, Vertex start, Vertex target, Vertex[] pi, int[] g) {
        LinkedList<Edge> path = new LinkedList<>();

        Vertex current = target;
        while (!current.equals(start)) {
            int currentPosition = current.getPosition(graph.getMaxY());

            Vertex previous = pi[currentPosition];
            int weigh = g[currentPosition] - g[previous.getPosition(graph.getMaxY())];

            path.addFirst(new Edge(previous, current, weigh));

            current = previous;
        }

        return path;
    }

    /**
     * TODO : improve perf
     */
    private static Vertex findLowerVertexWeight(Graph graph, int[] f, List<Vertex> borders) {
        Vertex result = borders.get(0);
        int cost = f[result.getPosition(graph.getMaxY())];

        for (Vertex vertex : borders) {
            System.out.println(vertex + " = " + f[vertex.getPosition(graph.getMaxY())]);
            if (cost > f[vertex.getPosition(graph.getMaxY())]) {
                cost = f[vertex.getPosition(graph.getMaxY())];
                result = vertex;
            }
        }
        System.out.println(result);
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
    private static int heuristic(Vertex source, Vertex destination) {
        int diffX = source.x > destination.x ? source.x - destination.x : destination.x - source.x;
        int diffY = source.y > destination.y ? source.y - destination.y : destination.y - source.y;

        return diffX + diffY;
    }

    @Contract(pure = true)
    private static int sum(int... numbers) {
        int sum = 0;
        for (int number : numbers) {
            if (number == Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            sum += number;
        }
        return sum;
    }

    public static class ShortestPath {
        public final Vertex source;
        public final Vertex target;

        public final List<Edge> path;
        public final int weigh;

        public final int step;

        private ShortestPath(Vertex source, Vertex target, List<Edge> path, int weigh, int step) {
            this.source = source;
            this.target = target;
            this.path = Collections.unmodifiableList(path);
            this.weigh = weigh;
            this.step = step;
        }
    }
}
