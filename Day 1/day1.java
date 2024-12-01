import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class day1 {
    public static void main(String[] args) {
        //Output values
        int sumDist = 0;
        int simScore = 0;
        //File setup
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        File in = new File("inputday1.text");
        try (Scanner fr = new Scanner(in)) {
                while(fr.hasNextLine()) {
                    String line = fr.nextLine();
                    String[] l;
                    l = line.split("\\s+");
                    left.add(Integer.valueOf(l[0]));
                    right.add(Integer.valueOf(l[1]));
                }
        }   catch(FileNotFoundException e) {
            out.println("Error! Could not find file.");
        }
        //Part 1 problem solution
        Collections.sort(left);
        Collections.sort(right);
        for(int i=0;i<left.size();i++) {
            sumDist += (Math.abs(left.get(i)-right.get(i)));
        }
        out.println("Sum of Distances: " + sumDist);

        //Part 2 problem solution
        for(int i=0;i<left.size();i++) {
            int currentNum = left.get(i);
            int numAppearances = 0;
            for(int z=0;z<right.size();z++) {
                if(right.get(z)==currentNum) {
                    numAppearances++;
                }
            }
            simScore += currentNum * numAppearances;
        }
        out.println("Similarity score: " + simScore);
    }
}