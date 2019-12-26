package com.jfsoftware.henrys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static com.jfsoftware.henrys.Product.APPLE;
import static com.jfsoftware.henrys.Product.MILK;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class AcceptanceTest {

    private ShoppingContext shoppingContext;

    @BeforeEach
    void createShoppingContext() {
        Set<Offer> offers = new HashSet<>(asList(new AppleOffer()));
        shoppingContext = new ShoppingContext(offers);
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
}