package com.industriallogic.henrysgroceries.service;

import com.industriallogic.henrysgroceries.model.ShoppingBasket;
import com.industriallogic.henrysgroceries.provider.OffersProvider;
import com.industriallogic.henrysgroceries.provider.ProductProvider;
import com.industriallogic.henrysgroceries.testutil.TestMockUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PriceShoppingBasketServiceTest {

    @InjectMocks
    private PriceShoppingBasketService pricingService;

    @Mock
    private OffersProvider OffersProvider;

    @Mock
    private static ProductProvider productProvider;

    @Test
    public void price1SoupAnd1BreadNoOfferAppliedTest() {
        ShoppingBasket basket = TestMockUtil.getBasket(productProvider, "Soup", "Bread");
        when(OffersProvider.getOffers()).thenReturn(TestMockUtil.getOffersList());
        BigDecimal bigDecimal = pricingService.priceShoppingBasket(basket);
        assertEquals(BigDecimal.valueOf(1.45), bigDecimal);
    }

    @Test
    public void price2SoupAnd1BreadComboOfferAppliedTest() {
        ShoppingBasket basket =TestMockUtil.getBasket(productProvider, "Soup", "Soup","Bread");
        when(OffersProvider.getOffers()).thenReturn(TestMockUtil.getOffersList());
        BigDecimal bigDecimal = pricingService.priceShoppingBasket(basket);
        assertEquals(BigDecimal.valueOf(1.70).setScale(2), bigDecimal);
    }

    @Test
    public void price2SoupAnd1BreadBoughtYesterdayComboOfferAppliedTest() {
        ShoppingBasket basket = TestMockUtil.getBasket(productProvider, "Soup", "Soup","Bread");
        basket.setShoppingDate(LocalDate.now().minusDays(1));
        when(OffersProvider.getOffers()).thenReturn(TestMockUtil.getOffersList());
        BigDecimal bigDecimal = pricingService.priceShoppingBasket(basket);
        assertEquals(BigDecimal.valueOf(1.70).setScale(2), bigDecimal);
    }

    @Test
    public void price2SoupAnd1BreadBought2DaysAgoNoOfferAppliedTest() {
        ShoppingBasket basket = TestMockUtil.getBasket(productProvider, "Soup", "Soup","Bread");
        basket.setShoppingDate(LocalDate.now().minusDays(2));
        when(OffersProvider.getOffers()).thenReturn(TestMockUtil.getOffersList());
        BigDecimal bigDecimal = pricingService.priceShoppingBasket(basket);
        assertEquals(BigDecimal.valueOf(2.10).setScale(2), bigDecimal);
    }

    @Test
    public void price2SoupAnd1BreadBoughtIn6DaysComboOfferAppliedTest() {
        ShoppingBasket basket = TestMockUtil.getBasket(productProvider, "Soup", "Soup","Bread");
        basket.setShoppingDate(LocalDate.now().plusDays(6));
        when(OffersProvider.getOffers()).thenReturn(TestMockUtil.getOffersList());
        BigDecimal bigDecimal = pricingService.priceShoppingBasket(basket);
        assertEquals(BigDecimal.valueOf(1.70).setScale(2), bigDecimal);
    }

    @Test
    public void price2SoupAnd1BreadBoughtIn7DaysNoOfferAppliedTest() {
        ShoppingBasket basket = TestMockUtil.getBasket(productProvider, "Soup", "Soup","Bread");
        basket.setShoppingDate(LocalDate.now().plusDays(7));
        when(OffersProvider.getOffers()).thenReturn(TestMockUtil.getOffersList());
        BigDecimal bigDecimal = pricingService.priceShoppingBasket(basket);
        assertEquals(BigDecimal.valueOf(2.10).setScale(2), bigDecimal);
    }

    @Test
    public void price3SoupAnd2BreadComboOfferAppliedOn1BreadTest() {
        ShoppingBasket basket = TestMockUtil.getBasket(productProvider, "Soup", "Soup", "Soup", "Bread", "Bread");
        when(OffersProvider.getOffers()).thenReturn(TestMockUtil.getOffersList());

        BigDecimal bigDecimal = pricingService.priceShoppingBasket(basket);
        assertEquals(BigDecimal.valueOf(3.15), bigDecimal);
    }

    @Test
    public void price4SoupAnd3BreadComboOfferAppliedOn2BreadTest() {
        ShoppingBasket basket = TestMockUtil.getBasket(productProvider, "Soup", "Soup", "Soup", "Soup", "Bread", "Bread", "Bread");
        when(OffersProvider.getOffers()).thenReturn(TestMockUtil.getOffersList());

        BigDecimal bigDecimal = pricingService.priceShoppingBasket(basket);
        assertEquals(BigDecimal.valueOf(4.20).setScale(2), bigDecimal);
    }

    @Test
    public void price4SoupAnd1BreadComboOfferAppliedOn1BreadTest() {
        ShoppingBasket basket = TestMockUtil.getBasket(productProvider, "Soup", "Soup", "Soup", "Soup", "Bread");
        when(OffersProvider.getOffers()).thenReturn(TestMockUtil.getOffersList());

        BigDecimal bigDecimal = pricingService.priceShoppingBasket(basket);
        assertEquals(BigDecimal.valueOf(3.00).setScale(2), bigDecimal);
    }

    @Test
    public void price6ApplesAndMilkBoughtTodayNoOfferApplied() {
        ShoppingBasket basket =TestMockUtil.getBasket(productProvider, "Apples", "Apples", "Apples", "Apples", "Apples", "Apples", "Milk");

        when(OffersProvider.getOffers()).thenReturn(TestMockUtil.getOffersList());

        BigDecimal bigDecimal = pricingService.priceShoppingBasket(basket);
        assertEquals(BigDecimal.valueOf(1.90).setScale(2), bigDecimal);
    }

    @Test
    public void price6ApplesAndMilkBoughtIn5DaysPercentOfferApplied() {
        ShoppingBasket basket = TestMockUtil.getBasket(productProvider, "Apples", "Apples", "Apples", "Apples", "Apples", "Apples", "Milk");
        basket.setShoppingDate(LocalDate.now().plusDays(5));
        when(OffersProvider.getOffers()).thenReturn(TestMockUtil.getOffersList());

        BigDecimal bigDecimal = pricingService.priceShoppingBasket(basket);
        assertEquals(BigDecimal.valueOf(1.84), bigDecimal);
    }

    @Test
    public void price6ApplesBoughtAfterEndOfFollowingMonthOfferApplied() {
        ShoppingBasket basket = TestMockUtil.getBasket(productProvider, "Apples", "Apples", "Apples", "Apples", "Apples", "Apples" );
        LocalDate firstOfNextMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
        LocalDate endOfNextMonth = firstOfNextMonth.with(TemporalAdjusters.lastDayOfMonth());
        basket.setShoppingDate(endOfNextMonth.plusDays(1));
        when(OffersProvider.getOffers()).thenReturn(TestMockUtil.getOffersList());

        BigDecimal bigDecimal = pricingService.priceShoppingBasket(basket);
        assertEquals(BigDecimal.valueOf(.60).setScale(2), bigDecimal);
    }

    @Test
    public void price3ApplesAnd2SoupBoughtIn5DaysPercentOfferApplied() {
        ShoppingBasket basket = TestMockUtil.getBasket(productProvider, "Apples", "Apples", "Apples", "Soup", "Soup");

        basket.setShoppingDate(LocalDate.now().plusDays(5));
        when(OffersProvider.getOffers()).thenReturn(TestMockUtil.getOffersList());

        BigDecimal bigDecimal = pricingService.priceShoppingBasket(basket);
        assertEquals(BigDecimal.valueOf(1.57), bigDecimal);
    }

    @Test
    public void price3ApplesAnd2SoupAnd1BreadBoughtIn5DaysComboAndPercentOfferApplied() {
        ShoppingBasket basket = TestMockUtil.getBasket(productProvider, "Apples", "Apples", "Apples", "Soup", "Soup", "Bread");

        basket.setShoppingDate(LocalDate.now().plusDays(5));
        when(OffersProvider.getOffers()).thenReturn(TestMockUtil.getOffersList());

        BigDecimal bigDecimal = pricingService.priceShoppingBasket(basket);
        assertEquals(BigDecimal.valueOf(1.97), bigDecimal);
    }
}
