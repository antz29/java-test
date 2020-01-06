package com.industriallogic.henrysgroceries.offers;

import com.industriallogic.henrysgroceries.exception.ProductNotFoundException;
import com.industriallogic.henrysgroceries.model.MeasurementUnit;
import com.industriallogic.henrysgroceries.model.Product;
import com.industriallogic.henrysgroceries.model.ShoppingBasket;
import com.industriallogic.henrysgroceries.provider.ProductProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PercentageDiscountOfferTest {

    private PercentageDiscountOffer percentDiscountOffer;

    @Mock
    private ProductProvider productProvider;

    @Mock
    private static ShoppingBasket shoppingBasket;

    @Before
    public void setUP() throws ProductNotFoundException {
        when(productProvider.getProduct("Apples")).thenReturn(new Product("A123", "Apple", BigDecimal.valueOf(.10), MeasurementUnit.SINGLE));

        LocalDate firstOfNextMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
        LocalDate endOfNextMonth = firstOfNextMonth.with(TemporalAdjusters.lastDayOfMonth());
        percentDiscountOffer = new PercentageDiscountOffer("Apple 10% OFF", productProvider.getProduct("Apples"), BigDecimal.valueOf(10), LocalDate.now().plusDays(3), endOfNextMonth);
    }


    @Test
    public void getDiscountOn3ApplesBoughtToday() throws ProductNotFoundException {
        when(shoppingBasket.getShoppingDate()).thenReturn(LocalDate.now());

        Map<Product, Integer> productMap = new HashMap<Product, Integer>() {{
            put(productProvider.getProduct("Apples"), 3);
        }};
        when(shoppingBasket.getProductsInBasket()).thenReturn(productMap);
        Assertions.assertEquals(  BigDecimal.valueOf(0), percentDiscountOffer.getDiscount(shoppingBasket));
    }

    @Test
    public void getDiscountOn3ApplesBoughtIn5Days() throws ProductNotFoundException {
        when(shoppingBasket.getShoppingDate()).thenReturn(LocalDate.now().plusDays(5));

        Map<Product, Integer> productMap = new HashMap<Product, Integer>() {{
            put(productProvider.getProduct("Apples"), 3);
        }};
        when(shoppingBasket.getProductsInBasket()).thenReturn(productMap);
        Assertions.assertEquals(  BigDecimal.valueOf(.03).setScale(2), percentDiscountOffer.getDiscount(shoppingBasket));
    }
}