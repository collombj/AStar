package fr.upem.algo.astar;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


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


        if (x1 < 0 || x1 >= tab.length) {
            x1 = 0;
        }
        if (y1 < 0 || y1 >= tab[0].length) {
            y1 = 0;
        }
        if (x2 < 0 || x2 >= tab.length) {
            x2 = tab.length - 1;
        }
        if (y2 < 0 || y2 >= tab[0].length) {
            y2 = tab[0].length - 1;
        }

        Vertex origin = new Vertex(x1, y1);
        Vertex target = new Vertex(x2, y2);

        displayGame(tab);

        Graph game = toGraph(tab);

        Astar.ShortestPath res = Astar.astar(game, origin, target);

        if (res == null) {
            System.err.println("Pas de chemin trouvé pour aller de " + origin + " à " + target);
            return;
        }

        System.out.println("Astar: " + res.step + " étapes");
        System.out.println("Chemin de lg. " + res.weigh + " allant de " + res.source + " à " + res.target);
        res.path.forEach(edge -> System.out.print(edge.source + " -> "));
        System.out.print(res.target);
        System.out.println();
    }

    private static void displayGame(int[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
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
        int maxX = tab.length;
        int maxY = tab[0].length;

        Graph g = new AdjGraph(maxX + 1, maxY + 1);

        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                Vertex org = new Vertex(x, y);
                if (tab[x][y] <= 0) {
                    continue;
                }

                // Est
                if (y + 1 < maxY && tab[x][y + 1] > 0) {
                    g.addEdge(org, new Vertex(x, y + 1), tab[x][y + 1]);
                }

                // Sud
                if (x + 1 < maxX && tab[x + 1][y] > 0) {
                    g.addEdge(org, new Vertex(x + 1, y), tab[x + 1][y]);
                }

                // Ouest
                if (y - 1 >= 0 && tab[x][y - 1] > 0) {
                    g.addEdge(org, new Vertex(x, y - 1), tab[x][y - 1]);
                }

                // Nord
                if (x - 1 >= 0 && tab[x - 1][y] > 0) {
                    g.addEdge(org, new Vertex(x - 1, y), tab[x - 1][y]);
                }

                // Sud - Est
                if (x + 1 < maxX && y + 1 < maxY && tab[x + 1][y + 1] > 0) {
                    g.addEdge(org, new Vertex(x + 1, y + 1), tab[x + 1][y + 1]);
                }
                // Sud - Ouest
                if (x + 1 < maxX && y - 1 >= 0 && tab[x + 1][y - 1] > 0) {
                    g.addEdge(org, new Vertex(x + 1, y - 1), tab[x + 1][y - 1]);
                }

                // Nord - Est
                if (x - 1 >= 0 && y + 1 < maxY && tab[x - 1][y + 1] > 0) {
                    g.addEdge(org, new Vertex(x - 1, y + 1), tab[x - 1][y + 1]);
                }
                // Nord - Ouest
                if (x - 1 >= 0 && y - 1 >= 0 && tab[x - 1][y - 1] > 0) {
                    g.addEdge(org, new Vertex(x - 1, y - 1), tab[x - 1][y - 1]);
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
