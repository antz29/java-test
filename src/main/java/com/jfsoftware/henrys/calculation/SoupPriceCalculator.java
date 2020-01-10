package com.jfsoftware.henrys.calculation;

import com.jfsoftware.henrys.ShoppingCart;
import com.jfsoftware.henrys.model.Product;

import java.math.BigDecimal;

import static com.jfsoftware.henrys.calculation.PriceCalculator.calculateRawPrice;

public class SoupPriceCalculator implements PriceCalculator {
    @Override
    public BigDecimal calculateDiscountedPrice(final  ShoppingCart shoppingCart) {
        return calculateRawPrice(shoppingCart, Product.SOUP);
    }
}