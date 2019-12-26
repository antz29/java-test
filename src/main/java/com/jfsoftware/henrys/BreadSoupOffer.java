package com.jfsoftware.henrys;

import java.math.BigDecimal;
import java.time.LocalDate;

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
}