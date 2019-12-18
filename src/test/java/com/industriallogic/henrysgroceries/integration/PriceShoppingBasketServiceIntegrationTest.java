package com.industriallogic.henrysgroceries.integration;


import com.industriallogic.henrysgroceries.PriceShoppingBasketApplication;
import com.industriallogic.henrysgroceries.exception.ProductNotFoundException;
import com.industriallogic.henrysgroceries.model.ShoppingBasket;
import com.industriallogic.henrysgroceries.provider.ProductProvider;
import com.industriallogic.henrysgroceries.service.PriceShoppingBasketService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PriceShoppingBasketApplication.class,
        initializers = ConfigFileApplicationContextInitializer.class)
@Slf4j
public class PriceShoppingBasketServiceIntegrationTest {

    @Autowired
    private PriceShoppingBasketService pricingService;

    @Autowired
    private ProductProvider productProvider;

    @Test
    public void price3SoupAnd2BreadComboOfferAppliedOn1BreadTest() {
        ShoppingBasket basket = getBasket("Soup", "Soup", "Soup", "Bread", "Bread");
        BigDecimal bigDecimal = pricingService.totalPriceToPay(basket);
        assertEquals(BigDecimal.valueOf(3.15), bigDecimal);
    }

    @Test
    public void price6ApplesAndMilkBoughtTodayNoOfferAppliedTest() {
        ShoppingBasket basket = getBasket("Apples", "Apples", "Apples", "Apples", "Apples", "Apples", "Milk");
        BigDecimal bigDecimal = pricingService.totalPriceToPay(basket);
        assertEquals(BigDecimal.valueOf(1.90).setScale(2), bigDecimal);
    }

    @Test
    public void price6ApplesAndMilkBoughtIn5DaysPercentOfferAppliedTest() {
        ShoppingBasket basket = getBasket("Apples", "Apples", "Apples", "Apples", "Apples", "Apples", "Milk");
        basket.setShoppingDate(LocalDate.now().plusDays(5));
        BigDecimal bigDecimal = pricingService.totalPriceToPay(basket);
        assertEquals(BigDecimal.valueOf(1.84), bigDecimal);
    }

    @Test
    public void price3ApplesAnd2SoupBoughtIn5DaysPercentOfferAppliedTest() {
        ShoppingBasket basket = getBasket("Apples", "Apples", "Apples", "Soup", "Soup");
        basket.setShoppingDate(LocalDate.now().plusDays(5));
        BigDecimal bigDecimal = pricingService.totalPriceToPay(basket);
        assertEquals(BigDecimal.valueOf(1.57), bigDecimal);
    }

    @Test
    public void price1SoupAnd1BreadNoOfferAppliedTest() {
        ShoppingBasket basket = getBasket("Soup", "Bread");
        BigDecimal bigDecimal = pricingService.totalPriceToPay(basket);

        assertEquals(BigDecimal.valueOf(1.45), bigDecimal);
    }

    @Test
    public void price2SoupAnd1BreadComboOfferAppliedTest() {
        ShoppingBasket basket = getBasket("Soup", "Soup", "Bread");
        BigDecimal bigDecimal = pricingService.totalPriceToPay(basket);
        assertEquals(BigDecimal.valueOf(1.70).setScale(2), bigDecimal);
    }

    @Test
    public void price2SoupAnd1BreadGetDiscountAmountTest() {
        ShoppingBasket basket = getBasket("Soup", "Soup", "Bread");
        BigDecimal bigDecimal = pricingService.getTotalDiscount(basket);
        assertEquals(BigDecimal.valueOf(.40).setScale(2), bigDecimal);
    }

    @Test
    public void price3ApplesAnd2SoupAnd1BreadBoughtIn5DaysComboAndPercentOfferAppliedTest() {
        ShoppingBasket basket = getBasket("Apples", "Apples", "Apples", "Soup", "Soup", "Bread");
        basket.setShoppingDate(LocalDate.now().plusDays(5));
        BigDecimal bigDecimal = pricingService.totalPriceToPay(basket);
        assertEquals(BigDecimal.valueOf(1.97), bigDecimal);
    }

    @Test
    public void price3ApplesAnd2SoupAnd1BreadBoughtIn5DaysGetDiscountAmountTest() {
        ShoppingBasket basket = getBasket("Apples", "Apples", "Apples", "Soup", "Soup", "Bread");
        basket.setShoppingDate(LocalDate.now().plusDays(5));
        BigDecimal bigDecimal = pricingService.totalPriceToPay(basket);
        assertEquals(BigDecimal.valueOf(1.97), bigDecimal);
    }

    public ShoppingBasket getBasket(String... productNames) {
        ShoppingBasket basket = new ShoppingBasket();
        for (String productName : productNames) {
            try {
                basket.addProductToBasket(productProvider.getProduct(productName));
            } catch (ProductNotFoundException e) {
                LOGGER.error("Product not found " + productName);
            }
        }
        return basket;
    }

}
