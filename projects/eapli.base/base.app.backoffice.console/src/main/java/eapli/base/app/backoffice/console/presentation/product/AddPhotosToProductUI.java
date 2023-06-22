package eapli.base.app.backoffice.console.presentation.product;

import com.sun.istack.NotNull;
import eapli.base.productmanagement.application.AddPhotosToProductController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class AddPhotosToProductUI extends AbstractUI {

    private final AddPhotosToProductController theController;

    public AddPhotosToProductUI(@NotNull AddPhotosToProductController theController) {
        this.theController = theController;
    }


    @Override
    protected boolean doShow() {

        final String productId = Console.readLine("Product Id");
        final Set<byte[]> photos = fillSet();
        theController.addPhotosToProduct(productId,photos);
        return false;
    }

    @Override
    public String headline() {
        return "Add photos to a Product";
    }

    private Set<byte[]> fillSet(){
        Set<byte[]> photoSet = null;
        System.out.println("Add Image Paths. When you want to stop adding images, enter 0.");
        String photoPath;
        do {
            photoPath = Console.readLine("Photo Image Path");
            if(photoPath!="0"){
                final InputStream photoStream = this.getClass().getClassLoader().getResourceAsStream(photoPath);
                if (photoStream != null) {
                    try {
                        final byte[] photo = IOUtils.toByteArray(photoStream);
                        photoSet.add(photo);
                        System.out.println("Photo added to the set.");
                    } catch (final IOException e) {
                        System.out.println("Image path is invalid.");
                    }
                }
            }
        }while(photoPath!="0");
        return photoSet;
    }
}
