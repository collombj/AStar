package fr.upem.algo.astar;

import java.util.Iterator;
import java.util.function.Consumer;

public interface Graph {
    int numberOfEdges();

    int numberOfVertices();

    void addEdge(Vertex source, Vertex destination, int weigh);

    boolean isEdge(Vertex source, Vertex destination);

    Iterator<Edge> neighborIterator(Vertex origin);

    int getMaxX();

    int getMaxY();

    default void forEachEdge(Vertex origin, Consumer<Edge> consumer) {
        Iterator<Edge> it = this.neighborIterator(origin);
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
    }

    default String toGraphviz() {
        StringBuilder result = new StringBuilder();
        result.append("digraph G {\n");
        forEachVertices(vertex -> {
            forEachEdge(vertex, edge -> {
                result
                        .append(edge.source).append(" -> ").append(edge.destination)
                        .append(" [ label=\"").append(edge.weigh).append("\" ] ; \n");
            });
        });

        return result.toString();
    }

    void forEachVertices(Consumer<Vertex> consumer);
}
