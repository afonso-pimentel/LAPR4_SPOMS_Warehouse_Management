package eapli.base.customerusermanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.Embeddable;

@Embeddable
public class VatCode implements ValueObject, Comparable<VatCode>{

    private String code;

    public VatCode(final String vatCode) {
        if (StringPredicates.isNullOrEmpty(vatCode)) {
            throw new IllegalArgumentException(
                    "A vat code should neither be null nor empty");
        }
        this.code = vatCode;
    }

    protected VatCode() {
        // for ORM
    }

    public static VatCode valueOf(final String vatCode) {
        return new VatCode(vatCode);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VatCode)) {
            return false;
        }

        final VatCode that = (VatCode) o;
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
    public int compareTo(VatCode o) {
        return code.compareTo(o.code);
    }
}