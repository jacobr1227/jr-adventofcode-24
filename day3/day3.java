package day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.stream.*;

public class day3 {
    public static void main(String[] args) {
        int total = 0;
        File in = new File("day3/inputday3.text");
        try (Scanner fr = new Scanner(in)) {
                List<String> next = new ArrayList<>();
                Stream<MatchResult> mul = fr.findAll("mul\\(\\d\\d?\\d?,\\d\\d?\\d?\\)|do\\(\\)|don't\\(\\)");
                mul.forEach(match -> next.add(match.group()));
                boolean skip = false;
                for(String n : next) {
                    if(n.equals("do()")) {
                        skip = false;
                    }
                    else if(n.equals("don't()")) {
                        skip = true;
                    }
                    else if (!skip) {
                        n = n.substring(4, n.length()-1);
                        String[] nums = n.split(",");
                        total += Integer.parseInt(nums[0])*Integer.parseInt(nums[1]);
                    }
                }
            System.out.println(total);
        } catch (FileNotFoundException e) {
            System.err.println("Failed to find file!");
        }
    }
}
