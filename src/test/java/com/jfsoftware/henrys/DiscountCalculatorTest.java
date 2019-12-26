package com.jfsoftware.henrys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.jfsoftware.henrys.Product.APPLE;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class DiscountCalculatorTest {
    private DiscountCalculator calculator;

    @BeforeEach
    void createDiscountCalculator() {
        calculator = new DiscountCalculator();
    }

    @Test
    void anAppleIsDiscountedCorrectly() {
        BigDecimal discount = calculator.calculateAppleDiscount(singletonList(new StockItem(APPLE)));
        assertThat(discount).isEqualTo(new BigDecimal("0.01"));
    }

    @Test
    void applesAreDiscountedCorrectly() {
        BigDecimal discount = calculator.calculateAppleDiscount(asList(
                new StockItem(APPLE),
                new StockItem(APPLE)));
        assertThat(discount).isEqualTo(new BigDecimal("0.02"));
    }
}