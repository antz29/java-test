package com.industriallogic.henrysgroceries.offers;

import com.industriallogic.henrysgroceries.model.ShoppingBasket;
import com.industriallogic.henrysgroceries.provider.ProductProvider;
import com.industriallogic.henrysgroceries.testutil.TestMockUtil;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PercentageDiscountOfferTest {

    @Mock
    private PercentageDiscountOffer percentDiscountOffer;

    @Mock
    private ProductProvider productProvider;

    @Test
    public void getDiscountOn3ApplesBoughtToday() {
        ShoppingBasket basket = TestMockUtil.getBasket(productProvider, "Apples", "Apples", "Apples");
        when(percentDiscountOffer.getDiscount(basket)).thenReturn(new BigDecimal(.00));
        Assertions.assertEquals(new BigDecimal(.00), percentDiscountOffer.getDiscount(basket));
    }

    @Test
    public void getDiscountOn3ApplesBoughtIn5Days() {
        ShoppingBasket basket = TestMockUtil.getBasket(productProvider, "Apples", "Apples", "Apples");
        basket.setShoppingDate(LocalDate.now().plusDays(5));
        when(percentDiscountOffer.getDiscount(basket)).thenReturn(new BigDecimal(.03));
        Assertions.assertEquals(new BigDecimal(.03), percentDiscountOffer.getDiscount(basket));
    }
}