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
import static com.ford.henrysgroceries.utils.EqualsBigDecimalMatcher.is;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static org.junit.Assert.assertThat;

public class TenPercentOffApplesOfferTest {
    private Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    private LocalDate today = LocalDate.now(fixedClock);
    private List<Offer> offers = Collections.singletonList(new TenPercentOffApplesOffer(today));

    @Test
    public void emptyBasketNoOffersTotalIsZero() {
        List<Offer> offers = Collections.emptyList();
        Basket basket = new Basket(offers, fixedClock);

        BigDecimal total = basket.calculateTotal();

        assertThat(total, is(BigDecimal.ZERO));
    }

    @Test
    public void emptyBasketWithOffersTotalIsZero() {
        Basket basket = new Basket(offers, fixedClock);

        BigDecimal total = basket.calculateTotal();

        assertThat(total, is(BigDecimal.ZERO));
    }

    @Test
    public void offerDoesNotApplyTwoDaysFromNow() {
        Clock clock = Clock.offset(fixedClock, Duration.ofDays(2));
        Basket basket = new Basket(offers, clock, apples());

        BigDecimal total = basket.calculateTotal();

        assertThat(total, is(apples().getPrice()));
    }

    @Test
    public void offerStartsThreeDaysFromNow() {
        Clock clock = Clock.offset(fixedClock, Duration.ofDays(3));
        Basket basket = new Basket(offers, clock, apples());

        BigDecimal total = basket.calculateTotal();

        assertThat(total, is(new BigDecimal("0.09")));
    }

    @Test
    public void offerAppliesTillEndOfFollowingMonth() {
        Clock clock = Clock.offset(fixedClock, lastDayOfferApplies());
        Basket basket = new Basket(offers, clock, apples());

        BigDecimal total = basket.calculateTotal();

        assertThat(total, is(new BigDecimal("0.09")));
    }

    @Test
    public void offerDoesNotApplyOneDayAfterEndOfFollowingMonth() {
        Clock clock = Clock.offset(fixedClock, lastDayOfferApplies().plusDays(1));
        Basket basket = new Basket(offers, clock, apples());

        BigDecimal total = basket.calculateTotal();

        assertThat(total, is(apples().getPrice()));
    }

    @Test
    public void oneAppleIsDiscountedBy10Percent() {
        Clock clock = Clock.offset(fixedClock, Duration.ofDays(10));
        Basket basket = new Basket(offers, clock, apples());

        BigDecimal total = basket.calculateTotal();

        assertThat(total, is(new BigDecimal("0.09")));
    }

    @Test
    public void manyApplesAreDiscountedBy10Percent() {
        Clock clock = Clock.offset(fixedClock, Duration.ofDays(10));
        Basket basket = new Basket(offers, clock, apples(), apples(), apples());

        BigDecimal total = basket.calculateTotal();

        assertThat(total, is(new BigDecimal("0.27")));
    }

    @Test
    public void offerNotAppliedToOtherProducts() {
        for (Product product : Arrays.asList(soup(), bread(), milk())) {
            Basket basket = new Basket(offers, fixedClock, product);

            BigDecimal total = basket.calculateTotal();

            assertThat(total, is(product.getPrice()));
        }
    }

    private Duration lastDayOfferApplies() {
        LocalDate lastDayOfferApplies = today.plusMonths(1).with(lastDayOfMonth());
        return Duration.ofDays(DAYS.between(today, lastDayOfferApplies));
    }
}
