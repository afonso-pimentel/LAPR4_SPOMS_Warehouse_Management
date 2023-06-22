package algorithms;

public class Coordinate {
    int r;
    int c;
    Coordinate parent;

    public Coordinate(int row, int col) {
        this.r = row;
        this.c = col;
        this.parent = null;
    }

    public Coordinate(int row, int col, Coordinate parent) {
        this.r = row;
        this.c = col;
        this.parent = parent;
    }

    public int getRow() {
        return r;
    }

    public int getCol() {
        return c;
    }
}
