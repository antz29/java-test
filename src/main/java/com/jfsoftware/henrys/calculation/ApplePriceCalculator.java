package com.jfsoftware.henrys.calculation;

import com.jfsoftware.henrys.ShoppingCart;
import com.jfsoftware.henrys.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

import static com.jfsoftware.henrys.calculation.PriceCalculator.calculateRawPrice;

public final class ApplePriceCalculator implements PriceCalculator {
    private final OfferSeasonDetails offerSeasonDetails;

    public ApplePriceCalculator(final OfferSeasonDetails offerSeasonDetails) {
        this.offerSeasonDetails = offerSeasonDetails;
    }

    @Override
    public BigDecimal calculateDiscountedPrice(final ShoppingCart shoppingCart) {
        offerSeasonDetails.setStartDate(LocalDate.now().plusDays(3));
        offerSeasonDetails.setEndDate(YearMonth.now().plusMonths(1).atEndOfMonth());
        offerSeasonDetails.setBuyDate(shoppingCart.calculateBuyDate());
        BigDecimal rawPrice = calculateRawPrice(shoppingCart, Product.APPLE);
        return offerSeasonDetails.isOfferInSeason() ? discountPrice(rawPrice) : rawPrice;
    }

    private BigDecimal discountPrice(final BigDecimal price) {
        return offerSeasonDetails.isOfferInSeason() ? price.multiply(new BigDecimal("0.90")) : price;
    }
}