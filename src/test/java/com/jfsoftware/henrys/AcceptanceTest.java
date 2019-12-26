package com.jfsoftware.henrys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static com.jfsoftware.henrys.Product.*;
import static com.jfsoftware.henrys.Product.BREAD;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class AcceptanceTest {

    private ShoppingContext shoppingContext;

    @BeforeEach
    void createShoppingContext() {
        Set<Offer> offers = new HashSet<>(asList(new AppleOffer(), new BreadSoupOffer()));
        shoppingContext = new ShoppingContext(offers);
    }

    @Test
    void threeSoupTwoBreadBoughtToday() {
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(BREAD));
        shoppingContext.addItemToBasket(new StockItem(BREAD));
        shoppingContext.setDaysFromNowToBuy(0);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("3.15"));
    }

    @Test
    void sixApplesAndABottleOfMilkBoughtToday() {
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(MILK));
        shoppingContext.setDaysFromNowToBuy(0);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("1.90"));
    }

    @Test
    void sixApplesAndABottleOfMilkBoughtFiveDaysTime() {
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(MILK));
        shoppingContext.setDaysFromNowToBuy(5);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("1.84"));
    }

    @Test
    void threeApplesTwoSoupAndLoafOfBreadBoughtFiveDaysTime() {
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(BREAD));
        shoppingContext.setDaysFromNowToBuy(5);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("1.97"));
    }
}