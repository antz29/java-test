package com.ford.henrysgroceries.offers;

import com.ford.henrysgroceries.Basket;
import com.ford.henrysgroceries.products.Product;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.ford.henrysgroceries.products.ProductHelper.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PercentageDiscountTest {
    @Test
    public void emptyBasketRemainsUnchanged() {
        List<Offer> offers = Collections.singletonList(new PercentageDiscount(apples(), 10));
        Basket basket = new Basket(offers);

        BigDecimal total = basket.calculateTotal();

        assertThat(total.compareTo(BigDecimal.ZERO), is(0));
    }

    @Test
    public void applesAreDiscountedBy10Percent() {
        List<Offer> offers = Collections.singletonList(new PercentageDiscount(apples(), 10));
        Basket basket = new Basket(offers, apples());

        BigDecimal total = basket.calculateTotal();

        assertThat(total.compareTo(new BigDecimal("0.09")), is(0));
    }

    @Test
    public void manyApplesAreDiscountedBy10Percent() {
        List<Offer> offers = Collections.singletonList(new PercentageDiscount(apples(), 10));
        Basket basket = new Basket(offers, apples(), apples(), apples());

        BigDecimal total = basket.calculateTotal();

        assertThat(total.compareTo(new BigDecimal("0.27")), is(0));
    }

    @Test
    public void offerNotAppliedToOtherProducts() {
        List<Offer> offers = Collections.singletonList(new PercentageDiscount(apples(), 10));

        for (Product product : Arrays.asList(soup(), bread(), milk())) {
            Basket basket = new Basket(offers, product);

            BigDecimal total = basket.calculateTotal();

            assertThat(total.compareTo(product.getPrice()), is(0));
        }
    }
}
