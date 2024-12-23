package day15;

import java.util.*;

public class Bot {
    private int x;
    private int y;
    private List<Character> moves;
    private char[][] map;
    Map<Character, int[]> dirMap = new HashMap<>();

    Bot(int x, int y, List<Character> movelist, char[][] mapArray) {
        this.x = x;
        this.y = y;
        this.moves = movelist;
        this.map = mapArray;
        dirMap.put('<', new int[] {0, -1});
        dirMap.put('^', new int[] {-1, 0});
        dirMap.put('>', new int[] {0, 1});
        dirMap.put('v', new int[] {1, 0});
        dirMap.put('V', new int[] {1, 0});
    }

    public void attempt() {
        Character m = this.moves.remove(0);
        int[] vel = dirMap.get(m);
        if(this.dirSafe(vel, this.x, this.y)) {
            int i = this.y, j = this.x;
            Character a = '@';
            this.map[this.y][this.x] = '.';
            boolean lastLoop = false;
            while(!lastLoop) {
                if(a == '.') {
                    lastLoop = true;
                }
                m = a; //Currently hovered symbol ('@' or 'O') is grabbed
                i += vel[0]; //Advance a position
                j += vel[1];
                a = this.map[i][j]; //Next symbol is grabbed
                if(!lastLoop) {
                    this.map[i][j] = m; //Replace location of next symbol with current symbol if the next symbol is not an unrelated box
                } else if(this.map[i][j] != 'O') {
                    //this.map[i][j] = m;
                }
            }
            this.y += vel[0];
            this.x += vel[1];
        }
    }

    public void attempt2() {
        Character m = this.moves.remove(0);
        int[] vel = dirMap.get(m);
        List<String> toMove = new ArrayList<>();
        List<String> moved = new ArrayList<>();
        if(this.dirSafe2(vel, this.x, this.y)) {
            int i = this.y, j = this.x;
            Character a = '@';
            this.map[this.y][this.x] = '.';
            boolean lastLoop = false;
            while(!lastLoop /*&& dirSafe2(vel, this.x, this.y)*/) {
                while(!toMove.isEmpty()) {
                    String s = toMove.remove(0);
                    int[] loc = new int[] {Integer.parseInt(s.split(" ")[0]), Integer.parseInt(s.split(" ")[1])};
                    if(!(moved.contains(s) || loc[0] == 0 || loc[1]==0)) {
                        if (this.map[loc[0] + vel[0]][loc[1] + vel[1]] == '.') {
                            this.map[loc[0] + vel[0]][loc[1] + vel[1]] = this.map[loc[0]][loc[1]];
                            this.map[loc[0]][loc[1]] = '.';
                            moved.add(s);
                        } else {
                            if (this.map[loc[0] + vel[0]][loc[1] + vel[1]] == '[') {
                                toMove.add((loc[0] + vel[0]) + " " + (loc[1] + vel[1] + 1));
                            }
                            if (this.map[loc[0] + vel[0]][loc[1] + vel[1]] == ']') {
                                toMove.add((loc[0] + vel[0]) + " " + (loc[1] + vel[1] - 1));
                            }
                            toMove.add((loc[0] + vel[0]) + " " + (loc[1] + vel[1]));
                            toMove.add(s); //Reinsert self to be done last.
                            //toMove.sort(null);
                            //Collections.reverse(toMove);
                        }
                    }
                }
                if(a == '.') {
                    lastLoop = true;
                }
                m = a;
                i += vel[0];
                j += vel[1];
                a = this.map[i][j];
                //If a == '[' or ']' then we need to push the associated box too. Any boxes touched by those will also move and must be checked.
                if(a == '[') {toMove.add(i + " " + (j+1));}
                if(a == ']') {toMove.add(i + " " + (j-1));}
                if(!lastLoop) {
                    this.map[i][j] = m;
                } else if(this.map[i][j] != '[' || this.map[i][j] != ']') {
                    //this.map[i][j] = m;
                }
            }
            this.y += vel[0];
            this.x += vel[1];
        }
    }

    private boolean dirSafe(int[] vel, int x, int y) {
        while(true) {
            if(x < 0 || x > this.map[y].length || y<0 || y > this.map.length) {
                return false;
            }
            if(this.map[y][x] == '#') {
                return false;
            }
            if(this.map[y][x] == '.') {
                return true;
            }
            y += vel[0];
            x += vel[1];
        }
    }

    //Only checks that the immediate neighbor is safe to move by checking its connected boxes in the direction of travel.
    private boolean dirSafe2(int[] vel, int x, int y) {
        while(true) {
            if(x <= 0 || y<=0 || y >= this.map.length-1 || x >= this.map[y].length-1) { //fail-safe for bad array math
                return false;
            }
            if(this.map[y][x] == '#') {
                return false;
            }
            if(this.map[y][x] == '.') {
                return true;
            }
            if(this.map[y][x] == '[' && vel[1] == 0) {
                return dirSafe2(vel, (x+1+vel[1]), (y+vel[0])) && dirSafe2(vel, (x+vel[1]), (y+vel[0]));
            }
            if(this.map[y][x] == ']' && vel[1]==0) {
                return dirSafe2(vel, (x-1+vel[1]), (y+vel[0])) && dirSafe2(vel, (x+vel[1]), (y+vel[0]));
            }
            y += vel[0];
            x += vel[1];
        }
    }

    public boolean hasMoves() {
        return !this.moves.isEmpty();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public char[][] getMap() {
        return this.map;
    }

    public void printMap() {
        for(int i=0;i<this.map.length;i++) {
            for(int j=0;j<this.map[i].length;j++) {
                System.out.print(this.map[i][j]);
            }
            System.out.println();
        }
    }
}
