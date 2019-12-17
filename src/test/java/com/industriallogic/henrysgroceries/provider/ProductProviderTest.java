package com.industriallogic.henrysgroceries.provider;

import com.industriallogic.henrysgroceries.model.MeasurementUnit;
import com.industriallogic.henrysgroceries.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductProviderTest {

    @Mock
    private  ProductProvider productProvider;

    @Test
    public void getProduct() {
        Optional<Product> apple = Optional.of(new Product("A123", "Apple", BigDecimal.valueOf(.10), MeasurementUnit.SINGLE));
        when(productProvider.getProduct("Apples")).thenReturn(apple);
        assertEquals(productProvider.getProduct("Apples"), apple);
    }

    @Test
    public void getProductWithSpace() {
        Optional<Product> noProduct = Optional.empty();
        when(productProvider.getProduct(" ")).thenReturn(noProduct);
        assertEquals(productProvider.getProduct(" "), noProduct);
    }

    @Test
    public void getInvalidProduct() {
        Optional<Product> noProduct = Optional.empty();
        when(productProvider.getProduct("chocolate")).thenReturn(noProduct);
        assertEquals(productProvider.getProduct("chocolate"), noProduct);
    }

}