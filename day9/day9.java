package day9;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;

public class day9 {
    public static void main(String[] args) {

        File in = new File("day9/inputday9.txt");
        try(Scanner fr = new Scanner(in)) {
            String disk = fr.nextLine();
            part1(disk);
            part2(disk);
        } catch (FileNotFoundException e) {
            System.err.println("Could not find file! " + e);
        }
    }

    private static void part1(String disk) {
        //Part 1
        long checksum = 0;
        List<String> fileSys = new ArrayList<>();
        int id = 0;
        boolean file = true;
        for(char c : disk.toCharArray()) {
            if(!file) {
                for(int i=0;i<Integer.parseInt(String.valueOf(c));i++) {
                    fileSys.add(".");
                }
                file = true;
            }
            else {
                for(int i=0;i<Integer.parseInt(String.valueOf(c));i++) {
                    fileSys.add(String.valueOf(id));
                }
                file = false;
                id++;
            }
        }
        int lastInt = fileSys.size()-1;
        for(int i=0;i<fileSys.size();i++) {
            while(fileSys.get(lastInt).equals(".")) {
                lastInt--;
            }
            if(Objects.equals(fileSys.get(i), ".") && i<lastInt) {
                fileSys.set(i, fileSys.get(lastInt));
                fileSys.set(lastInt, ".");
            }
            if(!Objects.equals(fileSys.get(i), ".")) {
                checksum += Long.parseUnsignedLong(fileSys.get(i)) * i;
            }
        }
        System.out.println(checksum);
    }

    private static void part2(String disk) {
        long checksum = 0;
        List<String[]> fileSys = new ArrayList<>();
        int id = 0;
        boolean file = true;
        for(char c : disk.toCharArray()) {
            if(c == '0') {
                file = !file;
            }
            else if(!file) {
                fileSys.add(new String[] {".", String.valueOf(c)});
                file = true;
            }
            else {
                fileSys.add(new String[] {String.valueOf(id), String.valueOf(c)});
                file = false;
                id++;
            }
        }
        boolean change = true;
        while(change) {
            change = false;
            INNER:
            for(int i=0;i<fileSys.size();i++) {
                if(fileSys.get(i)[0].equals(".")) {
                    int emptySpaceSize = Integer.parseInt(fileSys.get(i)[1]);
                    for(int j=fileSys.size()-1;j>i;j--) {
                        if(!fileSys.get(j)[0].equals(".") && Integer.parseInt(fileSys.get(j)[1]) <= emptySpaceSize) {
                            int sDiff = emptySpaceSize - Integer.parseInt(fileSys.get(j)[1]);
                            fileSys.set(i, new String[] {fileSys.get(j)[0], fileSys.get(j)[1]});
                            fileSys.set(j, new String[] {".", fileSys.get(j)[1]});
                            if(sDiff >= 1) {
                                fileSys.add(i+1, new String[] {".", String.valueOf(sDiff)});
                            }
                            change = true;
                            break INNER;
                        }
                    }
                }
            }
        }
        int indices = 0;
        for (String[] fileSy : fileSys) {
            if (!fileSy[0].equals(".")) {
                for (int a = indices; a < indices + Integer.parseInt(fileSy[1]); a++) {
                    checksum += Long.parseUnsignedLong(fileSy[0]) * a;
                }
            }
            indices += Integer.parseInt(fileSy[1]);
        }
        System.out.println(checksum);
    }
}
