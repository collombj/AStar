package fr.umlv.ir2.graphs;

import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Graph {
    int numberOfEdges();

    int numberOfVertices();

    void addEdge(int i, Neighbour j);

    boolean isEdge(int i, Neighbour j);

    Iterator<Neighbour> neighborIterator(int i);

    default void forEachEdge(int i, Consumer<Neighbour> consumer) {
        Iterator<Neighbour> it = this.neighborIterator(i);
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
    }

    default String toGraphviz() {
        StringBuilder result = new StringBuilder();
        result.append("digraph G {\n");
        for (int i = 0; i < numberOfVertices(); i++) {
            int pos = i;
            this.forEachEdge(i, neighbour -> result.append(pos).append(" -> ").append(neighbour.getVertex())
                    .append(" [ label=\"").append(neighbour.getValue()).append("\" ] ; \n"));
        }
        result.append("}");

        return result.toString();
    }

    default void forEachVertices(BiConsumer<Integer, Neighbour> consumer) {
        for (int i = 0; i < numberOfVertices(); i++) {
            int x = i;
            forEachEdge(i, neigh -> {
                consumer.accept(x, neigh);
            });
        }
    }
}
