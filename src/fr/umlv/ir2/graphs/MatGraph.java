package fr.umlv.ir2.graphs;

import java.util.Iterator;

public class MatGraph implements Graph {
    private final int[][] mat;
    private final int n; // number of vertices

    public MatGraph(int size) {
        this.mat = new int[size][size];
        this.n = size;
    }

    @Override
    public int numberOfEdges() {
        int quantity = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] != 0) {
                    quantity++;
                }
            }
        }

        return quantity;
    }

    @Override
    public int numberOfVertices() {
        return n;
    }

    @Override
    public void addEdge(int i, Neighbour j) {
        mat[i][j.getVertex()] = j.getValue();
    }

    @Override
    public boolean isEdge(int i, Neighbour j) {
        return (mat[i][j.getVertex()] != j.getValue());
    }

    @Override
    public Iterator<Neighbour> neighborIterator(int i) {
        return new Iterator<Neighbour>() {
            int j = -1;

            @Override
            public boolean hasNext() {
                if (j == -1) {
                    return findNext();
                }
                return (mat[i][j] != 0);
            }

            @Override
            public Neighbour next() {
                if (!hasNext()) {
                    throw new IllegalStateException("No next value");
                }
                Neighbour neighbour = new Neighbour(j, mat[i][j]);

                findNext();

                return neighbour;
            }

            private boolean findNext() {
                for (j += 1; j < n; j++) {
                    if (mat[i][j] != 0) {
                        return true;
                    }
                }
                return false;
            }
        };
    }
}
