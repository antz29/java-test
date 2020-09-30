package com.jfsoftware.henrys.calculation;

import com.jfsoftware.henrys.Cart;
import com.jfsoftware.henrys.model.Product;

import java.math.BigDecimal;

public class MilkPriceCalculator implements PriceCalculator {
    @Override
    public BigDecimal calculateDiscountedPrice(final Cart cart) {
        return PriceCalculator.calculateRawPrice(cart, Product.MILK);
    }
}