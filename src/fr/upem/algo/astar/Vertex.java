package fr.upem.algo.astar;

public class Vertex {
    public final int x;
    public final int y;

    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vertex(Vertex vertex) {
        x = vertex.x;
        y = vertex.y;
    }

    public int getPosition(int maxY) {
        return x * maxY + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        return x == vertex.x && y == vertex.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
