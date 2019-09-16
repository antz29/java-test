package com.ford.henrysgroceries.offers;

import com.ford.henrysgroceries.Basket;
import com.ford.henrysgroceries.products.Product;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.ford.henrysgroceries.products.ProductHelper.apples;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

public class TenPercentOffApplesOffer implements Offer {

    private final LocalDate start;
    private final LocalDate end;

    public TenPercentOffApplesOffer(LocalDate today) {
        start = today.plusDays(3);
        end = today.plusMonths(1).with(lastDayOfMonth());
    }

    @Override
    public Basket apply(Basket basket, LocalDate date) {
        if (notApplicable(date))
            return basket;

        basket.getProducts().stream()
                .filter(product -> product.getName().equals(apples().getName()))
                .forEach(product -> product.setDiscountPrice(setDiscountPrice(product)));
        return basket;
    }

    private boolean notApplicable(LocalDate date) {
        return date.isBefore(start) || date.isAfter(end);
    }

    private BigDecimal setDiscountPrice(Product product) {
        BigDecimal price = product.getPrice();
        BigDecimal percentage = new BigDecimal(10);
        BigDecimal discount = price.divide(new BigDecimal("100.00")).multiply(percentage);
        return price.subtract(discount);
    }
}
