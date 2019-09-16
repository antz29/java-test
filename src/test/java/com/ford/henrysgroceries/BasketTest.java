package com.ford.henrysgroceries;

import com.ford.henrysgroceries.offers.BuyTwoSoupsGetBreadHalfPriceOffer;
import com.ford.henrysgroceries.offers.Offer;
import com.ford.henrysgroceries.offers.TenPercentOffApplesOffer;
import com.ford.henrysgroceries.products.Product;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.*;
import java.util.Arrays;
import java.util.List;

import static com.ford.henrysgroceries.products.ProductHelper.*;
import static com.ford.henrysgroceries.utils.EqualsBigDecimalMatcher.is;
import static org.junit.Assert.assertThat;

public class BasketTest {
    private Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

    private Basket basket;
    private Offer applesOffer = new TenPercentOffApplesOffer(LocalDate.now());
    private Offer breadOffer = new BuyTwoSoupsGetBreadHalfPriceOffer(LocalDate.now());
    private List<Offer> offers = Arrays.asList(applesOffer, breadOffer);

    @Test
    public void emptyBasketWithNoOffersTotalsToZero() {
        basket = new Basket();

        BigDecimal total = basket.calculateTotal();

        assertThat(total, is(BigDecimal.ZERO));
    }

    @Test
    public void emptyBasketWithOffersTotalsToZero() {
        basket = new Basket(offers);

        BigDecimal total = basket.calculateTotal();

        assertThat(total, is(BigDecimal.ZERO));
    }

    @Test
    public void givesCorrectTotalForSingleProducts() {
        for (Product product : Arrays.asList(soup(), bread(), milk(), apples())) {
            givenBasketHasProduct(product);

            BigDecimal total = basket.calculateTotal();

            assertThat(total, is(product.getPrice()));
        }
    }

    @Test
    public void givesCorrectTotalForMilkAndBread() {
        givenBasketHasProducts(milk(), bread());

        BigDecimal total = basket.calculateTotal();

        assertThat(total, is(new BigDecimal("2.10")));
    }

    @Test
    public void givesCorrectTotalForMoreThanOneProductOfSameType() {
        givenBasketHasProducts(
                apples(), apples(),
                soup(), soup()
        );

        BigDecimal total = basket.calculateTotal();

        assertThat(total, is(new BigDecimal("1.50")));
    }

    @Test
    public void threeTinsOfSoupAndTwoLoavesOfBreadBoughtTodayCosts3Pounds15Pence() {
        givenBasketHasProducts(
                soup(), soup(), soup(),
                bread(), bread()
        );

        BigDecimal total = basket.calculateTotal();

        assertThat(total, is(new BigDecimal("3.15")));
    }

    @Test
    public void sixApplesAndOneBottleOfMilkBoughtTodayCosts1Pound90Pence() {
        givenBasketHasProducts(
                apples(), apples(), apples(),
                apples(), apples(), apples(),
                milk()
        );

        BigDecimal total = basket.calculateTotal();

        assertThat(total, is(new BigDecimal("1.90")));
    }

    @Test
    public void sixApplesAndOneBottleOfMilkBoughtIn5DaysTimeCosts1Pound84Pence() {
        Clock clock = Clock.offset(fixedClock, Duration.ofDays(5));
        givenBasketHasProducts(
                clock,
                apples(), apples(), apples(),
                apples(), apples(), apples(),
                milk()
        );

        BigDecimal total = basket.calculateTotal();

        assertThat(total, is(new BigDecimal("1.84")));
    }

    @Test
    public void threeApplesAndTwoTinsOfSoupAndAndOneLoafOfBreadBoughtIn5DaysTimeCosts1Pound97Pence() {
        Clock clock = Clock.offset(fixedClock, Duration.ofDays(5));
        givenBasketHasProducts(
                clock,
                apples(), apples(), apples(),
                soup(), soup(),
                bread()
        );

        BigDecimal total = basket.calculateTotal();

        assertThat(total, is(new BigDecimal("1.97")));
    }

    private void givenBasketHasProduct(Product product) {
        givenBasketHasProducts(product);
    }

    private void givenBasketHasProducts(Product... products) {
        givenBasketHasProducts(fixedClock, products);
    }

    private void givenBasketHasProducts(Clock clock, Product... products) {
        basket = new Basket(offers, clock, products);
    }
}
