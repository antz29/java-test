package com.jfsoftware.henrys.offer;

import com.jfsoftware.henrys.ShoppingContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

public class AppleOffer implements Offer {

    private OfferSeasonDetails offerSeasonDetails = new OfferSeasonDetails();
    private DiscountCalculator discountCalculator = new DiscountCalculator();

    @Override
    public BigDecimal calculateOfferDiscount(final ShoppingContext shoppingContext) {
        offerSeasonDetails.setStartDate(LocalDate.now().plusDays(3));
        offerSeasonDetails.setEndDate(YearMonth.now().plusMonths(1).atEndOfMonth());
        offerSeasonDetails.setBuyDate(shoppingContext.calculateBuyDate());
        return offerSeasonDetails.isOfferInSeason() ?
                discountCalculator.calculateAppleDiscount(shoppingContext.getBasket()) : new BigDecimal("0.00");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppleOffer that = (AppleOffer) o;
        return offerSeasonDetails.equals(that.offerSeasonDetails) &&
                discountCalculator.equals(that.discountCalculator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offerSeasonDetails, discountCalculator);
    }
}