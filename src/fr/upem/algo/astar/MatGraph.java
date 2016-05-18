
package fr.upem.algo.astar;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.function.Consumer;

public class MatGraph implements Graph {

    private final int[][] graph;

    private final int maxX;
    private final int maxY;
    private final int numberOfVertices;
    private int numberOfEdge = 0;

    public MatGraph(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        numberOfVertices = maxX * maxY;

        graph = new int[numberOfVertices][numberOfVertices];
    }

    @Contract(pure = true)
    private int position(@NotNull Vertex vertex) {
        return vertex.x * maxY + vertex.y;
    }

    @Contract("_ -> !null")
    private Vertex position(int position) {
        return new Vertex(position / maxY, position % maxY);
    }

    @Override
    public int numberOfEdges() {
        return numberOfEdge;
    }

    @Override
    public int numberOfVertices() {
        return numberOfVertices;
    }

    @Override
    public void addEdge(@NotNull Vertex source, @NotNull Vertex destination, int weigh) {
        int src = position(source);
        int dst = position(destination);

        graph[src][dst] = weigh;
        numberOfEdge++;
    }

    @Override
    public boolean isEdge(@NotNull Vertex source, @NotNull Vertex destination) {
        int src = position(source);
        int dst = position(destination);

        return graph[src][dst] != 0;
    }

    @Override
    public Iterator<Edge> neighborIterator(@NotNull Vertex origin) {

        return new Iterator<Edge>() {
            int vertex = origin.getPosition(maxY);
            int current = 0;
            int limit = graph.length - maxY;

            @Override
            public boolean hasNext() {
                while (graph[vertex][current] <= 0 && current < limit) {
                    current++;
                }

                return current < limit;
            }

            @Override
            public Edge next() {
                if (!hasNext()) {
                    throw new IllegalStateException("No next value");
                }

                Edge edge = new Edge(origin, position(current), graph[vertex][current]);
                current++;
                return edge;
            }
        };
    }

    @Override
    public void forEachVertices(@NotNull Consumer<Vertex> consumer) {
        for (int i = 0; i < numberOfVertices; i++) {
            consumer.accept(position(i));
        }
    }

    @Override
    public int getMaxX() {
        return maxX;
    }

    @Override
    public int getMaxY() {
        return maxY;
    }
}
