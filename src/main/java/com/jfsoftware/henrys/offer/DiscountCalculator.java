package com.jfsoftware.henrys.offer;

import com.jfsoftware.henrys.model.Product;
import com.jfsoftware.henrys.model.StockItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

class DiscountCalculator {
    BigDecimal calculateAppleDiscount(final List<StockItem> basket) {
        return basket
                .stream()
                .filter(item -> item.getProduct().equals(Product.APPLE))
                .map(StockItem::getPrice)
                .map(p -> p.multiply(new BigDecimal("0.10")))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    BigDecimal calculateSoupBreadDiscount(List<StockItem> basket) {
        final Supplier<Stream<StockItem>> streamSupplier = basket::stream;
        final long soupCount = streamSupplier
                .get()
                .filter(item -> item.getProduct().equals(Product.SOUP))
                .count();
        final long breadCount = streamSupplier
                .get()
                .filter(item -> item.getProduct().equals(Product.BREAD))
                .count();
        if (breadDiscountApplicable(soupCount, breadCount)) {
            return calculateBreadDiscount(soupCount);
        }
        return new BigDecimal("0.00");
    }

    private boolean breadDiscountApplicable(long soupCount, long breadCount) {
        return soupCount >= 2 && breadCount > 0;
    }

    private BigDecimal calculateBreadDiscount(long soupCount) {
        final long halfPriceBreadCount = soupCount / 2;
        final BigDecimal priceOfHalfPriceLoaves = Product.BREAD
                .getPrice()
                .multiply(new BigDecimal("0.5"));
        return priceOfHalfPriceLoaves
                .multiply(new BigDecimal(halfPriceBreadCount))
                .setScale(2, RoundingMode.HALF_UP);
    }
}