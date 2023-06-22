/*
 * Copyright (c) 2021-2022 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.base.app.backoffice.console.presentation;

import eapli.base.app.backoffice.console.presentation.customer.RegisterCustomerAction;
import eapli.base.app.backoffice.console.presentation.order.*;
import eapli.base.app.backoffice.console.presentation.product.AddPhotosToProductAction;
import eapli.base.app.backoffice.console.presentation.product.RegisterProductAction;
import eapli.base.app.backoffice.console.presentation.product.RegisterProductCategoryAction;
import eapli.base.app.backoffice.console.presentation.product.ListProductsAction;
import eapli.base.app.backoffice.console.presentation.survey.GenerateAnswerReportAction;
import eapli.base.app.backoffice.console.presentation.survey.ListSurveyAction;
import eapli.base.app.backoffice.console.presentation.survey.RegisterSurveyAction;
import eapli.base.app.backoffice.console.presentation.warehouse.ConfigureAgvAction;
import eapli.base.app.backoffice.console.presentation.warehouse.ImportWarehousePlantAction;
import eapli.base.app.common.console.presentation.authz.MyUserMenu;
import eapli.base.Application;
import eapli.base.app.backoffice.console.presentation.authz.AddUserUI;
import eapli.base.app.backoffice.console.presentation.authz.DeactivateUserAction;
import eapli.base.app.backoffice.console.presentation.authz.ListUsersAction;
import eapli.base.app.backoffice.console.presentation.clientuser.AcceptRefuseSignupRequestAction;
import eapli.base.surveysmanagement.domain.Answer;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.base.warehousemanagement.application.ImportWarehousePlantService;
import eapli.base.warehousemanagement.domain.Warehouse;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.menu.HorizontalMenuRenderer;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import org.yaml.snakeyaml.error.Mark;

/**
 * TODO split this class in more specialized classes for each menu
 *
 * @author Paulo Gandra Sousa
 */
public class MainMenu extends AbstractUI {

    private static final String RETURN_LABEL = "Return ";

    private static final int EXIT_OPTION = 0;

    // USERS
    private static final int ADD_USER_OPTION = 1;
    private static final int LIST_USERS_OPTION = 2;
    private static final int DEACTIVATE_USER_OPTION = 3;
    private static final int ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION = 4;

    // PRODUCTS
    private static final int PRODUCT_REGISTER_OPTION = 1;
    private static final int LIST_PRODUCTS_OPTION = 2;
    private static final int ADD_PHOTOS_TO_PRODUCT_OPTION = 3;

    // PRODUCTS CATEGORIES
    private static final int REGISTER_PRODUCT_CATEGORY = 1;

    // CUSTOMERS
    private static final int ADD_CUSTOMER_OPTION = 1;

    // ORDERS
    private static final int REGISTER_ORDER_OPTION = 1;
    private static final int LIST_ORDER_OPTION = 2;
    private static final int DISPATCH_PREPARED_ORDERS_OPTION = 3;
    private static final int ASSIGN_TOBEPREPARED_ORDERS_OPTION = 4;
    private static final int MARK_DELIVERED_ORDERS_OPTION = 5;

    // SURVEYS
    private static final int REGISTER_SURVEY_OPTION = 1;
    private static final int LIST_SURVEY_OPTION = 2;
    private static final int GENERATE_SURVEY_REPORT_OPTION = 4;

    // AVGS
    // WAREHOUSE
    private static final int IMPORT_JSON_WAREHOUSE_OPTION = 1;
    private static final int CONFIGURE_AGV = 2;

    // MAIN MENU
    private static final int MY_USER_OPTION = 1;
    private static final int USERS_OPTION = 2;
    private static final int SETTINGS_OPTION = 3;
    private static final int PRODUCTS_OPTION = 4;
    private static final int PRODUCT_CATEGORIES_OPTION = 5;
    private static final int CUSTOMERS_OPTION = 6;
    private static final int WAREHOUSE_OPTION = 7;
    private static final int ORDERS_OPTION = 8;

    private static final int SURVEYS_OPTION = 9;

    private static final String SEPARATOR_LABEL = "--------------";

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final ImportWarehousePlantService impWarehouse = new ImportWarehousePlantService();

    private final Warehouse warehousePlant = impWarehouse.importWarehousePlant();

    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

    /**
     * @return true if the user selected the exit option
     */
    @Override
    public boolean doShow() {
        final Menu menu = buildMainMenu();
        final MenuRenderer renderer;
        if (Application.settings().isMenuLayoutHorizontal()) {
            renderer = new HorizontalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        } else {
            renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        }
        return renderer.render();
    }

