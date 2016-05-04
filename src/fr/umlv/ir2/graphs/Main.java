package fr.umlv.ir2.graphs;


import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Valentin Griset on 04/05/2016.
 */
public class Main {


    public static void main(String[] args) {


        if (args.length != 3 && args.length != 1 ){
            usage_args1();
        }

        if (args.length == 3){
            //TODO calcule du chemin le plus cours entre les 2 cases donnée en paramètres
        }
        //TODO calcule avec les coordonnée 0 0 et cherchant les chemins les plus cours vers les 3 coins
        File file = new File(args[0]);
        int[][] tab = Parse.parseFile(file);

        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j <tab[i].length ; j++) {
                System.out.print(" "+tab[i][j]);
            }
            System.out.println();
        }
    }
    private static void usage_args1(){
        System.out.println("Astar + file  ");
    }


}
