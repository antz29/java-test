package com.jfsoftware.henrys.offer;

import com.jfsoftware.henrys.ShoppingContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class BreadSoupOffer implements Offer {
    private OfferSeasonDetails offerSeasonDetails = new OfferSeasonDetails();
    private DiscountCalculator discountCalculator = new DiscountCalculator();

    @Override
    public BigDecimal calculateOfferDiscount(final ShoppingContext shoppingContext) {
        final LocalDate yesterday = LocalDate.now().minusDays(1);
        final LocalDate sevenDaysFromYesterday = yesterday.plusDays(7);
        offerSeasonDetails.setStartDate(yesterday);
        offerSeasonDetails.setEndDate(sevenDaysFromYesterday);
        offerSeasonDetails.setBuyDate(shoppingContext.calculateBuyDate());
        return offerSeasonDetails.isOfferInSeason() ?
                discountCalculator.calculateSoupBreadDiscount(shoppingContext.getBasket()) : new BigDecimal("0.00");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BreadSoupOffer that = (BreadSoupOffer) o;
        return offerSeasonDetails.equals(that.offerSeasonDetails) &&
                discountCalculator.equals(that.discountCalculator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offerSeasonDetails, discountCalculator);
    }
}