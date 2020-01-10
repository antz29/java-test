package com.jfsoftware.henrys.calculation;

import com.jfsoftware.henrys.ShoppingCart;
import com.jfsoftware.henrys.model.Product;

import java.math.BigDecimal;

import static com.jfsoftware.henrys.calculation.PriceCalculator.calculateRawPrice;

public class MilkPriceCalculator implements PriceCalculator {
    @Override
    public BigDecimal calculateDiscountedPrice(final ShoppingCart shoppingCart) {
        return calculateRawPrice(shoppingCart, Product.MILK);
    }
}