package eapli.base.productmanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class StorageArea implements ValueObject, Comparable<StorageArea> {

    //private String storageArea;
    private Long aisle;
    private Long row;
    private Long shelf;

    protected StorageArea(final Long aisle, final Long row, final Long shelf){
        if (aisle == null){
            throw new IllegalArgumentException(
                    "A aisle should not be null.");
        }
        if (row == null){
            throw new IllegalArgumentException(
                    "A row should not be null.");
        }
        if (shelf == null){
            throw new IllegalArgumentException(
                    "A shelf should not be null.");
        }

        this.aisle = aisle;
        this.row = row;
        this.shelf = shelf;
    }

    protected StorageArea(){
        // for ORM
    }

    public static StorageArea valueOf(final Long aisle, final Long row, final Long shelf){
        return new StorageArea(aisle, row, shelf);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StorageArea)) {
            return false;
        }

        final StorageArea that = (StorageArea) o;
        return this.aisle.equals(that.aisle) && this.row.equals(that.row) && this.shelf.equals(that.shelf);
    }

    @Override
    public String toString() { return aisle + " -> " + row + " -> " + shelf; }

    @Override
    public int hashCode() { return this.toString().hashCode(); }

    @Override
    public int compareTo(StorageArea o) {
        if(this.aisle == o.aisle && this.row == o.row && this.shelf == o.shelf)
            return 0;
        return 1;
    }

    /**
     * Returns the product aisle
     * @return Long
     */
    public Long aisle(){
        return aisle;
    }

    /**
     * Returns the product row
     * @return Long
     */
    public Long row(){
        return row;
    }

    /**
     * Returns the product shelf
     * @return Long
     */
    public Long shelf(){
        return shelf;
    }
}
