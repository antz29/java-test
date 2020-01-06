package com.jfsoftware.henrys.offer;

import com.jfsoftware.henrys.ShoppingContext;
import com.jfsoftware.henrys.model.Product;
import com.jfsoftware.henrys.model.StockItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

public class AppleOffer implements Offer {

    private OfferSeasonDetails offerSeasonDetails = new OfferSeasonDetails();

    @Override
    public BigDecimal calculateOfferDiscount(final ShoppingContext shoppingContext) {
        offerSeasonDetails.setStartDate(LocalDate.now().plusDays(3));
        offerSeasonDetails.setEndDate(YearMonth.now().plusMonths(1).atEndOfMonth());
        offerSeasonDetails.setBuyDate(shoppingContext.calculateBuyDate());
        return offerSeasonDetails.isOfferInSeason() ?
                calculateOfferDiscount(shoppingContext.getBasket()) : new BigDecimal("0.00");
    }

    private BigDecimal calculateOfferDiscount(final List<StockItem> basket) {
        return basket
                .stream()
                .filter(item -> item.getProduct().equals(Product.APPLE))
                .map(StockItem::getPrice)
                .map(p -> p.multiply(new BigDecimal("0.10")))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppleOffer that = (AppleOffer) o;
        return offerSeasonDetails.equals(that.offerSeasonDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offerSeasonDetails);
    }
}