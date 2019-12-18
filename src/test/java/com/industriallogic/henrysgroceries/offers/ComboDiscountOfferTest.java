package com.industriallogic.henrysgroceries.offers;

import com.industriallogic.henrysgroceries.model.ShoppingBasket;
import com.industriallogic.henrysgroceries.provider.ProductProvider;
import com.industriallogic.henrysgroceries.testutil.TestMockUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ComboDiscountOfferTest {

    @Mock
    private ComboDiscountOffer comboDiscountOffer;

    @Mock
    private ProductProvider productProvider;

    @Test
    public void getDiscountOn2SoupsAnd1Bread() {
        ShoppingBasket basket = TestMockUtil.getBasket(productProvider, "Soup", "Soup", "Bread");
        when(comboDiscountOffer.getDiscount(basket)).thenReturn(new BigDecimal(.40));
        assertEquals(new BigDecimal(.40), comboDiscountOffer.getDiscount(basket));
    }

    @Test
    public void getDiscountOn2SoupsAnd2Bread() {
        ShoppingBasket basket = TestMockUtil.getBasket(productProvider, "Soup", "Soup", "Bread","Bread");
        when(comboDiscountOffer.getDiscount(basket)).thenReturn(new BigDecimal(.40));
        assertEquals(new BigDecimal(.40), comboDiscountOffer.getDiscount(basket));
    }
}