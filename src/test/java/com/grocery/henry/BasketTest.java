package com.grocery.henry;

import com.grocery.henry.domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

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
        basket.add(Product.SOUP, 3);
        basket.add(Product.BREAD, 2);

        BigDecimal result = basket.calculate();

        assertThat(result, is(BigDecimal.valueOf(3.15)));
    }
}
