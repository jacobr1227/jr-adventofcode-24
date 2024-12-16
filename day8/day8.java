package day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.ArrayList;

public class day8 {
    public static void main(String[] args) {
        File in = new File("day8/inputday8.txt");
        try(Scanner fr = new Scanner(in)) {
            //Setup
            List<String> lines = new ArrayList<>();
            while(fr.hasNextLine()) {
                lines.add(fr.nextLine());
            }
            char[][] mapArray = new char[lines.size()][lines.get(0).length()];
            for(int i=0;i<lines.size();i++) {
                mapArray[i] = lines.get(i).toCharArray();
            }

            List<String> usedNodes = new ArrayList<>();
            List<Character> antennae = new ArrayList<>();
            for(int i=0;i<mapArray.length;i++) {
                for (int j = 0; j < mapArray[i].length; j++) {
                    if (mapArray[i][j] != '.' && !antennae.contains(mapArray[i][j])){
                        antennae.add(mapArray[i][j]);
                        List<int[]> nodes = getNodes(findAntennae(mapArray, mapArray[i][j]), mapArray.length, mapArray[i].length);
                        for(int[] node : nodes) {
                            String n = node[0] + ", " + node[1];
                            if(!usedNodes.contains(n)) {
                                usedNodes.add(n);
                            }
                        }
                    }
                }
            }
            System.out.println(usedNodes.size());
        } catch (FileNotFoundException e) {
            System.err.println("Could not find file! " + e);
        }
    }

    private static List<int[]> findAntennae(char[][] mapArray, char type) {
        List<int[]> matching = new ArrayList<>();
        for(int i=0;i<mapArray.length;i++) {
            for (int j = 0; j < mapArray[i].length; j++) {
                if (mapArray[i][j] == type){
                    matching.add(new int[] {i, j});
                }
            }
        }
        return matching;
    }

    private static List<int[]> getNodes(List<int[]> matching, int yBound, int xBound) {
        List<int[]> nodes = new ArrayList<>();
        for(int[] src : matching) {
            for(int [] dest : matching) {
                if(src != dest && src[0] != -1 && src[1] != -1 && dest[0] != -1 && dest[1] != -1) {
                    int xDiff = dest[1] - src[1];
                    int yDiff = dest[0] - src[0];

                    //Search behind src
                    int x=src[1], y = src[0];
                    while((x - xDiff >= 0 && x - xDiff < xBound) && (y - yDiff >= 0 && y - yDiff < yBound)) {
                        x -= xDiff;
                        y -= yDiff;
                        nodes.add(new int[]{y, x});
                    }
                    //Search ahead of dest
                    x=dest[1];
                    y=dest[0];
                    while((x + xDiff >= 0 && x + xDiff < xBound) && (y + yDiff >= 0 && y + yDiff < yBound)) {
                        y += yDiff;
                        x += xDiff;
                        nodes.add(new int[]{y, x});
                    }
                }
            }
            if(matching.size() > 1) {
                nodes.add(new int[]{src[0], src[1]}); //Every antenna is also a node unless it is the only one.
            }
            matching.set(matching.indexOf(src), new int[] {-1, -1});
        }
        return nodes;
    }
}
