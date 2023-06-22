package eapli.base.ordersmanagement.domain;

import eapli.framework.domain.model.ValueObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime implements ValueObject, Comparable<DateTime> {

    private Date currentDate;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public DateTime(){
        currentDate = new Date();
    }

    @Override
    public String toString() {
        return formatter.format(currentDate);
    }

    @Override
    public int compareTo(DateTime o) {
        return 0;
    }
}
