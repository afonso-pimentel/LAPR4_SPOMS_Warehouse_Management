package eapli.base.ordersmanagement.domain;

import eapli.framework.general.domain.model.Money;

public enum ShipmentMethod {
    CTT(Money.euros(25)),
    UPS(Money.euros(30)),
    DHL(Money.euros(28));

    private Money cost;

    ShipmentMethod(Money euros) {
        this.cost = euros;
    }

    public Money getCost(){
        return cost;
    }
}
