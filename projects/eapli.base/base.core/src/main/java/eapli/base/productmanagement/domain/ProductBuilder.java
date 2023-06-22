package eapli.base.productmanagement.domain;

import eapli.framework.domain.model.DomainFactory;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.general.domain.model.Money;

import java.util.Set;

public class ProductBuilder implements DomainFactory<Product> {

    private Product theProduct;

    //Mandatory
    private AlphaNumericCode internalCode;
    private Designation brand;
    private Designation reference;
    private ProductCategory category;
    private Barcode barcode;
    private Money price;
    private ShortDescription shortDescription;
    private LongDescription extendedDescription;

    //Optional
    private TechnicalDescription technicalDescription = null;
    private Designation productCode = null;
    private StorageArea storageArea = null;
    private Set<Photo> photos = null;
    private Long quantity = 0L;

    //Default
    private boolean active;

    //Mandatory Attributes Building Methods

    public ProductBuilder identifiedBy(final AlphaNumericCode internalCode){
        this.internalCode = internalCode;
        return this;
    }

    public ProductBuilder ofBrand(final Designation brand){
        this.brand = brand;
        return this;
    }

    public ProductBuilder ofReference(final Designation reference){
        this.reference = reference;
        return this;
    }

    public ProductBuilder costing(final Money price){
        this.price = price;
        return this;
    }

    public ProductBuilder shortDescribedWith(final ShortDescription shortDescription){
        this.shortDescription = shortDescription;
        return this;
    }

    public ProductBuilder extendedDescribedWith(final LongDescription extendedDescription){
        this.extendedDescription = extendedDescription;
        return this;
    }

    public ProductBuilder fromProductCategory(final ProductCategory category){
        this.category = category;
        return this;
    }

    public ProductBuilder barcode(final Barcode barcode){
        this.barcode = barcode;
        return this;
    }

    private Product buildOrThrow(){
        if (theProduct != null){
            return theProduct;
        } else if (internalCode != null && brand != null &&
                reference != null && category != null &&
                barcode != null && price != null &&
                shortDescription != null && extendedDescription != null ){

            theProduct = new Product(internalCode, brand, reference, category, barcode, price, shortDescription, extendedDescription,
                        technicalDescription, productCode, storageArea, photos, quantity);
            return theProduct;
        }else{
            throw new IllegalStateException();
        }
    }

    //Optional Attributes Building Methods

    public ProductBuilder withPhotos(final Set<Photo> photos){
       if (photos != null)
           this.photos = photos;
       return this;
    }

    public ProductBuilder withPhoto(final Photo photo){
        theProduct.addPhotoOfProduct(photo);
        return this;
    }

    public ProductBuilder withProductionCode(final Designation productionCode){
        this.productCode = productCode;
        return this;
    }

    public ProductBuilder withTechnicalDescription(final TechnicalDescription technicalDescription){
        this.technicalDescription = technicalDescription;
        return this;
    }

    public ProductBuilder locatedIn(final StorageArea storageArea){
        this.storageArea = storageArea;
        return this;
    }

    public ProductBuilder quantified(final Long quantity){
        this.quantity = quantity;
        return this;
    }

    @Override
    public Product build() {
        final Product ret = buildOrThrow();
        theProduct = null;
        return ret;
    }
}
