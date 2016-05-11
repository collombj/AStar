package fr.upem.algo.astar;

import java.util.*;
import java.util.function.Consumer;


public class AdjGraph implements Graph {
    private final Map<Vertex, List<Edge>> graph;
    private final int maxX;
    private final int maxY;
    private final int numberOfVertices;
    private int numberOfEdge = 0;

    public AdjGraph(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        numberOfVertices = maxX * maxY;
        graph = new HashMap<>(numberOfVertices());
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
        List<Edge> edges = graph.get(source);
        if (edges == null) {
            edges = new LinkedList<>();
            graph.put(source, edges);
        }
        edges.add(new Edge(source, destination, weigh));
        numberOfEdge++;
    }

    @Override
    public boolean isEdge(Vertex source, Vertex destination) {
        for (Edge edge : graph.get(source)) {
            if (edge.destination == destination) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Edge> neighborIterator(Vertex origin) {
        List<Edge> edges = graph.get(origin);

        return edges == null ? null : edges.iterator();
    }

    @Override
    public void forEachVertices(Consumer<Vertex> consumer) {
        graph.keySet().forEach(consumer);
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