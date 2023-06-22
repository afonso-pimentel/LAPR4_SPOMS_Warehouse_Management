package eapli.base;

public enum TaxValues {
    IVA(1.23);

    private double value;

    TaxValues(double value) {
        this.value = value;
    }

    public double getValue(){
        return value;
    }
}
