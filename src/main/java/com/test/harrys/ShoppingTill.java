package com.test.harrys;

import com.test.harrys.model.Product;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


public class ShoppingTill {
    final static Logger LOGGER = Logger.getLogger(ShoppingTill.class);

    private static final Set<Product> PRODUCT_SET = new HashSet<>();

    private static final int myNumDecimals = 2;

    /**
     * get product instance using the code
     * @param productCode
     * @return gets product instance
     */
    public static Product getProductByCode(String productCode) {
        Product searchResult = PRODUCT_SET.stream()
                .filter(product -> product.getProductCode().equals(productCode))
                .findFirst().orElseThrow(() -> new IllegalArgumentException(
                        String.format("Cannot find product with product code : [%s] in product offerings",
                        productCode)));
        return searchResult;
    }

    /**
     * get price of product
     * @param productCode
     * @return price of related product
     */
    public static BigDecimal getProductPrice(String productCode){
        Product searchResult = getProductByCode(productCode);
        return searchResult.getPrice();
    }



    public static BigDecimal calculateBill(String[] shoppingList) {

        return null;
    }

}

