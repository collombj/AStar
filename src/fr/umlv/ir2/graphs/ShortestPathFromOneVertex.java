package fr.umlv.ir2.graphs;

import java.util.Arrays;

public class ShortestPathFromOneVertex {
    private final int source;
    private final int[] d;
    private final int[] pi;

    ShortestPathFromOneVertex(int source, int[] d, int[] pi) {
        this.source = source;
        this.d = d;
        this.pi = pi;
    }

    public void printShortestPathTo(int destination) {
        if (destination == -1) {
            throw new IllegalArgumentException("No way to go to");
        }

        if (destination == source) {
            System.out.printf(Integer.toString(destination));
            return;
        }
        printShortestPathTo(pi[destination]);
        System.out.printf(" -> " + destination);
    }

    public void printShortestPaths() {
        for (int i = 0; i < d.length; i++) {
            if (i == source) {
                continue;
            }
            printShortestPathTo(i);
        }
    }

    @Override
    public String toString() {
        return source + " " + Arrays.toString(d) + " " + Arrays.toString(pi);
    }
}
