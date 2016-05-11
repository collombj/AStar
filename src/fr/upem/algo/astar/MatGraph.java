
package fr.upem.algo.astar;

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

    private int position(Vertex vertex) {
        int x = vertex.x;
        int y = vertex.y * maxX;

        return x + y;
    }

    private Vertex position(int position) {
        return new Vertex(position % maxX, position / maxX);
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
    public void addEdge(Vertex source, Vertex destination, int weigh) {
        int src = position(source);
        int dst = position(destination);

        graph[src][dst] = weigh;
        numberOfEdge++;
    }

    @Override
    public boolean isEdge(Vertex source, Vertex destination) {
        int src = position(source);
        int dst = position(destination);

        return graph[src][dst] != 0;
    }

    @Override
    public Iterator<Edge> neighborIterator(Vertex origin) {
        int start = position(origin);
        int limit = start + maxX;

        return new Iterator<Edge>() {
            int current = start - 1;

            @Override
            public String toString() {
                return super.toString();
            }

            @Override
            public boolean hasNext() {
                if (current < start) {
                    findNext();
                }
                return current < limit;
            }

            @Override
            public Edge next() {
                if (!hasNext()) {
                    throw new IllegalStateException("No next value");
                }

                Edge edge = new Edge(origin, position(current), graph[start][current]);
                findNext();
                return edge;
            }

            private boolean findNext() {
                for (current += 1; current < limit; current++) {
                    if (graph[start][current] != 0) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    @Override
    public void forEachVertices(Consumer<Vertex> consumer) {
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
