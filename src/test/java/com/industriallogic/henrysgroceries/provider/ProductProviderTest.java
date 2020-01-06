package com.industriallogic.henrysgroceries.provider;

import com.industriallogic.henrysgroceries.exception.ProductNotFoundException;
import com.industriallogic.henrysgroceries.model.MeasurementUnit;
import com.industriallogic.henrysgroceries.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProductProvider.class)
public class ProductProviderTest {

    @Autowired
    private  ProductProvider productProvider;

    @Before
    public void prepare() {
        productProvider.init();
    }

    @Test
    public void getProduct() throws ProductNotFoundException {
        Product  apple =  new Product("A123", "Apple", BigDecimal.valueOf(.10), MeasurementUnit.SINGLE);
        assertEquals(productProvider.getProduct("Apples"), apple);
    }

    @Test(expected=ProductNotFoundException.class)
    public void getProductWithSpace() throws ProductNotFoundException {
        productProvider.getProduct(" ");
    }

    @Test(expected=ProductNotFoundException.class)
    public void getInvalidProduct() throws ProductNotFoundException {
        productProvider.getProduct("chocolate");
    }
}