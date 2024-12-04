package day4;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class day4 {
    public static void main(String[] args) {
        int xmasCount = 0;
        File in = new File("day4/inputday4.text");
        try {
            int hC = countHorizontal(new Scanner(in));
            int dCmas[] = countVertDiag(new Scanner(in));
            int dC = dCmas[0];
            int mas = dCmas[1];
            xmasCount += hC + dC;
            System.out.println("Horizontal Count: " + hC + "\nVertical & Diagonal Count: " + dC + "\nTotal: " + xmasCount);
            System.out.println("X-mas count: " + mas);
        } catch(FileNotFoundException e) {
            System.err.println("Could not find file! " + e);
        }
    }

    private static int countHorizontal(Scanner input) {
        int num = 0;
        while(input.hasNextLine()) {
            String line = input.nextLine();
            for(int i=0;i<line.length()-3;i++) {
                if(line.substring(i,i+4).equals("XMAS") || line.substring(i,i+4).equals("SAMX")) {
                    num++;
                }
            }
        }
        return num;
    }

    private static int[] countVertDiag(Scanner input) {
        int num = 0;
        int masCount = 0;
        List<String> lines = new ArrayList<>();
        while(input.hasNextLine()) {
            lines.add(input.nextLine());
        }
        if(!lines.isEmpty()) {
            char[][] dim = new char[lines.size()][lines.get(0).length()];
            int i = 0, j = 0;
            //Process data down to a 2d character array.
            for(String line : lines) {
                for(char c : line.toCharArray()) {
                    dim[i][j] = c;
                    j++;
                }
                i++;
                j=0;
            }

            for(i=0;i<dim.length;i++) {
                for(j=0;j<dim[i].length;j++) {
                    if(i+3<dim.length && j+3<dim[i].length) { //If there is room diagonally down and right
                        if ((dim[i][j] == 'X' && dim[i+1][j+1] == 'M' && dim[i+2][j+2] == 'A' && dim[i+3][j+3] == 'S') ||
                        (dim[i][j] == 'S' && dim[i+1][j+1] == 'A' && dim[i+2][j+2] == 'M' && dim[i+3][j+3] == 'X')) {
                            num++;
                        }
                    }
                    if(i+3<dim.length && j-3>=0) { //If there is room diagonally down and left
                        if ((dim[i][j] == 'X' && dim[i+1][j-1] == 'M' && dim[i+2][j-2] == 'A' && dim[i+3][j-3] == 'S') ||
                        (dim[i][j] == 'S' && dim[i+1][j-1] == 'A' && dim[i+2][j-2] == 'M' && dim[i+3][j-3] == 'X')) {
                            num++;
                        }
                    }
                    if(i+3<dim.length) {
                        if ((dim[i][j] == 'X' && dim[i+1][j] == 'M' && dim[i+2][j] == 'A' && dim[i+3][j] == 'S') ||
                        (dim[i][j] == 'S' && dim[i+1][j] == 'A' && dim[i+2][j] == 'M' && dim[i+3][j] == 'X')) {
                            num++;
                        }
                    }
                    if(dim[i][j] == 'A' && i+1<dim.length && i>0 && j>0 && j+1<dim[i].length) {
                        if(dim[i-1][j-1] == 'M' && dim[i+1][j+1] == 'S' && dim[i+1][j-1] == 'M' && dim[i-1][j+1] == 'S') {
                            masCount++;
                        }
                        else if(dim[i+1][j-1] == 'M' && dim[i-1][j+1] == 'S' && dim[i+1][j+1] == 'M' && dim[i-1][j-1] == 'S') {
                            masCount++;
                        }
                        else if(dim[i+1][j+1] == 'M' && dim[i-1][j-1] == 'S' && dim[i-1][j+1] == 'M' && dim[i+1][j-1] == 'S') {
                            masCount++;
                        }
                        else if(dim[i-1][j+1] == 'M' && dim[i+1][j-1] == 'S' && dim[i-1][j-1] == 'M' && dim[i+1][j+1] == 'S') {
                            masCount++;
                        }
                        
                    }
                }
            }
        }
        return new int[] {num, masCount};
    }
}
