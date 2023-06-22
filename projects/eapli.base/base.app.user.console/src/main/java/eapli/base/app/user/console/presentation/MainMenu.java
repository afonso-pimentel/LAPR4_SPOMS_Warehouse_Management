/*
 * Copyright (c) 2013-2019 the original author or authors.
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
package eapli.base.app.user.console.presentation;

import eapli.base.app.common.console.presentation.authz.MyUserMenu;
import eapli.base.app.user.console.presentation.order.ListOrderStatusAction;
import eapli.base.app.user.console.presentation.product.ListProductsAction;
import eapli.base.app.user.console.presentation.product.ListShoppingCartAction;
import eapli.base.app.user.console.presentation.product.ShoppingCartAction;
import eapli.base.app.user.console.presentation.surveys.AnswerSurveyAction;
import eapli.base.app.user.console.presentation.surveys.ListSurveysAction;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

/**
 * @author Paulo Gandra Sousa
 */
public class MainMenu extends ClientUserBaseUI {

    private static final String SEPARATOR_LABEL = "--------------";

    private static final String RETURN = "Return ";

    private static final String NOT_IMPLEMENTED_YET = "Not implemented yet";

    private static final int EXIT_OPTION = 0;

    // MAIN MENU
    private static final int MY_USER_OPTION = 1;
    private static final int PRODUCTS_OPTION = 2;
    private static final int ORDERS_OPTION = 4;



    //PRODUCTS
    private static final int LIST_PRODUCTS_OPTION = 1;
    private static final int ADD_PRODUCT_SHOPPING_CART_OPTION = 2;
    private static final int SHOW_SHOPPING_CART = 3;

    private static final int ACCOUNT_OPTION = 5;
    private static final int SETTINGS_OPTION = 6;

    // BOOKINGS MENU
    private static final int BOOK_A_MEAL_OPTION = 2;


    // ACCOUNT MENU
    private static final int LIST_MOVEMENTS_OPTION = 1;

    // SETTINGS
    private static final int SET_USER_ALERT_LIMIT_OPTION = 1;

    // SURVEYS
    private static final int SURVEYS_OPTION = 3;

    private static final int LIST_SURVEYS_OPTION = 1;

    private static final int ANSWER_SURVEY_OPTION = 2;

    // ORDERS

    private static final int CHECK_ORDERS_STATUS = 1;

    private final AuthorizationService authz =
            AuthzRegistry.authorizationService();

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
        final MenuRenderer renderer =
                new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();

        final Menu myUserMenu = new MyUserMenu();
        mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);

        mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        mainMenu.addSubMenu(PRODUCTS_OPTION, buildProductsMenu());
        mainMenu.addSubMenu(SURVEYS_OPTION, buildSurveysMenu());
        mainMenu.addSubMenu(ORDERS_OPTION, buildOrdersMenu());
        mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Bye, Bye"));
        return mainMenu;
    }

    private Menu buildProductsMenu() {
        final Menu menu = new Menu("Products >");
        menu.addItem(LIST_PRODUCTS_OPTION, "List Products", new ListProductsAction());
        menu.addItem(ADD_PRODUCT_SHOPPING_CART_OPTION, "Add Product to Shopping Cart", new ShoppingCartAction());
        menu.addItem(SHOW_SHOPPING_CART,"Show current shopping cart", new ListShoppingCartAction());
        menu.addItem(EXIT_OPTION, "Exit", Actions.SUCCESS);

        return menu;
    }

    private Menu buildSurveysMenu(){
        final Menu menu = new Menu("Surveys >");
        menu.addItem(LIST_SURVEYS_OPTION, "List Surveys", new ListSurveysAction());
        menu.addItem(ANSWER_SURVEY_OPTION, "Answer survey", new AnswerSurveyAction());
        return menu;
    }

    private Menu buildOrdersMenu(){
        final Menu menu = new Menu("Orders >");
        menu.addItem(CHECK_ORDERS_STATUS,"Orders Status", new ListOrderStatusAction());
        menu.addItem(EXIT_OPTION, "Exit", Actions.SUCCESS);
        return menu;
    }
}
