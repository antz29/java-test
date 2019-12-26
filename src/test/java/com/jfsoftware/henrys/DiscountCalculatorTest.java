package com.jfsoftware.henrys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.jfsoftware.henrys.Product.*;
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

    @Test
    void breadIsNotDiscountedWhenThereIsNoSoup() {
        BigDecimal discount = calculator.calculateSoupBreadDiscount(singletonList(new StockItem(BREAD)));
        assertThat(discount).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void breadIsNotDiscountedWhenThereIsOnlyOneSoup() {
        BigDecimal discount = calculator.calculateSoupBreadDiscount(asList(
                new StockItem(SOUP),
                new StockItem(BREAD)));
        assertThat(discount).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void twoSoupAndOneLoafAreDiscountedCorrectly() {
        BigDecimal discount = calculator.calculateSoupBreadDiscount(asList(
                new StockItem(SOUP),
                new StockItem(SOUP),
                new StockItem(BREAD)));
        assertThat(discount).isEqualTo(new BigDecimal("0.40"));
    }

    @Test
    void threeSoupAndOneLoafAreDiscountedCorrectly() {
        BigDecimal discount = calculator.calculateSoupBreadDiscount(asList(
                new StockItem(SOUP),
                new StockItem(SOUP),
                new StockItem(SOUP),
                new StockItem(BREAD)));
        assertThat(discount).isEqualTo(new BigDecimal("0.40"));
    }

    @Test
    void threeSoupAndTwoLoavesAreDiscountedCorrectly() {
        BigDecimal discount = calculator.calculateSoupBreadDiscount(asList(
                new StockItem(SOUP),
                new StockItem(SOUP),
                new StockItem(SOUP),
                new StockItem(BREAD),
                new StockItem(BREAD)));
        assertThat(discount).isEqualTo(new BigDecimal("0.40"));
    }

    @Test
    void twoSoupAndTwoLoavesAreDiscountedCorrectly() {
        BigDecimal discount = calculator.calculateSoupBreadDiscount(asList(
                new StockItem(SOUP),
                new StockItem(SOUP),
                new StockItem(BREAD),
                new StockItem(BREAD)));
        assertThat(discount).isEqualTo(new BigDecimal("0.40"));
    }

    @Test
    void fourSoupAndOneLoafAreDiscountedCorrectly() {
        BigDecimal discount = calculator.calculateSoupBreadDiscount(asList(
                new StockItem(SOUP),
                new StockItem(SOUP),
                new StockItem(SOUP),
                new StockItem(SOUP),
                new StockItem(BREAD)));
        assertThat(discount).isEqualTo(new BigDecimal("0.80"));
    }
}