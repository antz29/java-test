package com.jfsoftware.henrys.calculation;

import com.jfsoftware.henrys.Cart;
import com.jfsoftware.henrys.model.Item;
import com.jfsoftware.henrys.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.jfsoftware.henrys.calculation.PriceCalculator.calculateRawPrice;

/**
 * Refactor this code
 */
public final class BreadPriceCalculator implements PriceCalculator {
    private Cart cart;

    @Override
    public BigDecimal calculateDiscountedPrice(final Cart cart) {
        this.cart = cart;
        final LocalDate yesterday = LocalDate.now().minusDays(1);
        final LocalDate sevenDaysFromYesterday = yesterday.plusDays(7);
        SeasonOfferRules.getInstance().setStartDate(yesterday);
        SeasonOfferRules.getInstance().setEndDate(sevenDaysFromYesterday);
        BigDecimal rawPrice = calculateRawPrice(cart, Product.BREAD);

        return SeasonOfferRules.getInstance().isOfferInSeason() ? discountPrice(rawPrice) : rawPrice;
    }

    private BigDecimal discountPrice(final BigDecimal price) {
        final Supplier<Stream<Item>> streamSupplier = () -> cart.getItems().stream();
        final long soupCount = getProductCount(streamSupplier, Product.SOUP);
        final long breadCount = getProductCount(streamSupplier, Product.BREAD);

        if (breadDiscountApplicable(soupCount, breadCount)) {
            return price.subtract(discountCountBread(soupCount));
        }
        return price;
    }

    private long getProductCount(Supplier<Stream<Item>> streamSupplier, Product product) {
        return streamSupplier
                .get()
                .filter(item -> item.getProduct().equals(product))
                .count();
    }

    private boolean breadDiscountApplicable(final long soupCount, final long breadCount) {
        return soupCount >= 2 && breadCount > 0;
    }

    private BigDecimal discountCountBread(final long soupCount) {
        final long halfPriceBreadFactor = soupCount / 2;
        final BigDecimal priceOfHalfPriceLoaves = Product.BREAD
                .getPrice()
                .multiply(new BigDecimal(0.50));

        return priceOfHalfPriceLoaves
                .multiply(new BigDecimal(halfPriceBreadFactor))
                .setScale(2, RoundingMode.HALF_UP);
    }
}