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

        // Distance réel : tous les poids les plus légé entre source et tous les autres points
        int[] g = createIntegerArray(verticesQuantity, Integer.MAX_VALUE);
        g[start.getPosition(maxY)] = 0;
        // Distance simulée avec l'heuristique
        int[] f = createIntegerArray(verticesQuantity, Integer.MAX_VALUE);
        f[start.getPosition(maxY)] = heuristic(start, target);
        // Prédécesseur
        Vertex[] pi = new Vertex[verticesQuantity];

        // Sommets voisin
        List<Vertex> border = new ArrayList<>();
        border.add(start);

        // Sommets visités
        List<Vertex> visited = new ArrayList<>(verticesQuantity);

        int weighTotal = -1;
        int step = 0;
        while (!border.isEmpty()) {
            step++;
            Vertex x = findLowerVertexWeight(graph, f, border); //Sommet courant

            if (x.equals(target)) { //On s'arrete quand on arrive à la destination
                weighTotal = g[x.getPosition(maxY)];
                break;
            }

            border.remove(x); //On retire de nos voisins le sommet courant

            graph.forEachEdge(x, edge -> { // pour chaque voisins de notre sommet courant
                Vertex y = edge.destination; // récupère le voisin accessible

                int positionX = x.getPosition(maxY);//position du sommet courant
                int positionY = y.getPosition(maxY);//position du sommet voisin accessible

                if (visited.contains(y)) { //si mon tabeau de sommet visité contient notre voisin
                    if (g[positionY] > g[positionX] + edge.weigh) { //Si mon poids pour aller à mon voisin est plus légé que mon poids actuel avec le poids de mon voisin
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
                    visited.add(y);
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

    private static Vertex findLowerVertexWeight(Graph graph, int[] f, List<Vertex> borders) {
        Vertex result = borders.get(0);
        int cost = f[result.getPosition(graph.getMaxY())];

        for (Vertex vertex : borders) {
            if (cost > f[vertex.getPosition(graph.getMaxY())]) {
                cost = f[vertex.getPosition(graph.getMaxY())];
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
