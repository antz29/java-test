package com.industriallogic.henrysgroceries.integration;


import com.industriallogic.henrysgroceries.PriceShoppingBasketApplication;
import com.industriallogic.henrysgroceries.exception.ProductNotFoundException;
import com.industriallogic.henrysgroceries.service.ShoppingBasketService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PriceShoppingBasketApplication.class,
        initializers = ConfigFileApplicationContextInitializer.class)
@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ShoppingBasketServiceIntegrationTest {

    @Autowired
    private ShoppingBasketService shoppingBasketService;

    @Test
    public void price3SoupAnd2BreadComboOfferAppliedOn1BreadTest()   throws ProductNotFoundException {
        shoppingBasketService.addProductToBasket("Soup");
        shoppingBasketService.addProductToBasket("Soup");
        shoppingBasketService.addProductToBasket("Soup");
        shoppingBasketService.addProductToBasket("Bread");
        BigDecimal currTotal = shoppingBasketService.addProductToBasket("Bread");
        BigDecimal totalDiscount = shoppingBasketService.getTotalDiscount();
        BigDecimal totalPriceToPay = shoppingBasketService.totalPriceToPay( );
        assertEquals(BigDecimal.valueOf(3.55), currTotal);
        assertEquals(BigDecimal.valueOf(0.40).setScale(2), totalDiscount);
        assertEquals(BigDecimal.valueOf(3.15), totalPriceToPay);
    }

    @Test
    public void price6ApplesAndMilkBoughtTodayNoOfferAppliedTest() throws ProductNotFoundException {
        shoppingBasketService.addProductToBasket("Apples");
        shoppingBasketService.addProductToBasket("Apples");
        shoppingBasketService.addProductToBasket("Apples");
        shoppingBasketService.addProductToBasket("Apples");
        shoppingBasketService.addProductToBasket("Apples");
        shoppingBasketService.addProductToBasket("Apples");
        BigDecimal currTotal =  shoppingBasketService.addProductToBasket("Milk");
        BigDecimal totalDiscount = shoppingBasketService.getTotalDiscount();
        BigDecimal totalPriceToPay = shoppingBasketService.totalPriceToPay( );
        assertEquals(BigDecimal.valueOf(1.90).setScale(2), currTotal);
        assertEquals(BigDecimal.valueOf(0.00).setScale(2), totalDiscount);
        assertEquals(BigDecimal.valueOf(1.90).setScale(2), totalPriceToPay);
    }

    @Test
    public void price1SoupAnd1BreadNoOfferAppliedTest() throws ProductNotFoundException {
        shoppingBasketService.addProductToBasket("Soup");
        BigDecimal currTotal =  shoppingBasketService.addProductToBasket("Bread");
        BigDecimal totalDiscount = shoppingBasketService.getTotalDiscount();
        BigDecimal totalPriceToPay = shoppingBasketService.totalPriceToPay( );
        assertEquals(BigDecimal.valueOf(1.45).setScale(2), currTotal);
        assertEquals(BigDecimal.valueOf(0.00).setScale(2), totalDiscount);
        assertEquals(BigDecimal.valueOf(1.45).setScale(2), totalPriceToPay);
    }

    @Test
    public void price2SoupAnd1BreadComboOfferAppliedTest() throws ProductNotFoundException {
        shoppingBasketService.addProductToBasket("Soup");
        shoppingBasketService.addProductToBasket("Soup");
        BigDecimal currTotal =  shoppingBasketService.addProductToBasket("Bread");
        BigDecimal totalDiscount = shoppingBasketService.getTotalDiscount();
        BigDecimal totalPriceToPay = shoppingBasketService.totalPriceToPay( );
        assertEquals(BigDecimal.valueOf(2.10).setScale(2), currTotal);
        assertEquals(BigDecimal.valueOf(0.40).setScale(2), totalDiscount);
        assertEquals(BigDecimal.valueOf(1.70).setScale(2), totalPriceToPay);
    }
}
