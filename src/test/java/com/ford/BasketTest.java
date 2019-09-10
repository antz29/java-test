package com.ford;

import com.ford.products.Product;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.ford.products.ProductHelper.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BasketTest {
    private Basket basket;

    @Test
    public void emptyBasketTotalIsZero() {
        basket = new Basket();

        BigDecimal total = basket.calculateTotal();

        assertThat(total.compareTo(BigDecimal.ZERO), is(0));
    }

    @Test
    public void givesCorrectTotalForSingleProducts() {
        for (Product product : Arrays.asList(soup(), bread(), milk(), apples())) {
            givenBasketHasProduct(product);

            BigDecimal total = basket.calculateTotal();

            assertThat(total.compareTo(product.getPrice()), is(0));
        }
    }

    @Test
    public void givesCorrectTotalForMilkAndBread() {
        givenBasketHasProducts(milk(), bread());

        BigDecimal total = basket.calculateTotal();

        assertThat(total.compareTo(new BigDecimal("2.10")), is(0));
    }

    @Test
    public void givesCorrectTotalForMoreThanOneProductOfSameType() {
        givenBasketHasProducts(
                apples(), apples(),
                soup(), soup()
        );

        BigDecimal total = basket.calculateTotal();

        assertThat(total.compareTo(new BigDecimal("1.50")), is(0));
    }

    private void givenBasketHasProduct(Product product) {
        givenBasketHasProducts(product);
    }

    private void givenBasketHasProducts(Product... products) {
        basket = new Basket();

        for (Product product : products) {
            basket.addProduct(product);
        }
    }
}
