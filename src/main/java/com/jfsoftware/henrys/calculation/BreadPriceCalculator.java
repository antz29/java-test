package com.jfsoftware.henrys.calculation;

import com.jfsoftware.henrys.ShoppingCart;
import com.jfsoftware.henrys.model.Item;
import com.jfsoftware.henrys.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.jfsoftware.henrys.calculation.PriceCalculator.calculateRawPrice;

public final class BreadPriceCalculator implements PriceCalculator {
    private final OfferSeasonDetails offerSeasonDetails;
    private ShoppingCart shoppingCart;

    public BreadPriceCalculator(final OfferSeasonDetails offerSeasonDetails) {
        this.offerSeasonDetails = offerSeasonDetails;
    }

    @Override
    public BigDecimal calculateDiscountedPrice(final ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        final LocalDate yesterday = LocalDate.now().minusDays(1);
        final LocalDate sevenDaysFromYesterday = yesterday.plusDays(7);
        offerSeasonDetails.setStartDate(yesterday);
        offerSeasonDetails.setEndDate(sevenDaysFromYesterday);
        offerSeasonDetails.setBuyDate(shoppingCart.calculateBuyDate());
        BigDecimal rawPrice = calculateRawPrice(shoppingCart, Product.BREAD);

        return offerSeasonDetails.isOfferInSeason() ? discountPrice(rawPrice) : rawPrice;
    }

    private BigDecimal discountPrice(final BigDecimal price) {
        final Supplier<Stream<Item>> streamSupplier = () -> shoppingCart.getItems().stream();
        final long soupCount = streamSupplier
                .get()
                .filter(item -> item.getProduct().equals(Product.SOUP))
                .count();
        final long breadCount = streamSupplier
                .get()
                .filter(item -> item.getProduct().equals(Product.BREAD))
                .count();
        if (breadDiscountApplicable(soupCount, breadCount)) {
            return price.subtract(discountCountBread(soupCount));
        }
        return price;
    }

    private boolean breadDiscountApplicable(final long soupCount, final long breadCount) {
        return soupCount >= 2 && breadCount > 0;
    }

    private BigDecimal discountCountBread(final long soupCount) {
        final long halfPriceBreadFactor = soupCount / 2;
        final BigDecimal priceOfHalfPriceLoaves = Product.BREAD
                .getPrice()
                .multiply(new BigDecimal("0.5"));
        return priceOfHalfPriceLoaves
                .multiply(new BigDecimal(halfPriceBreadFactor))
                .setScale(2, RoundingMode.HALF_UP);
    }
}