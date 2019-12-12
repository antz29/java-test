package com.test.harrys.basket;

import com.test.harrys.model.ShoppingListItem;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * @author kay
 *encapsulates a shopping basket
 */
public class ShoppingBasket {
    private LocalDate shoppingDate;

    final static Logger LOGGER = Logger.getLogger(ShoppingBasket.class);

    private final Set<ShoppingListItem> listItem = new HashSet<>();

    public ShoppingBasket() {
        shoppingDate = LocalDate.now();
    }

    /**
     * adds one or more items of a specific product to a shopping basket
     * @param item shopping list item
     */
    public void addItem(ShoppingListItem item) {
        if(!listItem.add(item)){
            listItem.stream().filter(lItem -> lItem.getProductCode().equals(item.getProductCode())).
                    findAny().ifPresent(lItem -> lItem.increaseQuantity(item.getQuantity()));
        }
    }

    public Collection<ShoppingListItem> getShoppingListItems() {
        return this.listItem;
    }

    public LocalDate getShoppingDate() {
        return shoppingDate;
    }

    public void setShoppingDate(LocalDate shoppingDate) {
        this.shoppingDate = shoppingDate;
    }
}
