package com.ford.henrysgroceries.offers;

import com.ford.henrysgroceries.Basket;
import com.ford.henrysgroceries.products.Product;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.ford.henrysgroceries.products.ProductHelper.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BuyTwoSoupsGetBreadHalfPriceOfferTest {
    private Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    private LocalDate today = LocalDate.now(fixedClock);
    private List<Offer> offers = Collections.singletonList(new BuyTwoSoupsGetBreadHalfPriceOffer(today));

    @Test
    public void emptyBasketTotalsToZero() {
        Basket basket = new Basket(offers, fixedClock);

        BigDecimal total = basket.calculateTotal();

        assertThat(total.compareTo(BigDecimal.ZERO), is(0));
    }

    @Test
    public void offerDoesNotApplyTwoDaysAgo() {
        Clock clock = Clock.offset(fixedClock, Duration.ofDays(-2));
        Basket basket = new Basket(offers, clock, soup(), soup(), bread());

        BigDecimal total = basket.calculateTotal();

        assertThat(total.compareTo(new BigDecimal("2.10")), is(0));
    }

    @Test
    public void offerStartsFromYesterday() {
        Clock clock = Clock.offset(fixedClock, Duration.ofDays(-1));
        Basket basket = new Basket(offers, clock, soup(), soup(), bread());

        BigDecimal total = basket.calculateTotal();

        assertThat(total.compareTo(new BigDecimal("1.70")), is(0));
    }

    @Test
    public void offerAppliesForSevenDays() {
        Clock clock = Clock.offset(fixedClock, Duration.ofDays(6));
        Basket basket = new Basket(offers, clock, soup(), soup(), bread());

        BigDecimal total = basket.calculateTotal();

        assertThat(total.compareTo(new BigDecimal("1.70")), is(0));
    }

    @Test
    public void offerEndsAfterSevenDays() {
        Clock clock = Clock.offset(fixedClock, Duration.ofDays(7));
        Basket basket = new Basket(offers, clock, soup(), soup(), bread());

        BigDecimal total = basket.calculateTotal();

        assertThat(total.compareTo(new BigDecimal("2.10")), is(0));
    }

    @Test
    public void offerAppliesToday() {
        Basket basket = new Basket(offers, fixedClock, soup(), soup(), bread());

        BigDecimal total = basket.calculateTotal();

        assertThat(total.compareTo(new BigDecimal("1.70")), is(0));
    }

    @Test
    public void noDiscountWhenNoSoupBought() {
        Basket basket = new Basket(offers, fixedClock, bread());

        BigDecimal total = basket.calculateTotal();

        assertThat(total.compareTo(new BigDecimal("0.80")), is(0));
    }

    @Test
    public void noDiscountWhenOnlyOneSoupBought() {
        Basket basket = new Basket(offers, fixedClock, soup(), bread());

        BigDecimal total = basket.calculateTotal();

        assertThat(total.compareTo(new BigDecimal("1.45")), is(0));
    }

    @Test
    public void onlyDiscountedAppliedWhenTwoSoupsBought() {
        Basket basket = new Basket(offers, fixedClock, soup(), soup(), bread(), bread());

        BigDecimal total = basket.calculateTotal();

        assertThat(total.compareTo(new BigDecimal("2.50")), is(0));
    }

    @Test
    public void onlyOneDiscountAppliedWhenThreeSoupsBought() {
        Basket basket = new Basket(offers, fixedClock, soup(), soup(), soup(), bread(), bread());

        BigDecimal total = basket.calculateTotal();

        assertThat(total.compareTo(new BigDecimal("3.15")), is(0));
    }

    @Test
    public void twoDiscountsAppliedWhenFourSoupsBought() {
        Basket basket = new Basket(offers, fixedClock, soup(), soup(), soup(), soup(), bread(), bread());

        BigDecimal total = basket.calculateTotal();

        assertThat(total.compareTo(new BigDecimal("3.40")), is(0));
    }

    @Test
    public void offerNotAppliedToOtherProducts() {
        for (Product product : Arrays.asList(milk(), apples())) {
            Basket basket = new Basket(offers, fixedClock, product);

            BigDecimal total = basket.calculateTotal();

            assertThat(total.compareTo(product.getPrice()), is(0));
        }
    }
}
