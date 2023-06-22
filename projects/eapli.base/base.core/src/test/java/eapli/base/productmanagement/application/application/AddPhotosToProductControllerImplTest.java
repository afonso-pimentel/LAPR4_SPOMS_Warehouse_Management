package eapli.base.productmanagement.application.application;

import eapli.base.productmanagement.application.AddPhotosToProductControllerImpl;
import eapli.base.productmanagement.application.domain.ProductBuilderTest;
import eapli.base.productmanagement.domain.*;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.general.domain.model.Money;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.domain.model.Role;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddPhotosToProductControllerImplTest {
    @Mock
    private ProductRepository mockProductRepository;

    @Mock
    private AuthorizationService mockAuthorizationService;

    private AddPhotosToProductControllerImpl addPhotosToProductController;

    private Product product;

    private final AlphaNumericCode INTERNAL_CODE = AlphaNumericCode.valueOf("1234A5678");

    @BeforeEach
    public void createMocks() throws IOException {
        MockitoAnnotations.openMocks(this);
        addPhotosToProductController = new AddPhotosToProductControllerImpl(mockProductRepository, mockAuthorizationService);
        doNothing().when(mockAuthorizationService).ensureAuthenticatedUserHasAnyOf(any(Role.class));
//        product = saveProductInRepo();
    }

//    private Product saveProductInRepo() throws IOException {
//        var builder = new ProductBuilder();
//        var LG_BRAND = Designation.valueOf("LG");
//        var XPTO_REFERENCE = Designation.valueOf("XPTO");
//        var CATEGORY = new ProductCategory(ProductCategoryCode.valueOf("CATEGORY12"), ProductCategoryDescription.valueOf("TEST NEW PRODUCT CATEGORY"));
//        var barcodePath = "/main/resources/barcode_example.jfif";
//        var barcodeStream = ProductBuilderTest.class.getClassLoader().getResourceAsStream(barcodePath);
//        var bars = IOUtils.toByteArray(barcodeStream);
//        var BARCODE = Barcode.valueOf("1234.4567",bars);
//        var PRICE = Money.euros(100);
//        var SHORT_DESCRIPTION = ShortDescription.valueOf("Short Description");
//        var EXTENDED_DESCRIPTION = LongDescription.valueOf("Extended Description of LG XPTO Product");
//        var photoPathOne = System.getProperty("user.dir") + "\\src\\main\\resources\\product_photo1.jpg";
//        var photoPathTwo = System.getProperty("user.dir") + "\\src\\main\\resources\\product_photo2.jpg";
//        var photos = fillSetOfPhotos(photoPathOne,photoPathTwo);
//
//        var productBuilder = builder.identifiedBy(INTERNAL_CODE).ofBrand(LG_BRAND).ofReference(XPTO_REFERENCE)
//                .fromProductCategory(CATEGORY).barcode(BARCODE).costing(PRICE).shortDescribedWith(SHORT_DESCRIPTION)
//                .extendedDescribedWith(EXTENDED_DESCRIPTION).withPhotos(photos);
//        var product = productBuilder.build();
//        mockProductRepository.save(product);
//        return product;
//    }

    private Set<Photo> fillSetOfPhotos(String photoPathOne, String photoPathTwo) throws FileNotFoundException {
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
    }

    /**
     * This test is not made properly. We should use a ofIdentity(INTERNAL_CODE).get() to obtain the product
     * but this causes some sort of incompatibility with the mockedRepository.
     *
     * @throws FileNotFoundException
     */
//    @Test
//    public void Valid_SetOfPhotosShouldAddToProductPhotos() throws FileNotFoundException {
//        //Arrange
//        when(mockProductRepository.containsOfIdentity(INTERNAL_CODE)).thenReturn(true);
//        if(mockProductRepository.containsOfIdentity(INTERNAL_CODE)) {
//            String photoPathThree = System.getProperty("user.dir") + "\\src\\main\\resources\\product_photo3.jpg";
//            InputStream photoThreeStream = new FileInputStream(photoPathThree);
//            byte[] photoThree = null;
//            try {
//                photoThree = IOUtils.toByteArray(photoThreeStream);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Photo photo = Photo.valueOf(photoThree);
//            product.addPhotoOfProduct(photo);
//            verify(mockProductRepository).save(any());
//            assertEquals(3, product.photos().size());
//        }
//    }
}