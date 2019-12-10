package com.test.harrys.basket;

import com.test.harrys.ShoppingTill;
import com.test.harrys.model.ShoppingListItem;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * @author kay
 *encapsulates a shopping basket
 */
public class ShoppingBasket {
    final static Logger LOGGER = Logger.getLogger(ShoppingBasket.class);

    private final Set<ShoppingListItem> listItem = new HashSet<>();

    public ShoppingBasket() {

    }

    /**
     * adds one or more items of a specific product to a shopping basket
     * @param item shopping list item
     */
    public void addItem(ShoppingListItem item) {

    }

    public static BigDecimal getProductPrice(String productCode) {
        return ShoppingTill.getProductPrice(productCode);
    }

    public Collection<ShoppingListItem> getShoppingListItems() {
        return this.listItem;
    }

}
