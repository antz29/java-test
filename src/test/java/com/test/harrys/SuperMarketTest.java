package com.test.harrys;

import com.test.harrys.model.Product;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class SuperMarketTest {

    @BeforeClass
    public static void init(){
        Set<Product> products = new HashSet<Product>();
        Product product = new Product();
        product.setName("apple");
        product.setPrice(new BigDecimal("0.10"));
        product.setProductCode("apple");
        products.add(product);

        product = new Product();
        product.setName("soup");
        product.setPrice(new BigDecimal("0.65"));
        product.setProductCode("soup");
        products.add(product);

        product = new Product();
        product.setName("bread");
        product.setPrice(new BigDecimal("0.80"));
        product.setProductCode("bread");
        products.add(product);

        product = new Product();
        product.setName("milk");
        product.setPrice(new BigDecimal("1.30"));
        product.setProductCode("milk");
        products.add(product);

        ShoppingTill.setProductOffering(products);


    }


    @Test
    public void calculateTotalTest(){ Double expectedTotal =
            0.10 + 0.10 + 0.65 + 0.65 + 0.80 + 0.80 + 1.30;
        BigDecimal total = new BigDecimal(expectedTotal).
                setScale( 2, RoundingMode.HALF_UP);;
        String[] shoppingList =
                {"apple", "apple", "soup","soup","bread","bread","milk"};
        BigDecimal billTotal = ShoppingTill.calculateBill(shoppingList);
        assertEquals(total, billTotal);
    }


}
