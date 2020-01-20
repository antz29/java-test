package com.grocery.henry.price;

import com.grocery.henry.domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.grocery.henry.domain.Product.APPLE;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AppleDiscountTest {

    private AppleDiscount appleDiscount;
    private Map<Product, Integer> products;

    @Before
    public void setUp() {
        appleDiscount = new AppleDiscount();
        products = new HashMap();
    }

    @Test
    public void shouldGiveDiscountIfDateIsWithinOfferPeriod() {
        products.put(APPLE, 6);
        BigDecimal result = appleDiscount.calculatePrice(products, APPLE, now().plusDays(4));
        assertThat(result, is(valueOf(0.54)));
    }

    @Test
    public void shouldNotGiveDiscountIfDateIsOutsideOfferPeriod() {
        products.put(APPLE, 6);
        BigDecimal result = appleDiscount.calculatePrice(products, APPLE, now());
        assertThat(result, is(valueOf(0.6)));
    }

}
