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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ShoppingBasketServiceIntegrationTest {

    @Autowired
    private ShoppingBasketService shoppingBasketService;

    @Test
    public void price3SoupAnd2BreadComboOfferAppliedOn1BreadTest()   throws ProductNotFoundException {
        shoppingBasketService.addProductToBasket("Soup");
        shoppingBasketService.addProductToBasket("Soup");
        shoppingBasketService.addProductToBasket("Soup");
        shoppingBasketService.addProductToBasket("Bread");
        BigDecimal cuurTotal = shoppingBasketService.addProductToBasket("Bread");
        BigDecimal totalDiscount = shoppingBasketService.getTotalDiscount();
        BigDecimal totalPriceToPay = shoppingBasketService.totalPriceToPay( );
        assertEquals(BigDecimal.valueOf(3.55), cuurTotal);
        assertEquals(BigDecimal.valueOf(0.40).setScale(2), totalDiscount);
        assertEquals(BigDecimal.valueOf(3.15), totalPriceToPay);
    }


}
