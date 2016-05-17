package fr.upem.algo.astar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Valentin Griset on 04/05/2016.
 */
public class Parse {
    public static int[][] parseFile(File file) throws IOException {

        int x;
        int y;
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            line = br.readLine();
            x = Integer.valueOf(line);
            line = br.readLine();
            y = Integer.valueOf(line);
            int[][] tab = new int[x][y];
            for (int i = 0; i < x; i++) {
                line = br.readLine();
                String[] lineParse = line.trim().split(" ");
                for (int j = 0; j < lineParse.length; j++) {
                    if(lineParse[j].equals("#")){
                        tab[i][j]=-1;
                    }
                    else{
                        tab[i][j] = Integer.valueOf(lineParse[j]);
                    }
                }
            }
            return tab;
        }
    }
}
