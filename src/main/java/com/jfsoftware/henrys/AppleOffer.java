package com.jfsoftware.henrys;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

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
}