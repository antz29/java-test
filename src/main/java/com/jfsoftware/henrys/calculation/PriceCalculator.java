package com.jfsoftware.henrys.calculation;

import com.jfsoftware.henrys.ShoppingCart;
import com.jfsoftware.henrys.model.Item;
import com.jfsoftware.henrys.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface PriceCalculator {
    BigDecimal calculateDiscountedPrice(final ShoppingCart shoppingCart);

    static BigDecimal calculateRawPrice(final ShoppingCart shoppingCart, final Product product) {
        return shoppingCart.getItems()
                .stream()
                .filter(item -> item.getProduct().equals(product))
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }
}