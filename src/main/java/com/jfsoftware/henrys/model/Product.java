package com.jfsoftware.henrys.model;

import com.jfsoftware.henrys.calculation.*;
import com.jfsoftware.henrys.cli.InvalidItemException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Product {
    SOUP(Unit.TIN, new BigDecimal("0.65"), new SoupPriceCalculator() {
    }),
    BREAD(Unit.LOAF, new BigDecimal("0.80"), new BreadPriceCalculator(new OfferSeasonDetails())),
    MILK(Unit.BOTTLE, new BigDecimal("1.30"), new MilkPriceCalculator() {
    }),
    APPLE(Unit.SINGLE, new BigDecimal("0.10"), new ApplePriceCalculator(new OfferSeasonDetails()));

    final private Unit unit;
    final private BigDecimal price;
    final private PriceCalculator priceCalculator;

    Product(Unit unit, BigDecimal price, PriceCalculator priceCalculator) {
        this.unit = unit;
        this.price = price;
        this.priceCalculator = priceCalculator;
    }

    public static Product fromString(final String enteredProduct) {
        return Stream.of(Product.values())
                .filter(p -> p.name()
                        .equalsIgnoreCase(enteredProduct))
                .findAny()
                .orElseThrow(() -> new InvalidItemException("Please enter one of:\n " +
                        "Soup\n " +
                        "Bread\n " +
                        "Milk\n " +
                        "Apple"));
    }

    public static List<PriceCalculator> getPriceCalculators() {
        return Stream.of(Product.values())
                .map(Product::getPriceCalculator)
                .collect(Collectors.toList());
    }

    Unit getUnit() {
        return unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    PriceCalculator getPriceCalculator() {
        return priceCalculator;
    }

    public enum Unit {
        TIN, LOAF, BOTTLE, SINGLE
    }
}