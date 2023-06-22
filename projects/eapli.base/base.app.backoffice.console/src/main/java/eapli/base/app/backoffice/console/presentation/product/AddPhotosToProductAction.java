package eapli.base.app.backoffice.console.presentation.product;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productmanagement.application.AddPhotosToProductControllerImpl;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 * Menu action for adding Photos to a product.
 */
public class AddPhotosToProductAction implements Action {

    private final AddPhotosToProductUI addPhotosToProductUI;

    public AddPhotosToProductAction() {
        var productRepository = PersistenceContext.repositories().products();

        var controller = new AddPhotosToProductControllerImpl(productRepository, AuthzRegistry.authorizationService());

        this.addPhotosToProductUI = new AddPhotosToProductUI(controller);
    }


    @Override
    public boolean execute() { return this.addPhotosToProductUI.show(); }
}
