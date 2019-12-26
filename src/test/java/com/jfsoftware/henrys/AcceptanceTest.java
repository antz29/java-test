package com.jfsoftware.henrys;

import com.jfsoftware.henrys.model.StockItem;
import com.jfsoftware.henrys.offer.AppleOffer;
import com.jfsoftware.henrys.offer.BreadSoupOffer;
import com.jfsoftware.henrys.offer.Offer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static com.jfsoftware.henrys.model.Product.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class AcceptanceTest {

    private ShoppingContext shoppingContext;

    @BeforeEach
    void createShoppingContext() {
        Set<Offer> offers = new HashSet<>(asList(new AppleOffer(), new BreadSoupOffer()));
        shoppingContext = new ShoppingContext(offers);
    }

    @Test
    void threeSoupTwoBreadBoughtTodayIsPricedCorrectly() {
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(BREAD));
        shoppingContext.addItemToBasket(new StockItem(BREAD));
        shoppingContext.setDaysFromNowToBuy(0);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("3.15"));
    }

    @Test
    void sixApplesAndABottleOfMilkBoughtTodayIsPricedCorrectly() {
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
    void sixApplesAndABottleOfMilkBoughtInFiveDaysTimeIsPricedCorrectly() {
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
    void threeApplesTwoSoupAndLoafOfBreadBoughtInFiveDaysTimeIsPricedCorrectly() {
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(BREAD));
        shoppingContext.setDaysFromNowToBuy(5);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("1.97"));
    }

    @Test
    void emptyBasketBoughtToday() {
        shoppingContext.setDaysFromNowToBuy(0);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void emptyBasketBoughtTomorrow() {
        shoppingContext.setDaysFromNowToBuy(1);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("0.00"));
    }
}