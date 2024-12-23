package day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

public class day14 {
    //6376 is too low, 16779 is too high. This approach doesn't work.
    public static void main(String[] args) {
        File in = new File("day14/inputday14.txt");
        try(Scanner fr = new Scanner(in)) {
            List<Robot> robots = new ArrayList<>();
            List<Integer> possibleTimes = new ArrayList<>();
            long minScore = Integer.MAX_VALUE;
            while(fr.hasNextLine()) {
                String[] line = fr.nextLine().split(" ");
                String[] p = line[0].substring(2).split(",");
                String[] v = line[1].substring(2).split(",");
                int p0 = Integer.parseInt(p[0]);
                int p1 = Integer.parseInt(p[1]);
                int v0 = Integer.parseInt(v[0]);
                int v1 = Integer.parseInt(v[1]);
                robots.add(new Robot(p0, p1, v0, v1));
            }
            for(int i=0;i<30000;i++) {
                int[] q = new int[5];
                for (Robot robot : robots) {
                    robot.move(1, 101, 103);
                    q[robot.getQuad(101, 103)]++;
                }
                int safety = q[1]*q[2]*q[3]*q[4];
                if(i==100) {
                    System.out.println("Safety Factor: " + safety);
                }
                if(safety<minScore) {
                    minScore = Math.min(safety, minScore);
                    possibleTimes.add(i *1000);
                }

                if(!findDuplicates(robots)) {
                    possibleTimes.add(i);
                    char[][] mapArray = new char[103][101];
                    for(int a=0;a<mapArray.length;a++) {
                        Arrays.fill(mapArray[a], '0');
                    }
                    for(Robot robot : robots) {
                        mapArray[robot.getY()][robot.getX()]++;
                    }
                    for(int a=0;a<mapArray.length;a++) {
                        for(int j=0;j<mapArray[a].length;j++) {
                            System.out.print(mapArray[a][j]);
                        }
                        System.out.println();
                    }
                }
            }
            System.out.println("Easter Egg: " + Arrays.toString(possibleTimes.stream().toArray()));
        } catch(FileNotFoundException e) {
            System.err.println("Could not find file! " + e);
        }
    }

    private static boolean findDuplicates(List<Robot> robots) {
        List<String> pos = new ArrayList<>();
        for (Robot robot : robots) {
            pos.add(robot.getX() + " " + robot.getY());
        }
        while(!pos.isEmpty()) {
            String r = pos.remove(0);
            int x = Integer.parseInt(r.split(" ")[0]);
            int y = Integer.parseInt(r.split(" ")[1]);
            if(pos.contains(x + " " + y)) {
                return true;
            }
        }
        return false;
    }
}