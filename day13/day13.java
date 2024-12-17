package day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class day13 {
    public static void main(String[] args) {
        long tokens = 0;
        long p2Tokens = 0;
        File in = new File("day13/inputday13.txt");
        try (Scanner fr = new Scanner(in)) {
            while(fr.hasNextLine()) {
                String a = fr.nextLine();
                int aX = Integer.parseInt(a.substring(12, 14));
                int aY = Integer.parseInt(a.substring(18, 20));
                String b = fr.nextLine();
                int bX = Integer.parseInt(b.substring(12, 14));
                int bY = Integer.parseInt(b.substring(18, 20));
                String[] prize = fr.nextLine().split(" \\w=");
                long goalX = Integer.parseInt(prize[1].substring(0, prize[1].length()-1)); //trim comma
                long goalY = Integer.parseInt(prize[2]);
                if(fr.hasNextLine()) {
                    fr.nextLine(); //Consume blank line.
                }
                tokens += playGame2(aX, aY, bX, bY, goalX, goalY);
                goalX += 10000000000000L;
                goalY += 10000000000000L;
                p2Tokens += playGame2(aX, aY, bX, bY, goalX, goalY);
            }
            System.out.println(tokens);
            System.out.println(p2Tokens);
        } catch (FileNotFoundException e) {
            System.err.println("Could not find file! " + e);
        }
    }

    private static long playGame2(long aX, long aY, long bX, long bY, long goalX, long goalY) {
        long tokens = 0;
        long det = aX*bY - (aY*bX);
        double bTokens = (double) (aX * goalY - (aY * goalX)) /det;
        double aTokens = (double) (goalX * bY - (goalY * bX)) /det;
        if(bTokens%1 ==0.0 && aTokens%1==0.0) {
            return ((long) aTokens*3) + ((long) bTokens);
        }
        return 0;
    }
}
