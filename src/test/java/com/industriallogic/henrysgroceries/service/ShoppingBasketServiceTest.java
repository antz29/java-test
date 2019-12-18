package com.industriallogic.henrysgroceries.service;

import com.industriallogic.henrysgroceries.exception.ProductNotFoundException;
import com.industriallogic.henrysgroceries.model.MeasurementUnit;
import com.industriallogic.henrysgroceries.model.Product;
import com.industriallogic.henrysgroceries.model.ShoppingBasket;
import com.industriallogic.henrysgroceries.provider.OffersProvider;
import com.industriallogic.henrysgroceries.provider.ProductProvider;
import com.industriallogic.henrysgroceries.testutil.TestMockUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ShoppingBasketServiceTest {

    @InjectMocks
    private ShoppingBasketServiceImpl shoppingBasketService;

    @Mock
    private OffersProvider OffersProvider;

    @Mock
    private static ProductProvider productProvider;

    @Mock
    private static ShoppingBasket shoppingBasket;

    @Before
    public void setUP() throws ProductNotFoundException {
        when(productProvider.getProduct("Apples")).thenReturn(new Product("A123", "Apple", BigDecimal.valueOf(.10), MeasurementUnit.SINGLE));
        when(productProvider.getProduct("Bread")).thenReturn(new Product("B123", "Bread", BigDecimal.valueOf(.80), MeasurementUnit.LOAF));
        when(productProvider.getProduct("Milk")).thenReturn(new Product("M123", "Milk", BigDecimal.valueOf(1.30), MeasurementUnit.BOTTLE));
        when(productProvider.getProduct("Soup")).thenReturn(new Product("S123", "Soup", BigDecimal.valueOf(.65), MeasurementUnit.TIN));

        when(OffersProvider.getOffers()).thenReturn(TestMockUtil.getOffersList());
    }

    @Test
    public void add2ApplesToBasketTest() throws ProductNotFoundException {
        when(shoppingBasket.getShoppingDate()).thenReturn(LocalDate.now());
        when(shoppingBasketService.addProductToBasket("Soup")).thenReturn(BigDecimal.valueOf(0.65));
        when(shoppingBasketService.addProductToBasket("Bread")).thenReturn(BigDecimal.valueOf(1.45));
        when(shoppingBasket.getCurTotalAmount()).thenReturn(BigDecimal.valueOf(1.45));
        assertEquals(BigDecimal.valueOf(1.45), shoppingBasketService.totalPriceToPay());
    }

    @Test
    public void price1SoupAnd1BreadNoOfferAppliedTest() throws ProductNotFoundException {
        when(shoppingBasket.getShoppingDate()).thenReturn(LocalDate.now());
        when(shoppingBasketService.addProductToBasket("Soup")).thenReturn(BigDecimal.valueOf(0.65));
        when(shoppingBasketService.addProductToBasket("Bread")).thenReturn(BigDecimal.valueOf(1.45));
        when(shoppingBasket.getCurTotalAmount()).thenReturn(BigDecimal.valueOf(1.45));
        BigDecimal totalPriceToPay = shoppingBasketService.totalPriceToPay();
        assertEquals(BigDecimal.valueOf(1.45), totalPriceToPay);
    }

    @Test
    public void price2SoupAnd1BreadComboOfferAppliedTest() throws ProductNotFoundException {
        when(shoppingBasket.getShoppingDate()).thenReturn(LocalDate.now());
        when(shoppingBasketService.addProductToBasket("Soup")).thenReturn(BigDecimal.valueOf(0.65));
        when(shoppingBasketService.addProductToBasket("Soup")).thenReturn(BigDecimal.valueOf(1.30));
        when(shoppingBasketService.addProductToBasket("Bread")).thenReturn(BigDecimal.valueOf(2.10));
         Map<Product, Integer> productMap = new HashMap<Product, Integer>() {{
            put(productProvider.getProduct("Soup"), 2);
            put(productProvider.getProduct("Bread"), 1);
        }};
        when(shoppingBasket.getProductsInBasket()).thenReturn(productMap);
        when(shoppingBasket.getCurTotalAmount()).thenReturn(BigDecimal.valueOf(2.10));
        BigDecimal totalPriceToPay = shoppingBasketService.totalPriceToPay();
        assertEquals(BigDecimal.valueOf(1.70).setScale(2), totalPriceToPay);
    }

    @Test
    public void price2SoupAnd1BreadBoughtYesterdayComboOfferAppliedTest() throws ProductNotFoundException {
        when(shoppingBasket.getShoppingDate()).thenReturn(LocalDate.now().minusDays(1));
        when(shoppingBasketService.addProductToBasket("Soup")).thenReturn(BigDecimal.valueOf(0.65));
        when(shoppingBasketService.addProductToBasket("Soup")).thenReturn(BigDecimal.valueOf(1.30));
        when(shoppingBasketService.addProductToBasket("Bread")).thenReturn(BigDecimal.valueOf(2.10));
         Map<Product, Integer> productMap = new HashMap<Product, Integer>() {{
            put(productProvider.getProduct("Soup"), 2);
            put(productProvider.getProduct("Bread"), 1);
        }};
        when(shoppingBasket.getProductsInBasket()).thenReturn(productMap);
        when(shoppingBasket.getCurTotalAmount()).thenReturn(BigDecimal.valueOf(2.10));
        BigDecimal totalPriceToPay = shoppingBasketService.totalPriceToPay();
        assertEquals(BigDecimal.valueOf(1.70).setScale(2), totalPriceToPay);
    }

    @Test
    public void price2SoupAnd1BreadBoughtIn6DaysComboOfferAppliedTest() throws ProductNotFoundException{
        when(shoppingBasket.getShoppingDate()).thenReturn(LocalDate.now().plusDays(6));

        when(shoppingBasketService.addProductToBasket("Soup")).thenReturn(BigDecimal.valueOf(0.65));
        when(shoppingBasketService.addProductToBasket("Soup")).thenReturn(BigDecimal.valueOf(1.30));
        when(shoppingBasketService.addProductToBasket("Bread")).thenReturn(BigDecimal.valueOf(2.10));
         Map<Product, Integer> productMap = new HashMap<Product, Integer>() {{
            put(productProvider.getProduct("Soup"), 2);
            put(productProvider.getProduct("Bread"), 1);
        }};
        when(shoppingBasket.getProductsInBasket()).thenReturn(productMap);
        when(shoppingBasket.getCurTotalAmount()).thenReturn(BigDecimal.valueOf(2.10));
        BigDecimal totalPriceToPay = shoppingBasketService.totalPriceToPay();
        assertEquals(BigDecimal.valueOf(1.70).setScale(2), totalPriceToPay);
    }

    @Test
    public void price2SoupAnd1BreadBoughtIn7DaysNoOfferAppliedTest() throws ProductNotFoundException{
        when(shoppingBasket.getShoppingDate()).thenReturn(LocalDate.now().plusDays(7));

        when(shoppingBasketService.addProductToBasket("Soup")).thenReturn(BigDecimal.valueOf(0.65));
        when(shoppingBasketService.addProductToBasket("Soup")).thenReturn(BigDecimal.valueOf(1.30));
        when(shoppingBasketService.addProductToBasket("Bread")).thenReturn(BigDecimal.valueOf(2.10));
         Map<Product, Integer> productMap = new HashMap<Product, Integer>() {{
            put(productProvider.getProduct("Soup"), 2);
            put(productProvider.getProduct("Bread"), 1);
        }};
        when(shoppingBasket.getProductsInBasket()).thenReturn(productMap);
        when(shoppingBasket.getCurTotalAmount()).thenReturn(BigDecimal.valueOf(2.10));
        BigDecimal totalPriceToPay = shoppingBasketService.totalPriceToPay();
        assertEquals(BigDecimal.valueOf(2.10).setScale(2), totalPriceToPay);
    }

    @Test
    public void price3SoupAnd2BreadComboOfferAppliedOn1BreadTest() throws  ProductNotFoundException{
        when(shoppingBasket.getShoppingDate()).thenReturn(LocalDate.now());

        when(shoppingBasketService.addProductToBasket("Soup")).thenReturn(BigDecimal.valueOf(0.65));
        when(shoppingBasketService.addProductToBasket("Soup")).thenReturn(BigDecimal.valueOf(1.30));
        when(shoppingBasketService.addProductToBasket("Soup")).thenReturn(BigDecimal.valueOf(1.95));
        when(shoppingBasketService.addProductToBasket("Bread")).thenReturn(BigDecimal.valueOf(2.75));
        when(shoppingBasketService.addProductToBasket("Bread")).thenReturn(BigDecimal.valueOf(3.55));
         Map<Product, Integer> productMap = new HashMap<Product, Integer>() {{
            put(productProvider.getProduct("Soup"), 3);
            put(productProvider.getProduct("Bread"), 2);
        }};
        when(shoppingBasket.getProductsInBasket()).thenReturn(productMap);
        when(shoppingBasket.getCurTotalAmount()).thenReturn(BigDecimal.valueOf(3.55));
        BigDecimal totalPriceToPay = shoppingBasketService.totalPriceToPay();
        assertEquals(BigDecimal.valueOf(3.15), totalPriceToPay);
    }


    @Test
    public void price6ApplesAndMilkBoughtTodayNoOfferApplied() throws ProductNotFoundException {
        when(shoppingBasket.getShoppingDate()).thenReturn(LocalDate.now());

        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.10));
        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.20));
        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.30));
        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.40));
        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.50));
        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.60));
        when(shoppingBasketService.addProductToBasket("Milk")).thenReturn(BigDecimal.valueOf(1.90));
        Map<Product, Integer> productMap = new HashMap<Product, Integer>() {{
            put(productProvider.getProduct("Apples"), 6);
            put(productProvider.getProduct("Milk"), 1);
        }};
        when(shoppingBasket.getProductsInBasket()).thenReturn(productMap);
        when(shoppingBasket.getCurTotalAmount()).thenReturn(BigDecimal.valueOf(1.90));
        BigDecimal totalPriceToPay = shoppingBasketService.totalPriceToPay();
        assertEquals(BigDecimal.valueOf(1.90).setScale(2), totalPriceToPay);
    }

    @Test
    public void price6ApplesAndMilkBoughtIn5DaysPercentOfferApplied() throws ProductNotFoundException {
        when(shoppingBasket.getShoppingDate()).thenReturn(LocalDate.now().plusDays(5));

        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.10));
        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.20));
        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.30));
        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.40));
        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.50));
        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.60));
        when(shoppingBasketService.addProductToBasket("Milk")).thenReturn(BigDecimal.valueOf(1.90));
        Map<Product, Integer> productMap = new HashMap<Product, Integer>() {{
            put(productProvider.getProduct("Apples"), 6);
            put(productProvider.getProduct("Milk"), 1);
        }};
        when(shoppingBasket.getProductsInBasket()).thenReturn(productMap);
        when(shoppingBasket.getCurTotalAmount()).thenReturn(BigDecimal.valueOf(1.90));
        BigDecimal totalPriceToPay = shoppingBasketService.totalPriceToPay();
        assertEquals(BigDecimal.valueOf(1.84).setScale(2), totalPriceToPay);
    }

    @Test
    public void price6ApplesBoughtAfterEndOfFollowingMonthOfferApplied() throws ProductNotFoundException {
        LocalDate firstOfNextMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
        LocalDate endOfNextMonth = firstOfNextMonth.with(TemporalAdjusters.lastDayOfMonth());
        when(shoppingBasket.getShoppingDate()).thenReturn(endOfNextMonth.plusDays(1));

        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.10));
        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.20));
        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.30));
        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.40));
        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.50));
        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.60));

        Map<Product, Integer> productMap = new HashMap<Product, Integer>() {{
            put(productProvider.getProduct("Apples"), 6);
            put(productProvider.getProduct("Milk"), 1);
        }};
        when(shoppingBasket.getProductsInBasket()).thenReturn(productMap);
        when(shoppingBasket.getCurTotalAmount()).thenReturn(BigDecimal.valueOf(0.60));
        BigDecimal totalPriceToPay = shoppingBasketService.totalPriceToPay();
        assertEquals(BigDecimal.valueOf(.60).setScale(2), totalPriceToPay);
    }


    @Test
    public void price3ApplesAnd2SoupAnd1BreadBoughtIn5DaysComboAndPercentOfferApplied() throws ProductNotFoundException {
        when(shoppingBasket.getShoppingDate()).thenReturn(LocalDate.now().plusDays(5));

        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.10));
        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.20));
        when(shoppingBasketService.addProductToBasket("Apples")).thenReturn(BigDecimal.valueOf(0.30));
        when(shoppingBasketService.addProductToBasket("Soup")).thenReturn(BigDecimal.valueOf(0.95));
        when(shoppingBasketService.addProductToBasket("Soup")).thenReturn(BigDecimal.valueOf(1.60));
        when(shoppingBasketService.addProductToBasket("Bread")).thenReturn(BigDecimal.valueOf(2.40));

        Map<Product, Integer> productMap = new HashMap<Product, Integer>() {{
            put(productProvider.getProduct("Apples"), 3);
            put(productProvider.getProduct("Soup"), 2);
            put(productProvider.getProduct("Bread"), 1);
        }};
        when(shoppingBasket.getProductsInBasket()).thenReturn(productMap);
        when(shoppingBasket.getCurTotalAmount()).thenReturn(BigDecimal.valueOf(2.40));
        BigDecimal totalPriceToPay = shoppingBasketService.totalPriceToPay();
        assertEquals(BigDecimal.valueOf(1.97), totalPriceToPay);
    }
}
