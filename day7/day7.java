package day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class day7 {
    public static void main(String[] args) {
        long calibrationRes = 0;
        File in = new File("day7/inputday7.txt");
        try(Scanner fr = new Scanner(in)) {
            while(fr.hasNextLine()) {
                String line = fr.nextLine();
                String[] eq = line.split(": ");
                Long res = Long.parseUnsignedLong(eq[0]);
                String[] ops = eq[1].split(" ");
                if(doMath(res, ops, 0, new Long(0))) {
                    calibrationRes += res;
                }
            }
            System.out.println(calibrationRes);
        } catch(FileNotFoundException e) {
            System.err.println("Could not find file! " + e);
        }
    }

    private static boolean doMath(Long res, String[] ops, int index, Long current) {
        if(current.equals(res)) {
            return true;
        }

        if(current > res) {
            return false;
        }

        if(index < ops.length) {
            boolean add = doMath(res, ops.clone(), index+1, current+Long.parseUnsignedLong(ops[index]));
            if(!add) {
                boolean mult = doMath(res, ops.clone(), index + 1, current * Long.parseUnsignedLong(ops[index]));
                if(!mult) {
                    current = Long.parseUnsignedLong(current.toString() + ops[index]);
                    return doMath(res, ops, index+1, current);
                } else return true;
            }
            else return true;
        }
        return false;
    }
}
