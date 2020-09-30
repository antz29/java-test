package com.jfsoftware.henrys.calculation;

import com.jfsoftware.henrys.Cart;
import com.jfsoftware.henrys.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

public final class ApplePriceCalculator implements PriceCalculator {

    @Override
    public BigDecimal calculateDiscountedPrice(final Cart cart) {
        SeasonOfferRules.getInstance().setStartDate(LocalDate.now().plusDays(3));
        SeasonOfferRules.getInstance().setEndDate(YearMonth.now().plusMonths(1).atEndOfMonth());
        BigDecimal rawPrice = PriceCalculator.calculateRawPrice(cart, Product.APPLE);
        return SeasonOfferRules.getInstance().isOfferInSeason() ? discountPrice(rawPrice) : rawPrice;
    }

    private BigDecimal discountPrice(final BigDecimal price) {
        return SeasonOfferRules.getInstance().isOfferInSeason() ? price.multiply(new BigDecimal(0.90)) : price;
    }
}