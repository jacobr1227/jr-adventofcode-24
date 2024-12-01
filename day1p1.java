import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class day1p1 {
    public static void main(String[] args) {
        int sumDist = 0;
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
        Collections.sort(left);
        Collections.sort(right);
        for(int i=0;i<left.size();i++) {
            sumDist += (Math.abs(left.get(i)-right.get(i)));
        }
        out.println(sumDist);
    }
}