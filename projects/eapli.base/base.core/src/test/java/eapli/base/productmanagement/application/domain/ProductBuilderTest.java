package eapli.base.productmanagement.application.domain;

import eapli.base.productmanagement.domain.*;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.general.domain.model.Money;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductBuilderTest {

    private static final AlphaNumericCode INTERNAL_CODE = AlphaNumericCode.valueOf("1234A5678");
    private static final Designation LG_BRAND = Designation.valueOf("LG");
    private static final Designation XPTO_REFERENCE = Designation.valueOf("XPTO");
    private static final ProductCategory CATEGORY = new ProductCategory(ProductCategoryCode.valueOf("CATEGORY12"), ProductCategoryDescription.valueOf("TEST NEW PRODUCT CATEGORY"));
    private static final String barcodePath = "/main/resources/barcode_example.jfif";
    private static final InputStream barcodeStream = ProductBuilderTest.class.getClassLoader().getResourceAsStream(barcodePath);
    private static byte[] bars;
    static {
        try {
            bars = IOUtils.toByteArray(barcodeStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static final Barcode BARCODE = Barcode.valueOf("1234567890000",bars);
    private static final Money PRICE = Money.euros(100);
    private static final ShortDescription SHORT_DESCRIPTION = ShortDescription.valueOf("Short Description");
    private static final LongDescription EXTENDED_DESCRIPTION = LongDescription.valueOf("Extended Description of LG XPTO Product");
    private static final TechnicalDescription TECHNICAL_DESCRIPTION = TechnicalDescription.valueOf("Technical Description of LG XPTO Product");
    private static final Designation PRODUCT_CODE = Designation.valueOf("12345678");
    private static final StorageArea STORAGE_AREA = StorageArea.valueOf(2L,1L, 2L);
    private static final String photoPathOne = System.getProperty("user.dir") + "\\src\\main\\resources\\product_photo1.jpg";
    private static final String photoPathTwo = System.getProperty("user.dir") + "\\src\\main\\resources\\product_photo2.jpg";


    /*
    private Set<Photo> fillSetOfPhotos() throws FileNotFoundException {
        InputStream photoOneStream = new FileInputStream(photoPathOne);
        InputStream photoTwoStream =new FileInputStream(photoPathTwo);
        byte[] photoOne = null;
            try {
                photoOne = IOUtils.toByteArray(photoOneStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        byte[] photoTwo = null;
            try {
                photoTwo = IOUtils.toByteArray(photoTwoStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        Set<Photo> setPhotos = new HashSet<>();
        setPhotos.add(Photo.valueOf(photoOne));
        setPhotos.add(Photo.valueOf(photoTwo));
        return setPhotos;
    }*/


    private Product buildProduct(){
        return new ProductBuilder().identifiedBy(INTERNAL_CODE).ofBrand(LG_BRAND).ofReference(XPTO_REFERENCE)
                .fromProductCategory(CATEGORY).barcode(BARCODE).costing(PRICE).shortDescribedWith(SHORT_DESCRIPTION)
                .extendedDescribedWith(EXTENDED_DESCRIPTION).build();
    }

    /*
    private Product buildCompleteProduct() throws FileNotFoundException {

        return new ProductBuilder().identifiedBy(INTERNAL_CODE).ofBrand(LG_BRAND).ofReference(XPTO_REFERENCE)
                .fromProductCategory(CATEGORY).barcode(BARCODE).costing(PRICE).shortDescribedWith(SHORT_DESCRIPTION)
                .extendedDescribedWith(EXTENDED_DESCRIPTION).withTechnicalDescription(TECHNICAL_DESCRIPTION).withProductionCode(PRODUCT_CODE)
                .locatedIn(STORAGE_AREA).withPhotos(fillSetOfPhotos()).quantified(1L).build();
    }*/

    @Test
    public void ensureCanBuildWithOnlyMandatoryData(){
        final Product subject = buildProduct();
        assertNotNull(subject);
    }

    /*
    @Test
    public void ensureCanBuildWithAllData() throws FileNotFoundException {
        final Product subject = buildCompleteProduct();
        assertNotNull(subject);
    }*/

    @Test
    public void cannotBuildWithNullInternalCode(){
        //Arrange, Act & Assert
        assertThrows(IllegalStateException.class,() ->
                new ProductBuilder().identifiedBy(null)
                        .ofBrand(LG_BRAND)
                        .ofReference(XPTO_REFERENCE)
                        .fromProductCategory(CATEGORY)
                        .barcode(BARCODE)
                        .costing(PRICE)
                        .shortDescribedWith(SHORT_DESCRIPTION)
                        .extendedDescribedWith(EXTENDED_DESCRIPTION).build());
    }

    @Test
    public void cannotBuildWithNullBrand() {
        //Arrange, Act & Assert
        assertThrows(IllegalStateException.class, () ->
                new ProductBuilder().identifiedBy(INTERNAL_CODE)
                        .ofBrand(null)
                        .ofReference(XPTO_REFERENCE)
                        .fromProductCategory(CATEGORY)
                        .barcode(BARCODE)
                        .costing(PRICE)
                        .shortDescribedWith(SHORT_DESCRIPTION)
                        .extendedDescribedWith(EXTENDED_DESCRIPTION).build());
    }

    @Test
    public void cannotBuildWithNullReference() {
        //Arrange, Act & Assert
        assertThrows(IllegalStateException.class, () ->
                new ProductBuilder().identifiedBy(INTERNAL_CODE)
                        .ofBrand(LG_BRAND)
                        .ofReference(null)
                        .fromProductCategory(CATEGORY)
                        .barcode(BARCODE)
                        .costing(PRICE)
                        .shortDescribedWith(SHORT_DESCRIPTION)
                        .extendedDescribedWith(EXTENDED_DESCRIPTION).build());
    }

    @Test
    public void cannotBuildWithNullCategory() {
        //Arrange, Act & Assert
        assertThrows(IllegalStateException.class, () ->
                new ProductBuilder().identifiedBy(INTERNAL_CODE)
                        .ofBrand(LG_BRAND)
                        .ofReference(XPTO_REFERENCE)
                        .fromProductCategory(null)
                        .barcode(BARCODE)
                        .costing(PRICE)
                        .shortDescribedWith(SHORT_DESCRIPTION)
                        .extendedDescribedWith(EXTENDED_DESCRIPTION).build());
    }

    @Test
    public void cannotBuildWithNullBarcode() {
        //Arrange, Act & Assert
        assertThrows(IllegalStateException.class, () ->
                new ProductBuilder().identifiedBy(INTERNAL_CODE)
                        .ofBrand(LG_BRAND)
                        .ofReference(XPTO_REFERENCE)
                        .fromProductCategory(CATEGORY)
                        .barcode(null)
                        .costing(PRICE)
                        .shortDescribedWith(SHORT_DESCRIPTION)
                        .extendedDescribedWith(EXTENDED_DESCRIPTION).build());
    }

    @Test
    public void cannotBuildWithInvalidBarcode() {
        //Arrange, Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new ProductBuilder().identifiedBy(INTERNAL_CODE)
                        .ofBrand(LG_BRAND)
                        .ofReference(XPTO_REFERENCE)
                        .fromProductCategory(CATEGORY)
                        .barcode(Barcode.valueOf("123",bars))
                        .costing(PRICE)
                        .shortDescribedWith(SHORT_DESCRIPTION)
                        .extendedDescribedWith(EXTENDED_DESCRIPTION).build());
    }

    @Test
    public void cannotBuildWithNullPrice() {
        //Arrange, Act & Assert
        assertThrows(IllegalStateException.class, () ->
                new ProductBuilder().identifiedBy(INTERNAL_CODE)
                        .ofBrand(LG_BRAND)
                        .ofReference(XPTO_REFERENCE)
                        .fromProductCategory(CATEGORY)
                        .barcode(BARCODE)
                        .costing(null)
                        .shortDescribedWith(SHORT_DESCRIPTION)
                        .extendedDescribedWith(EXTENDED_DESCRIPTION).build());
    }

    @Test
    public void cannotBuildWithNullShortDescription() {
        //Arrange, Act & Assert
        assertThrows(IllegalStateException.class, () ->
                new ProductBuilder().identifiedBy(INTERNAL_CODE)
                        .ofBrand(LG_BRAND)
                        .ofReference(XPTO_REFERENCE)
                        .fromProductCategory(CATEGORY)
                        .barcode(BARCODE)
                        .costing(PRICE)
                        .shortDescribedWith(null)
                        .extendedDescribedWith(EXTENDED_DESCRIPTION).build());
    }

    @Test
    public void cannotBuildWithInvalidShortDescription() {
        //Arrange, Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new ProductBuilder().identifiedBy(INTERNAL_CODE)
                        .ofBrand(LG_BRAND)
                        .ofReference(XPTO_REFERENCE)
                        .fromProductCategory(CATEGORY)
                        .barcode(BARCODE)
                        .costing(PRICE)
                        .shortDescribedWith(ShortDescription.valueOf("Invalid Short Description For The LG XPTO PRODUCT"))
                        .extendedDescribedWith(EXTENDED_DESCRIPTION).build());
    }

    @Test
    public void cannotBuildWithNullLongDescription() {
        //Arrange, Act & Assert
        assertThrows(IllegalStateException.class, () ->
                new ProductBuilder().identifiedBy(INTERNAL_CODE)
                        .ofBrand(LG_BRAND)
                        .ofReference(XPTO_REFERENCE)
                        .fromProductCategory(CATEGORY)
                        .barcode(BARCODE)
                        .costing(PRICE)
                        .shortDescribedWith(SHORT_DESCRIPTION)
                        .extendedDescribedWith(null).build());
    }

    @Test
    public void cannotBuildWithInvalidLongDescription() {
        //Arrange, Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new ProductBuilder().identifiedBy(INTERNAL_CODE)
                        .ofBrand(LG_BRAND)
                        .ofReference(XPTO_REFERENCE)
                        .fromProductCategory(CATEGORY)
                        .barcode(BARCODE)
                        .costing(PRICE)
                        .shortDescribedWith(SHORT_DESCRIPTION)
                        .extendedDescribedWith(LongDescription.valueOf("Invalid")).build());
    }


}