    @Override
    public String headline() {

        return authz.session().map(s -> "Base [ @" + s.authenticatedUser().identity() + " ]")
                .orElse("Base [ ==Anonymous== ]");
    }

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();

        final Menu myUserMenu = new MyUserMenu();
        mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.ADMIN)) {
            final Menu usersMenu = buildUsersMenu();
            mainMenu.addSubMenu(USERS_OPTION, usersMenu);
            final Menu settingsMenu = buildAdminSettingsMenu();
            mainMenu.addSubMenu(SETTINGS_OPTION, settingsMenu);
        }

        mainMenu.addSubMenu(PRODUCTS_OPTION, buildProductsMenu());

        mainMenu.addSubMenu(PRODUCT_CATEGORIES_OPTION, buildProductCategoriesMenu());

        mainMenu.addSubMenu(CUSTOMERS_OPTION, buildCostumersMenu());

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.WAREHOUSE_EMPLOYEE)) {
            mainMenu.addSubMenu(WAREHOUSE_OPTION, buildWarehouseMenu());
        }

        mainMenu.addSubMenu(ORDERS_OPTION, buildOrdersMenu());

        mainMenu.addSubMenu(SURVEYS_OPTION, buildSurveysMenu());

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Bye, Bye"));

        return mainMenu;
    }

    private Menu buildAdminSettingsMenu() {
        final Menu menu = new Menu("Settings >");


        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildUsersMenu() {
        final Menu menu = new Menu("Users >");

        menu.addItem(ADD_USER_OPTION, "Add User", new AddUserUI()::show);
        menu.addItem(LIST_USERS_OPTION, "List all Users", new ListUsersAction());
        menu.addItem(DEACTIVATE_USER_OPTION, "Deactivate User", new DeactivateUserAction());
        menu.addItem(ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION, "Accept/Refuse Signup Request",
                new AcceptRefuseSignupRequestAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildProductsMenu() {
        final Menu menu = new Menu("Products >");
        menu.addItem(PRODUCT_REGISTER_OPTION, "Register new Product", new RegisterProductAction());
        menu.addItem(LIST_PRODUCTS_OPTION, "List Products", new ListProductsAction());
        menu.addItem(ADD_PHOTOS_TO_PRODUCT_OPTION, "Add photos to a Product", new AddPhotosToProductAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildProductCategoriesMenu() {
        final Menu menu = new Menu("Product Categories >");
        //Example
        //menu.addItem(DEACTIVATE_USER_OPTION, "Deactivate User", new DeactivateUserAction());

        menu.addItem(REGISTER_PRODUCT_CATEGORY, "Register Product Category", new RegisterProductCategoryAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildCostumersMenu() {
        final Menu menu = new Menu("Costumers >");
        //Example
        //menu.addItem(DEACTIVATE_USER_OPTION, "Deactivate User", new DeactivateUserAction());
        menu.addItem(ADD_CUSTOMER_OPTION,"Register a new Customer", new RegisterCustomerAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildOrdersMenu() {
        final Menu menu = new Menu("Orders >");
        //Example
        //menu.addItem(DEACTIVATE_USER_OPTION, "Deactivate User", new DeactivateUserAction());
        menu.addItem(REGISTER_ORDER_OPTION,"Register a new Order", new RegisterOrderAction());
        menu.addItem(LIST_ORDER_OPTION,"List Orders", new ListOrderAction());
        menu.addItem(DISPATCH_PREPARED_ORDERS_OPTION,"Dispatch Prepared Orders", new DispatchPreparedOrderAction());
        menu.addItem(ASSIGN_TOBEPREPARED_ORDERS_OPTION,"Assign Order To An AGV", new AssignOrderToAvgAction());
        menu.addItem(MARK_DELIVERED_ORDERS_OPTION,"Mark Order As Delivered", new MarkAsDeliveredAction());

        return menu;
    }

    private Menu buildSurveysMenu() {
        final Menu menu = new Menu("Surveys >");
        //Example
        menu.addItem(REGISTER_SURVEY_OPTION,"Register a new Survey", new RegisterSurveyAction());
        menu.addItem(LIST_SURVEY_OPTION, "List all surveys", new ListSurveyAction());
        menu.addItem(GENERATE_SURVEY_REPORT_OPTION, "Generate survey report", new GenerateAnswerReportAction());

        return menu;
    }

    private Menu buildWarehouseMenu() {
        final Menu menu = new Menu("Warehouse >");
        //Example
        menu.addItem(IMPORT_JSON_WAREHOUSE_OPTION, "Import plant", new ImportWarehousePlantAction());
        menu.addItem(CONFIGURE_AGV, "Configure AGV", new ConfigureAgvAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

}
