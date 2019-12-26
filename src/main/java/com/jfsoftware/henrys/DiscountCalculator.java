package com.jfsoftware.henrys;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class DiscountCalculator {
    public BigDecimal calculateAppleDiscount(final List<StockItem> basket) {
        return basket
                .stream()
                .filter(item -> item.getProduct().equals(Product.APPLE))
                .map(StockItem::getPrice)
                .map(p -> p.multiply(new BigDecimal("0.10")))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }
}