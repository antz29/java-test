package com.industriallogic.henrysgroceries.model;


import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ShoppingBasket {

    private Map<Product, Integer> basket = new HashMap<>();
    private LocalDate shoppingDate = LocalDate.now();

    public void addProductToBasket(Product product) {
        basket.compute(product,
                (k, v) -> {
                    return v == null ? 1 : v + 1;
                });
    }

    public void setShoppingDate(LocalDate date) {
        this.shoppingDate = date;
    }

    public LocalDate getShoppingDate() {
        return this.shoppingDate;
    }

    public Map<Product, Integer> getProductsInBasket() {
        return Collections.unmodifiableMap(basket);
    }

}



