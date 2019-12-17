package com.industriallogic.henrysgroceries.provider;

import com.industriallogic.henrysgroceries.exception.ProductNotFoundException;
import com.industriallogic.henrysgroceries.model.MeasurementUnit;
import com.industriallogic.henrysgroceries.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductProviderTest {

    @Mock
    private  ProductProvider productProvider;

    @Test
    public void getProduct() throws ProductNotFoundException {
        Product  apple =  new Product("A123", "Apple", BigDecimal.valueOf(.10), MeasurementUnit.SINGLE);
        when(productProvider.getProduct("Apples")).thenReturn(apple);
        assertEquals(productProvider.getProduct("Apples"), apple);
    }

    @Test(expected=ProductNotFoundException.class)
    public void getProductWithSpace() throws ProductNotFoundException {
        when(productProvider.getProduct(" ")).thenThrow(new ProductNotFoundException("Product Not found"));
        productProvider.getProduct(" ");
    }

    @Test(expected=ProductNotFoundException.class)
    public void getInvalidProduct() throws ProductNotFoundException {
        when(productProvider.getProduct("chocolate")).thenThrow(new ProductNotFoundException("Product Not found"));
        productProvider.getProduct("chocolate");
    }
}