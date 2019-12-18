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
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PercentageDiscountOfferTest {

    @Mock
    private PercentageDiscountOffer percentDiscountOffer;

    @Mock
    private ProductProvider productProvider;

    @Mock
    private static ShoppingBasket shoppingBasket;
    @Before
    public void setUP() throws ProductNotFoundException {
        when(productProvider.getProduct("Apples")).thenReturn(new Product("A123", "Apple", BigDecimal.valueOf(.10), MeasurementUnit.SINGLE));
    }


    @Test
    public void getDiscountOn3ApplesBoughtToday() throws ProductNotFoundException {
        when(shoppingBasket.getShoppingDate()).thenReturn(LocalDate.now());

        Map<Product, Integer> productMap = new HashMap<Product, Integer>() {{
            put(productProvider.getProduct("Apples"), 3);
        }};
        when(shoppingBasket.getProductsInBasket()).thenReturn(productMap);

        when(percentDiscountOffer.getDiscount(shoppingBasket)).thenReturn(new BigDecimal(.00));
        Assertions.assertEquals(new BigDecimal(.00), percentDiscountOffer.getDiscount(shoppingBasket));
    }

    @Test
    public void getDiscountOn3ApplesBoughtIn5Days() throws ProductNotFoundException {
        when(shoppingBasket.getShoppingDate()).thenReturn(LocalDate.now().plusDays(5));

        Map<Product, Integer> productMap = new HashMap<Product, Integer>() {{
            put(productProvider.getProduct("Apples"), 3);
        }};
        when(shoppingBasket.getProductsInBasket()).thenReturn(productMap);
        when(percentDiscountOffer.getDiscount(shoppingBasket)).thenReturn(new BigDecimal(.03));
        Assertions.assertEquals(new BigDecimal(.03), percentDiscountOffer.getDiscount(shoppingBasket));
    }
}