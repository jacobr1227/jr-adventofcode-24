package day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day12 {
    public static void main(String[] args) {
        File in = new File("day12/inputday12.txt");
        try (Scanner fr = new Scanner(in)) {
            List<String> lines = new ArrayList<>();
            while (fr.hasNextLine()) {
                lines.add(fr.nextLine());
            }
            char[][] mapArray = new char[lines.size()][lines.get(0).length()];
            for (int i = 0; i < lines.size(); i++) {
                mapArray[i] = lines.get(i).toCharArray();
            }
            long price = 0;
            long disPrice = 0;
            for(int i=0;i< mapArray.length;i++) {
                for(int j=0;j<mapArray[i].length;j++) {
                    if(mapArray[i][j] != '.') {
                        //price += findRegionPrice(mapArray, i, j, mapArray[i][j]);
                        disPrice += findDiscountPrice(mapArray, i, j, mapArray[i][j]);
                    }
                }
            }
            //System.out.println(price);
            System.out.println(disPrice);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file! " + e);
        }
    }

    //Part 1
    private static int findRegionPrice(char[][] mapArray, int i, int j, char plant) {
        int area = 0;
        int perimeter = 0;
        List<int[]> unvisited = new ArrayList<>();
        List<String> visited = new ArrayList<>();
        unvisited.add(new int[]{i, j});
        while(!unvisited.isEmpty()) {
            int[] loc = unvisited.remove(0);
            String locString = loc[0] + ", " + loc[1];
            if(!visited.contains(locString)) {
                visited.add(locString);
                mapArray[loc[0]][loc[1]] = '.';
                area += 1;
                if (loc[0] == 0) {
                    //At top of map
                    perimeter += 1;
                } else if (mapArray[loc[0] - 1][loc[1]] == plant) {
                    unvisited.add(new int[]{loc[0] - 1, loc[1]});
                } else if (!visited.contains((loc[0]-1) + ", " + loc[1])) {
                    perimeter += 1;
                }

                if (loc[0] == mapArray.length - 1) {
                    //At Bottom of map
                    perimeter += 1;
                } else if (mapArray[loc[0] + 1][loc[1]] == plant) {
                    unvisited.add(new int[]{loc[0] + 1, loc[1]});
                } else if (!visited.contains((loc[0]+1) + ", " + loc[1])) {
                    perimeter += 1;
                }

                if (loc[1] == 0) {
                    //At left of map
                    perimeter += 1;
                } else if (mapArray[loc[0]][loc[1] - 1] == plant) {
                    unvisited.add(new int[]{loc[0], loc[1] - 1});
                } else if (!visited.contains(loc[0] + ", " + (loc[1]-1))) {
                    perimeter += 1;
                }

                if (loc[1] == mapArray[loc[0]].length - 1) {
                    //At right of map
                    perimeter += 1;
                } else if (mapArray[loc[0]][loc[1] + 1] == plant) {
                    unvisited.add(new int[]{loc[0], loc[1] + 1});
                } else if (!visited.contains(loc[0] + ", " + (loc[1]+1))) {
                    perimeter += 1;
                }
            }
        }
        return area * perimeter;
    }

    //Part 2
    private static long findDiscountPrice(char[][] mapArray, int i, int j, char plant) {
        long area = 0;
        long sides = 0;
        List<int[]> unvisited = new ArrayList<>();
        List<String> visited = new ArrayList<>();
        Map<String, String> walls = new HashMap<>();
        unvisited.add(new int[]{i, j});
        while(!unvisited.isEmpty()) {
            int[] loc = unvisited.remove(0);
            String locString = loc[0] + ", " + loc[1];
            String up = (loc[0]-1) + ", " + loc[1];
            String down = (loc[0]+1) + ", " + loc[1];
            String left = loc[0] + ", " + (loc[1]-1);
            String right = loc[0] + ", " + (loc[1]+1);
            if(!visited.contains(locString)) {
                visited.add(locString);
                mapArray[loc[0]][loc[1]] = '.';
                area += 1;

                if (loc[0] > 0 && mapArray[loc[0] - 1][loc[1]] == plant) {
                    unvisited.add(new int[]{loc[0] - 1, loc[1]});
                } else if (!visited.contains(up)) {
                    if(walls.containsKey(locString))
                        walls.put(locString, walls.get(locString) + "U");
                    else
                        walls.put(locString, "U");
                }

                if (loc[0] < mapArray.length - 1 && mapArray[loc[0] + 1][loc[1]] == plant) {
                    unvisited.add(new int[]{loc[0] + 1, loc[1]});
                } else if (!visited.contains(down)) {
                    if(walls.containsKey(locString))
                        walls.put(locString, walls.get(locString) + "D");
                    else
                        walls.put(locString, "D");
                }

                if (loc[1] > 0 && mapArray[loc[0]][loc[1] - 1] == plant) {
                    unvisited.add(new int[]{loc[0], loc[1] - 1});
                } else if (!visited.contains(left)) {
                    if(walls.containsKey(locString))
                        walls.put(locString, walls.get(locString) + "L");
                    else
                        walls.put(locString, "L");
                }

                if (loc[1] < mapArray[loc[0]].length - 1 && mapArray[loc[0]][loc[1] + 1] == plant) {
                    unvisited.add(new int[]{loc[0], loc[1] + 1});
                } else if (!visited.contains(right)) {
                    if(walls.containsKey(locString))
                        walls.put(locString, walls.get(locString) + "R");
                    else
                        walls.put(locString, "R");
                }
                if(walls.get(locString) != null) {
                    //Outer corners
                    String corners = walls.get(locString);
                    if (corners.contains("U") && corners.contains("L")) {
                        sides++;
                    }
                    if (corners.contains("U") && corners.contains("R")) {
                        sides++;
                    }
                    if (corners.contains("D") && corners.contains("L")) {
                        sides++;
                    }
                    if (corners.contains("D") && corners.contains("R")) {
                        sides++;
                    }

                    //Inner corners
                    if((visited.contains((loc[0]-1) + ", " + (loc[1]-1)) && walls.containsKey((loc[0]-1) + ", " + (loc[1]-1)) && walls.get((loc[0]-1) + ", " + (loc[1]-1)).contains("R"))) { //Inner top right
                        if(visited.contains(left) || mapArray[loc[0]][loc[1]-1] == plant) {
                            sides++;
                        }
                    }
                    if((visited.contains((loc[0]+1) + ", " + (loc[1]+1)) && walls.containsKey((loc[0]+1) + ", " + (loc[1]+1)) && walls.get((loc[0]+1) + ", " + (loc[1]+1)).contains("U"))) {
                        if(visited.contains(down) || mapArray[loc[0]+1][loc[1]] == plant) {
                            sides++;
                        }
                    }

                    if((visited.contains((loc[0]-1) + ", " + (loc[1]+1)) && walls.containsKey((loc[0]-1) + ", " + (loc[1]+1)) && walls.get((loc[0]-1) + ", " + (loc[1]+1)).contains("L"))) { //Inner top left
                        if(visited.contains(right) || mapArray[loc[0]][loc[1]+1] == plant) {
                            sides++;
                        }
                    }
                    if((visited.contains((loc[0]+1) + ", " + (loc[1]-1)) && walls.containsKey((loc[0]+1) + ", " + (loc[1]-1)) && walls.get((loc[0]+1) + ", " + (loc[1]-1)).contains("U"))) { //Inner top left
                        if(visited.contains(down) || mapArray[loc[0]+1][loc[1]] == plant) {
                            sides++;
                        }
                    }

                    if((visited.contains((loc[0]-1) + ", " + (loc[1]+1)) && walls.containsKey((loc[0]-1) + ", " + (loc[1]+1)) && walls.get((loc[0]-1) + ", " + (loc[1]+1)).contains("D"))) { //Inner bottom right
                        if(visited.contains(up) || mapArray[loc[0]-1][loc[1]] == plant) {
                            sides++;
                        }
                    }
                    if((visited.contains((loc[0]+1) + ", " + (loc[1]-1)) && walls.containsKey((loc[0]+1) + ", " + (loc[1]-1)) && walls.get((loc[0]+1) + ", " + (loc[1]-1)).contains("R"))) { //Inner bottom right
                        if(visited.contains(left) || mapArray[loc[0]][loc[1]-1] == plant) {
                            sides++;
                        }
                    }

                    if((visited.contains((loc[0]-1) + ", " + (loc[1]-1)) && walls.containsKey((loc[0]-1) + ", " + (loc[1]-1)) && walls.get((loc[0]-1) + ", " + (loc[1]-1)).contains("D"))) { //Inner bottom left
                        if(visited.contains(up) || mapArray[loc[0]-1][loc[1]] == plant) {
                            sides++;
                        }
                    }
                    if((visited.contains((loc[0]+1) + ", " + (loc[1]+1)) && walls.containsKey((loc[0]+1) + ", " + (loc[1]+1)) && walls.get((loc[0]+1) + ", " + (loc[1]+1)).contains("L"))) { //Inner bottom left
                        if(visited.contains(right) || mapArray[loc[0]][loc[1]+1] == plant) {
                            sides++;
                        }
                    }
                }
            }
        }
        return area * sides;
    }
}
