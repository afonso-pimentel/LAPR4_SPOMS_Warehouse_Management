package eapli.base.app.backoffice.console.presentation.product;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productmanagement.application.ListProductCategoryService;
import eapli.base.productmanagement.application.RegisterProductControllerImpl;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class RegisterProductAction implements Action {

    private final RegisterProductUI registerProductUI;

    public RegisterProductAction() {
        var productRepository = PersistenceContext.repositories().products();

        var productCategoriesRepository = PersistenceContext.repositories().productCategories();

        var listProductCategoryService = new ListProductCategoryService(productCategoriesRepository);

        var theController = new RegisterProductControllerImpl(productRepository, listProductCategoryService, AuthzRegistry.authorizationService());

        this.registerProductUI = new RegisterProductUI(theController);

    }

    @Override
    public boolean execute() {
        return this.registerProductUI.show();
    }
}
