package fr.upem.algo.astar;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Valentin Griset on 04/05/2016.
 */
public class Main {
    public static void main(String[] args) {
        int x1 = 0;
        int y1 = 0;

        int x2 = -1;
        int y2 = -1;

        if (args.length != 5 && args.length != 1) {
            usage();
            return;
        }

        File file = new File(args[0]);
        if (args.length == 5) {
            x1 = Integer.parseInt(args[1]);
            y1 = Integer.parseInt(args[2]);

            x2 = Integer.parseInt(args[3]);
            y2 = Integer.parseInt(args[4]);
        }

        int[][] tab;

        try {
            tab = Parse.parseFile(file);
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            return;
        } catch (IOException e) {
            System.err.println(e.toString());
            return;
        }


        if (x2 == -1 || x2 >= tab.length) {
            x2 = tab[0].length;
        }
        if (y2 == -1 || y2 >= tab[0].length) {
            y2 = tab[0].length;
        }

        displayGame(tab);

        Graph game = toGraph(tab);

        Astar.ShortestPath res = Astar.astar(game, new Vertex(x1, y1), new Vertex(x2, y2));
        if (res == null) {
            System.err.println("No path found");
            return;
        }

        System.out.println("Chemin de lg. " + res.weigh + " allant de " + res.source + " à " + res.target);
        res.path.forEach(edge -> System.out.print(edge.source + " -> "));
        System.out.print(res.target);
        System.out.println();
    }

    private static void displayGame(int[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j <tab[i].length ; j++) {
                if (tab[i][j] == -1) {
                    System.out.print("# ");
                } else {
                    System.out.print(tab[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    private static Graph toGraph(int[][] tab) {
        int maxX = tab.length - 1;
        int maxY = tab[0].length - 1;
        Graph g = new MatGraph(maxX + 1, maxY + 1);

        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if (tab[i][j] == -1) {
                    continue;
                }

                Vertex current = new Vertex(i, j);

                // Nord
                if (i > 0 && tab[i - 1][j] != -1) {
                    g.addEdge(current, new Vertex(i - 1, j), tab[i - 1][j]);
                }
                // Ouest
                if (j > 0 && tab[i][j - 1] != -1) {
                    g.addEdge(current, new Vertex(i, j - 1), tab[i][j - 1]);
                }

                // Sud
                if (i < maxX && tab[i + 1][j] != -1) {
                    g.addEdge(current, new Vertex(i + 1, j), tab[i + 1][j]);
                }
                // Est
                if (j > maxY && tab[i][j + 1] != -1) {
                    g.addEdge(current, new Vertex(i, j + 1), tab[i][j + 1]);
                }

                // Nord-Ouest
                if (i > 0 && j > 0 && tab[i - 1][j - 1] != -1) {
                    g.addEdge(current, new Vertex(i - 1, j - 1), tab[i - 1][j - 1]);
                }
                // Nord-Est
                if (i > 0 && j < maxY && tab[i - 1][j + 1] != -1) {
                    g.addEdge(current, new Vertex(i - 1, j + 1), tab[i - 1][j + 1]);
                }

                // Sud-Ouest
                if (i > maxX && j > 0 && tab[i + 1][j - 1] != -1) {
                    g.addEdge(current, new Vertex(i + 1, j - 1), tab[i + 1][j - 1]);
                }
                // Sud-Est
                if (i > maxX && j < maxY && tab[i + 1][j + 1] != -1) {
                    g.addEdge(current, new Vertex(i + 1, j + 1), tab[i + 1][j + 1]);
                }
            }
        }

        return g;
    }

    private static void usage() {
        System.out.println("USAGE:");
        System.out.println("\t- FILE");
        System.out.println("\t- FILE X1 Y1 X2 Y2");
    }


}
