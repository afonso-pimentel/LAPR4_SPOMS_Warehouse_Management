package eapli.base.productmanagement.domain;

import eapli.base.TaxValues;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.general.domain.model.Description;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.general.domain.model.Money;
import eapli.framework.representations.RepresentationBuilder;
import eapli.framework.representations.Representationable;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Set;

@Entity
public class Product implements AggregateRoot<AlphaNumericCode>, Representationable {

    @Id
    @AttributeOverride(name = "code", column = @Column(name = "internalCode"))
    private AlphaNumericCode internalCode;

    @AttributeOverride(name = "name", column = @Column(name = "brand"))
    private Designation brand;

    @AttributeOverride(name = "name", column = @Column(name = "reference"))
    private Designation reference;

    @ManyToOne
    private ProductCategory category;

    @AttributeOverride(name = "code", column = @Column(name = "barcode"))
    private Barcode barcode;

    @AttributeOverride(name = "amount", column = @Column(name = "price"))
    private Money price;

    @AttributeOverride(name = "value", column = @Column(name = "shortDescription"))
    private ShortDescription shortDescription;

    @AttributeOverride(name = "value", column = @Column(name = "extendedDescription"))
    private LongDescription extendedDescription;

    @AttributeOverride(name = "name", column = @Column(name = "productCode"))
    private Designation productCode;

    @AttributeOverride(name = "value", column = @Column(name = "technicalDescription"))
    private TechnicalDescription technicalDescription;

    private StorageArea storageArea;

    @ElementCollection
    private Set<Photo> photos;

    private Long quantity;

    private boolean active;

    /**Constructor for the product.
     *
     * @param internalCode
     * @param brand
     * @param reference
     * @param category
     * @param barcode
     * @param price
     * @param shortDescription
     * @param extendedDescription
     * @param technicalDescription
     * @param productCode
     * @param storageArea
     * @param photos
     * @param quantity
     */
    public Product(AlphaNumericCode internalCode, Designation brand, Designation reference, ProductCategory category,
                   Barcode barcode, Money price, ShortDescription shortDescription, LongDescription extendedDescription, TechnicalDescription technicalDescription,
                   Designation productCode, StorageArea storageArea, Set<Photo> photos, Long quantity) {
        Preconditions.noneNull(internalCode,brand,reference,category,barcode,price,shortDescription,extendedDescription);

        this.internalCode = internalCode;
        this.brand = brand;
        this.reference = reference;
        this.category = category;
        this.barcode = barcode;
        this.price = price;
        this.shortDescription = shortDescription;
        this.extendedDescription = extendedDescription;

        //Optional fields, they can be defined later
        this.technicalDescription = technicalDescription;
        this.productCode = productCode;
        this.storageArea = storageArea;
        this.photos = photos;
        this.quantity = quantity;

        //Default value
        this.active = true;
    }

    public Product() {
        //For ORM
    }

    @Override
    public boolean equals(final Object o) { return DomainEntities.areEqual(this,o);}

