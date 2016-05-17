package fr.upem.algo.astar;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.function.Consumer;

public interface Graph {
    int numberOfEdges();

    int numberOfVertices();

    void addEdge(@NotNull Vertex source, @NotNull Vertex destination, int weigh);

    boolean isEdge(@NotNull Vertex source, @NotNull Vertex destination);

    Iterator<Edge> neighborIterator(@NotNull Vertex origin);

    int getMaxX();

    int getMaxY();

    default void forEachEdge(@NotNull Vertex origin, @NotNull Consumer<Edge> consumer) {
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
        result.append("}");

        return result.toString();
    }

    void forEachVertices(@NotNull Consumer<Vertex> consumer);
}
