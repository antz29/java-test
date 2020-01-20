package com.grocery.henry.price;

import com.grocery.henry.domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.grocery.henry.domain.Product.BREAD;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BreadDiscountTest {
    private BreadDiscount breadDiscount;
    private Map<Product, Integer> products;

    @Before
    public void setUp() {
        breadDiscount = new BreadDiscount();
        products = new HashMap();
    }

    @Test
    public void shouldGiveDiscountIfDateIsWithinOfferPeriod() {
        products.put(Product.SOUP, 3);
        products.put(Product.BREAD, 2);

        BigDecimal result = breadDiscount.calculatePrice(products, BREAD, now());

        assertThat(result, is(valueOf(1.2)));
    }

    @Test
    public void shouldNotGiveDiscountIfDateIsOutsideOfferPeriod() {
        products.put(Product.SOUP, 3);
        products.put(Product.BREAD, 2);

        BigDecimal result = breadDiscount.calculatePrice(products, BREAD, now().minusDays(2));
        assertThat(result, is(valueOf(1.6)));
    }
}
