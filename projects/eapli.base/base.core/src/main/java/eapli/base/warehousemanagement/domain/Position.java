package eapli.base.warehousemanagement.domain;

public class Position {

    private int lSquare;
    private int wSquare;

    public Position(final int lSquare, final int wSquare) throws IllegalArgumentException {
        if (lSquare <= 0 || wSquare <= 0)
            throw new IllegalArgumentException("Invalid Position");

        this.lSquare = lSquare;
        this.wSquare = wSquare;
    }

    protected Position() {
        // for ORM
    }

    public String toString() {
        String strLsquare = Integer.toString(this.lSquare);
        String strWsquare = Integer.toString(this.wSquare);

        return "{" + strLsquare + ", " + strWsquare + "}";
    }

    public int getlSquare() {
        return lSquare;
    }

    public int getwSquare() {
        return wSquare;
    }

    public void setlSquare(int lSquare) {
        this.lSquare = lSquare;
    }

    public void setwSquare(int wSquare) {
        this.wSquare = wSquare;
    }

    

    
}
