package fr.umlv.ir2.graphs;

import java.io.*;

/**
 * Created by Valentin Griset on 04/05/2016.
 */
public class Parse {


    public static int[][] parseFile(File file) {

        int x;
        int y;
        String line;
        String split = " ";
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
        } catch (IOException e ) {
            e.printStackTrace();
            return null;
        }
    }
}
