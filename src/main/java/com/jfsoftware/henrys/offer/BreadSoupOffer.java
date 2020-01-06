package com.jfsoftware.henrys.offer;

import com.jfsoftware.henrys.ShoppingContext;
import com.jfsoftware.henrys.model.Product;
import com.jfsoftware.henrys.model.StockItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class BreadSoupOffer implements Offer {
    private OfferSeasonDetails offerSeasonDetails = new OfferSeasonDetails();

    @Override
    public BigDecimal calculateOfferDiscount(final ShoppingContext shoppingContext) {
        final LocalDate yesterday = LocalDate.now().minusDays(1);
        final LocalDate sevenDaysFromYesterday = yesterday.plusDays(7);
        offerSeasonDetails.setStartDate(yesterday);
        offerSeasonDetails.setEndDate(sevenDaysFromYesterday);
        offerSeasonDetails.setBuyDate(shoppingContext.calculateBuyDate());
        return offerSeasonDetails.isOfferInSeason() ?
                calculateOfferDiscount(shoppingContext.getBasket()) : new BigDecimal("0.00");
    }

    BigDecimal calculateOfferDiscount(List<StockItem> basket) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BreadSoupOffer that = (BreadSoupOffer) o;
        return offerSeasonDetails.equals(that.offerSeasonDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offerSeasonDetails);
    }
}