package com.grocery.henry;

import com.grocery.henry.domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BasketTest {

    private Basket basket;

    @Before
    public void setUp() {
        basket = new Basket();
    }

    @Test
    public void shouldApplyDiscountWhenThreeSoupsTwoBreadsBoughtToday() {
        basket.add(Product.SOUP, 3);
        basket.add(Product.BREAD, 2);

        BigDecimal result = basket.calculate(now());

        assertThat(result, is(valueOf(3.15)));
    }

    @Test
    public void shouldNotApplyDiscountWhenSixApplesOneMilkBoughtToday() {
        basket.add(Product.APPLE, 6);
        basket.add(Product.MILK, 1);

        BigDecimal result = basket.calculate(now());

        assertThat(result, is(valueOf(1.90)));
    }

    @Test
    public void shouldApplyDiscountWhenSixApplesOneMilkBoughtInFiveDaysTime() {
        basket.add(Product.APPLE, 6);
        basket.add(Product.MILK, 1);

        BigDecimal result = basket.calculate(now().plusDays(5));

        assertThat(result, is(valueOf(1.84)));
    }

    @Test
    public void shouldApplyDiscountWhenThreeApplesTwoSoupsOneBreadBoughtInFiveDaysTime() {
        basket.add(Product.APPLE, 3);
        basket.add(Product.SOUP, 2);
        basket.add(Product.BREAD, 1);

        BigDecimal result = basket.calculate(now().plusDays(5));

        assertThat(result, is(valueOf(1.97)));
    }
}
