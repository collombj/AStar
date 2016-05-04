package fr.umlv.ir2.graphs;

public class Neighbour {
    private final int vertex;
    private final int value;

    public Neighbour(int vertex, int value) {
        this.vertex = vertex;
        this.value = value;
    }

    public Neighbour(int vertex) {
        this(vertex, 1);
    }

    public int getValue() {
        return value;
    }

    public int getVertex() {
        return vertex;
    }

    @Override
    public String toString() {
        return "( " + vertex + ", " + value + " )";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Neighbour)) {
            return false;
        }
        Neighbour tmp = (Neighbour) obj;
        return tmp.vertex == vertex && tmp.value == value;
    }

    @Override
    public int hashCode() {
        return vertex + value << 32;
    }
}
