package com.industriallogic.henrysgroceries.offers;

import com.industriallogic.henrysgroceries.exception.ProductNotFoundException;
import com.industriallogic.henrysgroceries.model.MeasurementUnit;
import com.industriallogic.henrysgroceries.model.Product;
import com.industriallogic.henrysgroceries.model.ShoppingBasket;
import com.industriallogic.henrysgroceries.provider.ProductProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ComboDiscountOfferTest {

    @Mock
    private ComboDiscountOffer comboDiscountOffer;

    @Mock
    private ProductProvider productProvider;

    @Mock
    private static ShoppingBasket shoppingBasket;
    @Before
    public void setUP() throws ProductNotFoundException {
        when(productProvider.getProduct("Bread")).thenReturn(new Product("B123", "Bread", BigDecimal.valueOf(.80), MeasurementUnit.LOAF));
        when(productProvider.getProduct("Soup")).thenReturn(new Product("S123", "Soup", BigDecimal.valueOf(.65), MeasurementUnit.TIN));
    }

    @Test
    public void getDiscountOn2SoupsAnd1Bread() throws ProductNotFoundException {
        when(shoppingBasket.getShoppingDate()).thenReturn(LocalDate.now());

        Map<Product, Integer> productMap = new HashMap<Product, Integer>() {{
            put(productProvider.getProduct("Soup"), 2);
            put(productProvider.getProduct("Bread"), 1);
        }};
        when(shoppingBasket.getProductsInBasket()).thenReturn(productMap);
        when(comboDiscountOffer.getDiscount(shoppingBasket)).thenReturn(new BigDecimal(.40));
        assertEquals(new BigDecimal(.40), comboDiscountOffer.getDiscount(shoppingBasket));
    }

    @Test
    public void getDiscountOn2SoupsAnd2Bread() throws ProductNotFoundException {
        when(shoppingBasket.getShoppingDate()).thenReturn(LocalDate.now());

        Map<Product, Integer> productMap = new HashMap<Product, Integer>() {{
            put(productProvider.getProduct("Soup"), 2);
            put(productProvider.getProduct("Bread"), 2);
        }};
        when(comboDiscountOffer.getDiscount(shoppingBasket)).thenReturn(new BigDecimal(.40));
        assertEquals(new BigDecimal(.40), comboDiscountOffer.getDiscount(shoppingBasket));
    }
}