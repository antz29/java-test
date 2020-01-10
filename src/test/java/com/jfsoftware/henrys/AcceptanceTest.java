package com.jfsoftware.henrys;

import com.jfsoftware.henrys.model.Item;
import com.jfsoftware.henrys.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.jfsoftware.henrys.model.Product.*;
import static org.assertj.core.api.Assertions.assertThat;

class AcceptanceTest {

    private ShoppingCart shoppingCart;

    @BeforeEach
    void createShoppingContext() {
        shoppingCart = new ShoppingCart();
    }

    @Test
    void threeSoupTwoBreadBoughtTodayIsPricedCorrectly() {
        addToShoppingCart(SOUP, 3);
        addToShoppingCart(BREAD, 2);
        shoppingCart.setDaysFromNowToBuy(0);
        assertThat(shoppingCart.calculateTotalPrice()).isEqualTo(new BigDecimal("3.15"));
    }

    @Test
    void sixApplesAndABottleOfMilkBoughtTodayIsPricedCorrectly() {
        addToShoppingCart(APPLE, 6);
        addToShoppingCart(MILK, 1);
        shoppingCart.setDaysFromNowToBuy(0);
        assertThat(shoppingCart.calculateTotalPrice()).isEqualTo(new BigDecimal("1.90"));
    }

    @Test
    void sixApplesAndABottleOfMilkBoughtInFiveDaysTimeIsPricedCorrectly() {
        addToShoppingCart(APPLE, 6);
        addToShoppingCart(MILK, 1);
        shoppingCart.setDaysFromNowToBuy(5);
        assertThat(shoppingCart.calculateTotalPrice()).isEqualTo(new BigDecimal("1.84"));
    }

    @Test
    void threeApplesTwoSoupAndLoafOfBreadBoughtInFiveDaysTimeIsPricedCorrectly() {
        addToShoppingCart(APPLE, 3);
        addToShoppingCart(SOUP, 2);
        addToShoppingCart(BREAD, 1);
        shoppingCart.setDaysFromNowToBuy(5);
        assertThat(shoppingCart.calculateTotalPrice()).isEqualTo(new BigDecimal("1.97"));
    }

    @Test
    void emptyBasketBoughtTodayIsPricedCorrectly() {
        shoppingCart.setDaysFromNowToBuy(0);
        assertThat(shoppingCart.calculateTotalPrice()).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void emptyBasketBoughtTomorrowIsPricedCorrectly() {
        shoppingCart.setDaysFromNowToBuy(1);
        assertThat(shoppingCart.calculateTotalPrice()).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void anAppleIsPricedCorrectly() {
        shoppingCart.addItem(new Item(APPLE));
        shoppingCart.setDaysFromNowToBuy(0);
        assertThat(shoppingCart.calculateTotalPrice()).isEqualTo(new BigDecimal("0.10"));
    }

    @Test
    void applesArePricedCorrectly() {
        addToShoppingCart(APPLE, 2);
        shoppingCart.setDaysFromNowToBuy(0);
        assertThat(shoppingCart.calculateTotalPrice()).isEqualTo(new BigDecimal("0.20"));
    }

    @Test
    void breadIsNotDiscountedWhenThereIsNoSoup() {
        shoppingCart.addItem(new Item(BREAD));
        shoppingCart.setDaysFromNowToBuy(0);
        assertThat(shoppingCart.calculateTotalPrice()).isEqualTo(new BigDecimal("0.80"));
    }

    @Test
    void oneLoafAndOneSoupArePricedCorrectly() {
        shoppingCart.addItem(new Item(BREAD));
        shoppingCart.addItem(new Item(SOUP));
        shoppingCart.setDaysFromNowToBuy(0);
        assertThat(shoppingCart.calculateTotalPrice()).isEqualTo(new BigDecimal("1.45"));
    }

    @Test
    void twoSoupAndOneLoafArePricedCorrectly() {
        addToShoppingCart(SOUP, 2);
        shoppingCart.addItem(new Item(BREAD));
        shoppingCart.setDaysFromNowToBuy(0);
        assertThat(shoppingCart.calculateTotalPrice()).isEqualTo(new BigDecimal("1.70"));
    }

    @Test
    void threeSoupAndOneLoafArePricedCorrectly() {
        addToShoppingCart(SOUP, 3);
        shoppingCart.addItem(new Item(BREAD));
        shoppingCart.setDaysFromNowToBuy(0);
        assertThat(shoppingCart.calculateTotalPrice()).isEqualTo(new BigDecimal("2.35"));
    }

    @Test
    void threeSoupAndTwoLoavesArePricedCorrectly() {
        addToShoppingCart(SOUP, 3);
        addToShoppingCart(BREAD, 2);
        shoppingCart.setDaysFromNowToBuy(0);
        assertThat(shoppingCart.calculateTotalPrice()).isEqualTo(new BigDecimal("3.15"));
    }

    @Test
    void twoSoupAndTwoLoavesArePricedCorrectly() {
        addToShoppingCart(SOUP, 2);
        addToShoppingCart(BREAD, 2);
        shoppingCart.setDaysFromNowToBuy(0);
        assertThat(shoppingCart.calculateTotalPrice()).isEqualTo(new BigDecimal("2.50"));
    }

    @Test
    void fourSoupAndOneLoafAArePricedCorrectly() {
        addToShoppingCart(SOUP, 4);
        addToShoppingCart(BREAD, 1);
        shoppingCart.setDaysFromNowToBuy(0);
        assertThat(shoppingCart.calculateTotalPrice()).isEqualTo(new BigDecimal("2.60"));
    }

    private void addToShoppingCart(Product product, int count) {
        for (int i = 0; i < count; i++) {
            shoppingCart.addItem(new Item(product));
        }
    }
}