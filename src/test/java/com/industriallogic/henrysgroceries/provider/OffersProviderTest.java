package com.industriallogic.henrysgroceries.provider;


import com.industriallogic.henrysgroceries.exception.ProductNotFoundException;
import com.industriallogic.henrysgroceries.model.MeasurementUnit;
import com.industriallogic.henrysgroceries.model.Product;
import com.industriallogic.henrysgroceries.testutil.TestMockUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OffersProviderTest {

    @InjectMocks
    private OffersProvider offersProvider;

    @Mock
    private ProductProvider productProvider;

    @Before
    public void prepare() throws ProductNotFoundException {
        when(productProvider.getProduct("Bread")).thenReturn(new Product("B123", "Bread", BigDecimal.valueOf(.80), MeasurementUnit.LOAF));
        when(productProvider.getProduct("Soup")).thenReturn(new Product("S123", "Soup", BigDecimal.valueOf(.65), MeasurementUnit.TIN));
        when(productProvider.getProduct("Apples")).thenReturn(new Product("A123", "Apple", BigDecimal.valueOf(.10), MeasurementUnit.SINGLE));
        offersProvider.init();
    }

    @Test
    public void getOffers()  {
        assertEquals(TestMockUtil.getOffersList(), offersProvider.getOffers());
    }
}