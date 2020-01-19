package com.grocery.henry;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BasketTest {

    private Basket basket;

    @Before
    public void setUp() {
        basket = new Basket();
    }

    @Test
    public void shouldApplyDiscountWhenThreeTinsAndTwoLoavesAdded() {
        basket.add("soup", 3);
        basket.add("bread", 2);

        double result = basket.calculate();

        assertThat(result, is(3.15));
    }
}
