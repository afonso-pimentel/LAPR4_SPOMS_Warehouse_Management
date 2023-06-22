package eapli.base.productmanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlTransient;

@Embeddable
public class Photo implements ValueObject, Comparable<Photo> {

    @JsonIgnore
    @XmlTransient
    @Lob
    private byte[] photo;

    public Photo(final byte[] photo){
        this.photo = photo;
    }

    protected Photo(){
        // for ORM
        photo = null;
    }

    public static Photo valueOf(final byte[] photo) { return new Photo(photo); }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Photo)) return false;

        final Photo that = (Photo) o;
        return this.photo.equals(that.photo);
    }

    @Override
    public int hashCode() {
        return this.photo.hashCode();
    }

    @Override
    public String toString() {
        return this.photo.toString();
    }

    @Override
    public int compareTo(Photo o) {
        return 0;
    }
}
