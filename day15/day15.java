package day15;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class day15 {
    public static void main(String[] args) {
        File in = new File("day15/inputday15.txt");
        try(Scanner fr = new Scanner(in)) {
            boolean map = true;
            List<String> mapLines = new ArrayList<>();
            List<Character> moveLines = new ArrayList<>();
            int[] robotCoords = new int[2];
            while(fr.hasNextLine()) {
                String l = fr.nextLine();
                if(l.isEmpty()) {
                    map = false;
                }
                if(map) {
                    mapLines.add(l);
                    for(Character c : l.toCharArray()) {
                        if(c == '@') {
                            robotCoords = new int[] {mapLines.size()-1, l.indexOf(c)};
                        }
                    }
                }
                else {
                    for(Character c : l.toCharArray()) {
                        moveLines.add(c);
                    }
                }
            }
            List<Character> moves2 = new ArrayList<>(moveLines);
            char[][] mapArray = new char[mapLines.size()][mapLines.get(0).length()];
            for(int i=0;i<mapLines.size();i++) {
                mapArray[i] = mapLines.get(i).toCharArray();
            }

            //part 1
            Bot robot = new Bot(robotCoords[1], robotCoords[0], moveLines, mapArray);
            while(robot.hasMoves()) {
                robot.attempt();
            }
            int GPSScore = 0;
            char[][] newMap = robot.getMap();
            for(int i=0;i<newMap.length;i++) {
                for(int j=0;j<newMap[i].length;j++) {
                    if(newMap[i][j] == 'O') {
                        GPSScore += 100*i + j;
                    }
                }
            }
            robot.printMap();
            System.out.println("GPS Sum: " + GPSScore);

            //part 2
            //double the map here
            for(int i=0;i<mapLines.size();i++) {
                mapArray[i] = mapLines.get(i).toCharArray();
            }
            char[][] newArray = new char[mapArray.length][mapArray[0].length*2];
            for(int i=0;i<mapArray.length;i++) {
                for(int j=0;j<mapArray[i].length;j++) {
                    if(mapArray[i][j] == '@') {
                        newArray[i][j*2] = '@';
                        newArray[i][j*2+1] = '.';
                        robotCoords = new int[] {i, j*2};
                    }
                    if(mapArray[i][j] == 'O') {
                        newArray[i][j*2] = '[';
                        newArray[i][j*2+1] = ']';
                    }
                    if(mapArray[i][j] == '#') {
                        newArray[i][j*2] = '#';
                        newArray[i][j*2+1] = '#';
                    }
                    if(mapArray[i][j] == '.') {
                        newArray[i][j*2] = '.';
                        newArray[i][j*2+1] = '.';
                    }

                }
            }
            //Adjust robot coordinates appropriately
            Bot rob2 = new Bot(robotCoords[1], robotCoords[0], moves2, newArray);
            while(rob2.hasMoves()) {
                rob2.attempt2();
            }
            GPSScore = 0;
            char[][] newMap2 = rob2.getMap();
            for(int i=0;i<newMap2.length;i++) {
                for(int j=0;j<newMap2[i].length;j++) {
                    if(newMap2[i][j] == '[') {
                        GPSScore += 100*i + j;
                    }
                }
            }
            rob2.printMap();
            System.out.println("Double-width GPS Sum: " + GPSScore);
        } catch(FileNotFoundException e) {
            System.err.println("Could not find file! " + e);
        }
    }
}
