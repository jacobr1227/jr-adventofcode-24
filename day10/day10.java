package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class day10 {
    public static void main(String[] args) {
        File in = new File("day10/inputday10.txt");
        try (Scanner fr = new Scanner(in)) {
            List<String> lines = new ArrayList<>();
            while (fr.hasNextLine()) {
                lines.add(fr.nextLine());
            }
            char[][] mapArray = new char[lines.size()][lines.get(0).length()];
            for (int i = 0; i < lines.size(); i++) {
                mapArray[i] = lines.get(i).toCharArray();
            }
            int score = 0;
            for (int i = 0; i < mapArray.length; i++) {
                for (int j = 0; j < mapArray[i].length; j++) {
                    if (mapArray[i][j] == '0') {
                        List<int[]> trails = trailsList(mapArray, '0', new int[]{i, j});
                        int num = 0;
                        while(!trails.isEmpty()) {
                            int[] entry = trails.get(0);
                            trails.removeIf(t -> Arrays.equals(t, entry));
                            num++;
                        }
                        System.out.println("Trailhead complete! Score: " + num);
                        score += num;
                    }
                }
            }
            System.out.println("Total Original score: " + score);
            score = 0;
            for (int i = 0; i < mapArray.length; i++) {
                for (int j = 0; j < mapArray[i].length; j++) {
                    if (mapArray[i][j] == '0') {
                        int num = trails(mapArray, '0', new int[]{i, j});
                        System.out.println("Trailhead complete! Score: " + num);
                        score += num;
                    }
                }
            }
            System.out.println("Total Revised score: " + score);

        } catch (FileNotFoundException e) {
            System.err.println("Could not find file! " + e);
            System.exit(-1);
        }
    }

    private static int trails(char[][] mapArray, char height, int[] location) {
        int sum = 0;
        if (height == '9') {
            return 1;
        }
        height += 1;

        if (location[0] > 0 && mapArray[location[0] - 1][location[1]] == height) {
            sum += trails(mapArray, height, new int[]{location[0] - 1, location[1]});
        }
        if (location[0] < mapArray.length-1 && mapArray[location[0] + 1][location[1]] == height) {
            sum += trails(mapArray, height, new int[]{location[0] + 1, location[1]});
        }
        if (location[1] > 0 && mapArray[location[0]][location[1] - 1] == height) {
            sum += trails(mapArray, height, new int[]{location[0], location[1] - 1});
        }
        if (location[1] < mapArray[location[0]].length-1 && mapArray[location[0]][location[1] + 1] == height) {
            sum += trails(mapArray, height, new int[]{location[0], location[1] + 1});
        }
        return sum;
    }


    private static List<int[]> trailsList(char[][] mapArray, char height, int[] location) {
        List<int[]> visited = new ArrayList<>();
        if (height == '9') {
            visited.add(location);
            return visited;
        }
        height += 1;

        if (location[0] > 0 && mapArray[location[0] - 1][location[1]] == height) {
            visited.addAll(trailsList(mapArray, height, new int[]{location[0] - 1, location[1]}));
        }
        if (location[0] < mapArray.length-1 && mapArray[location[0] + 1][location[1]] == height) {
            visited.addAll(trailsList(mapArray, height, new int[]{location[0] + 1, location[1]}));
        }
        if (location[1] > 0 && mapArray[location[0]][location[1] - 1] == height) {
            visited.addAll(trailsList(mapArray, height, new int[]{location[0], location[1] - 1}));
        }
        if (location[1] < mapArray[location[0]].length-1 && mapArray[location[0]][location[1] + 1] == height) {
            visited.addAll(trailsList(mapArray, height, new int[]{location[0], location[1] + 1}));
        }
        return visited;
    }
}
