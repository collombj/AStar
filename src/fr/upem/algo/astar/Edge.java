package fr.upem.algo.astar;

import java.util.Objects;

public class Edge {
    public final Vertex source;
    public final Vertex destination;

    public final int weigh;

    public Edge(Vertex source, Vertex destination, int weigh) {
        this.source = Objects.requireNonNull(source);
        this.destination = Objects.requireNonNull(destination);
        this.weigh = weigh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        return weigh == edge.weigh && source.equals(edge.source) && destination.equals(edge.destination);

    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + destination.hashCode();
        result = 31 * result + weigh;
        return result;
    }

    @Override
    public String toString() {
        return source + " --" + weigh + "--> " + destination;
    }
}