    @Override
    public boolean sameAs(final Object other) {
        if (!(other instanceof Product)) {
            return false;
        }

        final Product that = (Product) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity()) && brand.equals(that.brand) && reference.equals(that.reference) && category.equals(that.category) &&
                barcode.equals(that.barcode) && price.equals(that.price) && shortDescription.equals(that.shortDescription) && extendedDescription.equals(that.extendedDescription);
    }

    @Override
    public int compareTo(AlphaNumericCode other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public AlphaNumericCode identity() {
        return this.internalCode;
    }

    @Override
    public boolean hasIdentity(AlphaNumericCode id) {
        return AggregateRoot.super.hasIdentity(id);
    }

    /**
     * Builder of Product
     * @param builder
     * @param <R>
     * @return
     */
    public <R> R buildRepresentation(final RepresentationBuilder<R> builder) {
        builder.startObject("Product")
                .withProperty("internalCode", String.valueOf(internalCode)) //check this
                .withProperty("brand", brand)
                .withProperty("reference", reference)
                .withProperty("category", String.valueOf(category))  //check this
                .withProperty("barcode", String.valueOf(barcode)) //check this
                .withProperty("price", price)
                .withProperty("shortDescription", String.valueOf(shortDescription))
                .withProperty("extendedDescription", String.valueOf(extendedDescription));
        if (photos != null) builder.withProperty("photos", (BigInteger) photos); //check this, might need to change it to startCollection
        if (productCode != null) builder.withProperty("productCode", productCode);
        if (technicalDescription != null) builder.withProperty("technicalDescription", String.valueOf(technicalDescription));
        if (storageArea != null) builder.withProperty("storageArea", String.valueOf(storageArea)); //check this
        if (quantity != 0) builder.withProperty("quantity", quantity);
        builder.endObject();
        return builder.build();
    }

    /**
     * Update the product's properties. The codes and identity cannot be changed.
     * @param shortDescription
     * @param extendedDescription
     * @param technicalDescription
     * @param price
     * @param storageArea
     */
    public void update(final ShortDescription shortDescription, final LongDescription extendedDescription,
                       final TechnicalDescription technicalDescription, final Money price,
                       final StorageArea storageArea) {
        Preconditions.noneNull(shortDescription,extendedDescription,technicalDescription,price,storageArea);

        this.price = price;
        this.shortDescription = shortDescription;
        this.extendedDescription = extendedDescription;
        this.technicalDescription = technicalDescription;
        this.storageArea = storageArea;
        changePriceTo(price);
        changeShortDescriptionTo(shortDescription);
        changeExtendedDescriptionTo(extendedDescription);
        changeTechnicalDescriptionTo(technicalDescription);
        changeStorageArea(storageArea);

    }

    public Designation brand(){ return this.brand; }
    public Designation reference() { return this.reference; }
    public ProductCategory category() { return this.category; }
    public Money price() { return this.price; }
    public Money priceWithTaxes() { return price.multiply(TaxValues.IVA.getValue()); }
    public Barcode barcode() { return this.barcode; }
    public ShortDescription shortDescription() {return this.shortDescription; }
    public LongDescription extendedDescription() {return this.extendedDescription; }
    public TechnicalDescription technicalDescription() { return this.technicalDescription; }
    public Designation productCode() { return this.productCode; }
    public StorageArea storageArea() { return this.storageArea; }
    public Set<Photo> photos() { return this.photos; }
    public Long quantity() { return this.quantity; }

    /**
     * Changes price value
     * @param newPrice
     */
    public void changePriceTo(final Money newPrice) { price = newPrice; }

    /**
     * Changes shortDescription
     * @param newShortDescription
     */
    public void changeShortDescriptionTo(final ShortDescription newShortDescription) { shortDescription = newShortDescription; }

    /**
     * Changes extendedDescription
     * @param newExtendedDescription
     */
    public void changeExtendedDescriptionTo(final LongDescription newExtendedDescription) { extendedDescription = newExtendedDescription; }

    /**
     * Changes technicalDescription
     * @param newTechnicalDescription
     */
    public void changeTechnicalDescriptionTo(final TechnicalDescription newTechnicalDescription) { technicalDescription = newTechnicalDescription; }

    /**
     * Changes storageArea
     * @param newStorageArea
     */
    public void changeStorageArea(final StorageArea newStorageArea) { storageArea = newStorageArea; }

    /**
     * Adds photo to set of Photos
     * @param newPhoto
     */
    public void addPhotoOfProduct(final Photo newPhoto){ photos.add(newPhoto); }

    /**
     * Returns the state of the product.
     * @return
     */
    public boolean isActive(){ return this.active;  }

    /**
     * Changes the state of the product to the opposite.
     * @return
     */
    public boolean toggleState(){
        this.active = !this.active;
        return isActive();
    }
}
