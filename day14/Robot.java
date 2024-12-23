package day14;

public class Robot {
    private int x, y, dX, dY;
    Robot(int x, int y, int dX, int dY) {
        this.x = x;
        this.y = y;
        this.dX = dX;
        this.dY = dY;
    }

    public void move(int steps, int sizeX, int sizeY) {
        int x = (this.x + this.dX * steps) % sizeX;
        int y = (this.y + this.dY * steps) % sizeY;
        if(x < 0) {x += sizeX;}
        if(y < 0) {y += sizeY;}
        this.setX(x);
        this.setY(y);
    }

    public int getX() {return this.x;}
    public int getY() {return this.y;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}

    public int getQuad(int sizeX, int sizeY) {
        if(this.x < (sizeX/2)) {
            if(this.y < (sizeY/2)) {
                return 1;
            } else if(this.y > (sizeY/2)) {
                return 2;
            }
        } else if (this.x > (sizeX/2)) {
            if(this.y < (sizeY/2)) {
                return 3;
            } else if(this.y > (sizeY/2)) {
                return 4;
            }
        }

        return 0;
    }

}
