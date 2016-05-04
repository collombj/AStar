package fr.umlv.ir2.graphs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jeremie on 24/03/2016.
 */
public class AdjGraph implements Graph {
    private final List<List<Neighbour>> adj;
    private final int n;

    public AdjGraph(int size) {
        adj = new ArrayList<>(size);
        n = size;
    }

    @Override
    public int numberOfEdges() {
        int result = 0;
        for (List<Neighbour> neighbours : adj) {
            if (neighbours != null) {
                result += neighbours.size();
            }
        }
        return result;
    }

    @Override
    public int numberOfVertices() {
        return n;
    }

    @Override
    public void addEdge(int i, Neighbour j) {
        List<Neighbour> edges = adj.get(i);
        if (edges == null) {
            edges = new LinkedList<>();
            adj.add(i, edges);
        }

        edges.add(j);
    }

    @Override
    public boolean isEdge(int i, Neighbour j) {
        List<Neighbour> edges = adj.get(i);
        if (edges != null) {
            for (Neighbour edge : edges) {
                if (edge.equals(j)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public Iterator<Neighbour> neighborIterator(int i) {
        return new Iterator<Neighbour>() {
            Iterator<Neighbour> it;

            @Override
            public boolean hasNext() {
                if (it == null) {
                    List<Neighbour> lst = adj.get(i);
                    if (lst == null) {
                        return false;
                    }
                    it = lst.iterator();
                }

                return it.hasNext();
            }

            @Override
            public Neighbour next() {
                return it.next();
            }
        };
    }
}
