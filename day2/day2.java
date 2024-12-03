package day2;

import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.System.out;
import java.util.Scanner;

public class day2 {

    public static void main(String[] args) {
        int safeReports = 0;
        int correctedReports = 0;
        File in = new File("day2/inputday2.text");
        try (Scanner fr = new Scanner(in)) {
            while (fr.hasNextLine()) {
                String line = fr.nextLine();
                String[] lin;
                lin = line.split("\\s+");
                boolean success = isSuccessful(lin);
                if (success) {
                    safeReports++;
                } else {
                    int i = 0;
                    while(!success && i<lin.length) {
                        String[] lin2 = new String[lin.length-1];
                        for(int j=0;j<lin.length;j++) {
                            if(j<i) {
                                lin2[j] = lin[j];
                            }
                            if(j>i) {
                                lin2[j-1] = lin[j];
                            }
                        }
                        success = isSuccessful(lin2);
                        i++;
                    }
                    if (success) {
                        correctedReports++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            out.println("Error! Could not find file.");
        }
        System.out.println("Number of Safe Reports: " + safeReports);
        System.out.println("Number of Corrected Reports: " + correctedReports);
        System.out.println("Total Safe Reports: " + (safeReports+correctedReports));
    }

    private static boolean isSuccessful(String[] line) {
        boolean init = false, success = true;
        int prev = 0;
        int dir = 0;
        OUTER:
        for (String l : line) {
            int num = Integer.parseInt(l);
            if (init) {
                switch (dir) {
                    case 0 -> {
                        if (num > prev) {
                            dir = 1;
                        } else if (num < prev) {
                            dir = -1;
                        } else {
                            success = false;
                            break OUTER;
                        }
                    }
                    case 1 -> {
                        if (num <= prev) {
                            success = false;
                            break OUTER;
                        }
                    }
                    default -> {
                        if (num >= prev) {
                            success = false;
                            break OUTER;
                        }
                    }
                }
                if (Math.abs(num - prev) < 1 || Math.abs(num - prev) > 3) {
                    success = false;
                    break;
                }
            }
            init = true;
            prev = num;
        }
        return success;
    }
}
