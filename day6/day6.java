package day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TODO: ANS between 1600 and 1900.
//1907 is original answer.
//1528 produced by causing loop indicators to go ahead and move to the next location without checking (breaks on corners)
//1499 is next answer, still wrong.
public class day6 {

    public static void main(String[] args) {
        int possiblePositions = 0;
        String startDirection = "";
        File in = new File("day6/inputday6.text");
        try (Scanner fr = new Scanner(in)) {
            List<String> map = new ArrayList<>();
            while (fr.hasNextLine()) {
                String line = fr.nextLine();
                map.add(line);
            }
            char[][] mapArray = new char[map.size()][map.get(0).length()];
            char[][] initMap = new char[map.size()][map.get(0).length()];
            int[] guardLocation = new int[2];
            String guardDirection = "";
            boolean inMap = true;
            for (int i = 0; i < map.size(); i++) {
                initMap[i] = map.get(i).toCharArray();
                mapArray[i] = map.get(i).toCharArray();
                for (int j = 0; j < mapArray[i].length; j++) {
                    if (mapArray[i][j] == '^') {
                        guardLocation = new int[]{i, j};
                        guardDirection = "UP";
                    } else if (mapArray[i][j] == '>') {
                        guardLocation = new int[]{i, j};
                        guardDirection = "RIGHT";
                    } else if (mapArray[i][j] == 'v') {
                        guardLocation = new int[]{i, j};
                        guardDirection = "DOWN";
                    } else if (mapArray[i][j] == '<') {
                        guardLocation = new int[]{i, j};
                        guardDirection = "LEFT";
                    }
                }
            }
            //part 1
            startDirection = guardDirection;
            int i = guardLocation[0], j = guardLocation[1];
            int distinct = 0;
            while (inMap) {
                if (mapArray[i][j] != 'X' && mapArray[i][j] != 'T') {
                    mapArray[i][j] = 'X';
                    distinct++;
                }
                switch (guardDirection) {
                    case "UP" -> {
                        if (i == 0) {
                            inMap = false;
                            break;
                        }
                        if (mapArray[i - 1][j] == '#') {
                            guardDirection = "RIGHT";
                            mapArray[i][j] = 'T';
                        } else {
                            i--;
                        }
                    }
                    case "LEFT" -> {
                        if (j == 0) {
                            inMap = false;
                            break;
                        }
                        if (mapArray[i][j - 1] == '#') {
                            guardDirection = "UP";
                            mapArray[i][j] = 'T';
                        } else {
                            j--;
                        }
                    }
                    case "RIGHT" -> {
                        if (j == mapArray[i].length - 1) {
                            inMap = false;
                            break;
                        }
                        if (mapArray[i][j + 1] == '#') {
                            guardDirection = "DOWN";
                            mapArray[i][j] = 'T';
                        } else {
                            j++;
                        }
                    }
                    case "DOWN" -> {
                        if (i == mapArray.length - 1) {
                            inMap = false;
                            break;
                        }
                        if (mapArray[i + 1][j] == '#') {
                            guardDirection = "LEFT";
                            mapArray[i][j] = 'T';
                        } else {
                            i++;
                        }
                    }
                }
            }
            System.out.println("Distinct positions: " + distinct);
        } catch (FileNotFoundException e) {
            System.err.println("Could not find file! " + e);
        }
    }
}
