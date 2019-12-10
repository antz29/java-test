package com.test.harrys.model;

import com.test.harrys.ShoppingTill;

import java.util.Objects;

/**
 * @author kay
 *Class encapsulates total number of a specific item purchased
 *consider this a line on a shopping list
 *i.e 6 bananas. A Collection of this instance will represent the
 *contents of a shopping basket.
 */
public class ShoppingListItem {
    private String productCode;
    private int quantity;

    public ShoppingListItem(String productCode, int quantity){
        this(productCode);
        this.quantity = quantity;
    }

    public ShoppingListItem(String productCode){
        ShoppingTill.getProductByCode(productCode);
        this.productCode = productCode;
        this.quantity = 1;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    @Override
    public boolean equals(Object o) {
        ShoppingListItem toBeCompared = (ShoppingListItem)o;
        return productCode.equals(toBeCompared.getProductCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productCode);
    }

}

