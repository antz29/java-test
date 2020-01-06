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
    void emptyBasketBoughtTodayIsPricedCorrectly() {
        shoppingContext.setDaysFromNowToBuy(0);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void emptyBasketBoughtTomorrowIsPricedCorrectly() {
        shoppingContext.setDaysFromNowToBuy(1);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void anAppleIsPricedCorrectly() {
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.setDaysFromNowToBuy(0);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("0.10"));
    }

    @Test
    void applesArePricedCorrectly() {
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.addItemToBasket(new StockItem(APPLE));
        shoppingContext.setDaysFromNowToBuy(0);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("0.20"));
    }

    @Test
    void breadIsNotDiscountedWhenThereIsNoSoup() {
        shoppingContext.addItemToBasket(new StockItem(BREAD));
        shoppingContext.setDaysFromNowToBuy(0);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("0.80"));
    }

    @Test
    void oneLoafAndOneSoupArePricedCorrectly() {
        shoppingContext.addItemToBasket(new StockItem(BREAD));
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.setDaysFromNowToBuy(0);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("1.45"));
    }

    @Test
    void twoSoupAndOneLoafArePricedCorrectly() {
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(BREAD));
        shoppingContext.setDaysFromNowToBuy(0);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("1.70"));
    }

    @Test
    void threeSoupAndOneLoafArePricedCorrectly() {
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(BREAD));
        shoppingContext.setDaysFromNowToBuy(0);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("2.35"));
    }

    @Test
    void threeSoupAndTwoLoavesArePricedCorrectly() {
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(BREAD));
        shoppingContext.addItemToBasket(new StockItem(BREAD));
        shoppingContext.setDaysFromNowToBuy(0);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("3.15"));
    }

    @Test
    void twoSoupAndTwoLoavesArePricedCorrectly() {
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(BREAD));
        shoppingContext.addItemToBasket(new StockItem(BREAD));
        shoppingContext.setDaysFromNowToBuy(0);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("2.50"));
    }

    @Test
    void fourSoupAndOneLoafAArePricedCorrectly() {
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(SOUP));
        shoppingContext.addItemToBasket(new StockItem(BREAD));
        shoppingContext.setDaysFromNowToBuy(0);
        assertThat(shoppingContext.getTotalPrice()).isEqualTo(new BigDecimal("2.60"));
    }
}