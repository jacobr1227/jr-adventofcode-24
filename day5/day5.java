package day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class day5 {

    public static void main(String[] args) {
        int correctMidSum = 0;
        int incorrectMidSum = 0;
        Map<Integer, List<Integer>> ruleMap = new HashMap<>();
        File in = new File("day5/inputday5.text");
        try (Scanner fr = new Scanner(in)) {
            while (fr.hasNextLine()) {
                String line = fr.nextLine();
                if (line.contains("|")) { //Rule lines
                    String rule[] = line.split("\\|");
                    if (!ruleMap.containsKey(Integer.valueOf(rule[0]))) {
                        ruleMap.put(Integer.valueOf(rule[0]), new ArrayList<>());
                    }
                    List<Integer> tmp = ruleMap.get(Integer.valueOf(rule[0]));
                    tmp.add(Integer.valueOf(rule[1]));
                    ruleMap.put(Integer.valueOf(rule[0]), tmp);
                } else if (!line.isBlank()) { //Data lines
                    boolean success = true;
                    int pgs[] = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
                    OUTER:
                    for (int i = 0; i < pgs.length; i++) {
                        if (ruleMap.containsKey(pgs[i])) {
                            List<Integer> rules = ruleMap.get(pgs[i]);
                            for (int j = i; j > 0; j--) {
                                if (rules.contains(pgs[j])) {
                                    success = false;
                                    break OUTER;
                                }
                            }
                        }
                    }
                    if (success) {
                        correctMidSum += pgs[(int) Math.ceil(pgs.length / 2)];
                    } else {
                        //Reorder pages until correct.
                        int[] solve = new int[pgs.length];
                        for(int a=0;a<solve.length;a++) {
                            solve[a] = -1;
                        }
                        solve = correctPage(ruleMap, pgs, solve, 0);
                        if (validate(solve)) {
                            incorrectMidSum += solve[(int) Math.ceil(pgs.length / 2)];
                        }
                    }
                }
            }
            System.out.println("Sum of Successful middle pages: " + correctMidSum);
            System.out.println("Sum of corrected middle pages: " + incorrectMidSum);
        } catch (FileNotFoundException e) {
            System.err.println("Could not find file! " + e);
        }
    }

    private static int[] correctPage(Map<Integer, List<Integer>> ruleMap, int[] pgs, int[] solution, int index) {
        if (index == 0) {
            for (int i = index; i < pgs.length; i++) {
                solution[index] = pgs[i];
                int[] tmp = cloneArray(pgs);
                tmp[i] = -1;
                solution = correctPage(ruleMap, tmp, solution, index + 1);
                for (int s = 0; s < solution.length; s++) {
                    if (solution[s] == -1) {
                        break;
                    }
                    if (s == solution.length - 1) {
                        return solution;
                    }
                }
            }
        } else {
            List<Integer> pgrules = ruleMap.get(solution[index - 1]);
            for (int a = 0; a < pgs.length; a++) {
                if (pgrules != null && pgrules.contains(pgs[a])) {
                    solution[index] = pgs[a];
                    int[] tmp = cloneArray(pgs);
                    tmp[a] = -1;
                    solution = correctPage(ruleMap, tmp, solution, index + 1);
                    for (int s = 0; s < solution.length; s++) {
                        if (solution[s] == -1) {
                            break;
                        }
                        if (s == solution.length - 1) {
                            return solution;
                        }
                    }
                }
            }
        }
        return solution;
    }

    private static boolean validate(int[] solve) {
        for(int s : solve) {
            if (s==-1) {
                return false;
            }
        }
        return true;
    }

    private static int[] cloneArray(int[] arr) {
        int[] res = new int[arr.length];
        for(int i=0;i<arr.length;i++) {
            res[i] = arr[i];
        }
        return res;
    }
}
