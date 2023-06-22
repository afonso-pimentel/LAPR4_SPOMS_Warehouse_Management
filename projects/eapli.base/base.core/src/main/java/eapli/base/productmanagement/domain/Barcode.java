package eapli.base.productmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

import static javax.persistence.FetchType.LAZY;

@Embeddable
public class Barcode implements ValueObject, Comparable<Barcode> {

    private final String code;

    @Lob @Basic(fetch=LAZY)
    private final byte[] bars;

    protected Barcode(final String code, final byte[] bars) {
        if (StringPredicates.isNullOrEmpty(code)) {
            throw new IllegalArgumentException(
                    "A barcode should neither be null nor empty");
        }
        if (code.length()!=13 || !isNumeric(code)){
            throw new IllegalArgumentException("In order to be compliant with EAN-13, the code must be 13 numerical digits long.");
        }
        this.code = code;
        this.bars = bars;
    }

    protected Barcode() {
        // for ORM
        code = null;
        bars = null;
    }

    public static Barcode valueOf(final String barCode, final byte[] bars) {
        return new Barcode(barCode, bars);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Barcode)) {
            return false;
        }

        final Barcode that = (Barcode) o;
        return this.code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return this.code.hashCode();
    }

    @Override
    public String toString() {
        return this.code;
    }

    @Override
    public int compareTo(Barcode o) {
        return code.compareTo(o.code);
    }

    /**
     * Checks if a string is numeric.
     * @param strNum
     * @return
     */
    private boolean isNumeric(String strNum){
        if (strNum == null) {
            return false;
        }
        try {
            long d = Long.parseLong(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
