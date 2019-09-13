package com.ford.henrysgroceries.products;

import org.junit.Test;

import java.math.BigDecimal;

import static com.ford.henrysgroceries.products.ProductHelper.milk;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ProductTest {
    @Test
    public void hasDiscountReturnsTrueWhenDiscountedPriceExists() {
        Product milk = milk();
        milk.setDiscountPrice(new BigDecimal("1.00"));

        assertThat(milk.hasDiscount(), is(true));
    }

    @Test
    public void hasDiscountReturnsFalseWhenNoDiscountedPrice() {
        assertThat(milk().hasDiscount(), is(false));
    }
}
