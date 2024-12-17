package day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class day11 {
    public static void main(String[] args) {
        File in = new File("day11/inputday11.txt");
        try(Scanner fr = new Scanner(in)) {
            String line = fr.nextLine();
            List<String> l = Arrays.asList(line.split(" "));
            List<Long> st = l.stream().map(Long::valueOf).collect(Collectors.toList());
            Map<Long, Long> stones = new HashMap<>();
            for(Long stone : st) {
                stones.put(stone, 1L);
            }
            for(int a=0;a<75;a++) {
                blink(stones);
            }
            long numStones = 0;
            for(Long stone : stones.keySet()) {
                numStones += stones.get(stone);
            }
            System.out.println("\n" + numStones);
        } catch (FileNotFoundException e) {
            System.err.println("Could not find file! " + e);
        }
    }

    private static void blink(Map<Long, Long> stones) {
        Map<Long, Long> srcStones = new HashMap<>(stones);
        for(Long key : srcStones.keySet()) {
            if(srcStones.get(key) > 0) {
                if (key == 0L) {
                    if (stones.containsKey(1L)) {
                        stones.put(1L, stones.get(1L) + srcStones.get(key));
                    } else {
                        stones.put(1L, srcStones.get(key));
                    }
                } else if (key.toString().length() % 2 == 0 && key.toString().length() >= 2) {
                    String k = key.toString();
                    Long l = Long.parseUnsignedLong(k.substring(0, k.length() / 2));
                    Long r = Long.parseUnsignedLong(k.substring(k.length() / 2));
                    if (stones.containsKey(l)) {
                        stones.put(l, stones.get(l) + srcStones.get(key));
                    } else {
                        stones.put(l, srcStones.get(key));
                    }
                    if (stones.containsKey(r)) {
                        stones.put(r, stones.get(r) + srcStones.get(key));
                    } else {
                        stones.put(r, srcStones.get(key));
                    }
                } else {
                    if (stones.containsKey(key * 2024L)) {
                        stones.put(key * 2024L, stones.get(key * 2024L) + srcStones.get(key));
                    } else {
                        stones.put(key * 2024L, srcStones.get(key));
                    }
                }
                stones.put(key, stones.get(key)-srcStones.get(key));
            }
        }
    }
}
