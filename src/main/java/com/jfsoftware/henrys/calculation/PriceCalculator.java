package com.jfsoftware.henrys.calculation;

import com.jfsoftware.henrys.Cart;
import com.jfsoftware.henrys.model.Item;
import com.jfsoftware.henrys.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface PriceCalculator {
    BigDecimal calculateDiscountedPrice(final Cart cart);

    static BigDecimal calculateRawPrice(final Cart cart, final Product product) {
        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().equals(product))
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }
}