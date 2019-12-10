package com.test.harrys;

import com.test.harrys.basket.ShoppingBasket;
import com.test.harrys.model.Product;
import com.test.harrys.model.ShoppingListItem;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
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
        ShoppingBasket basket = new ShoppingBasket();
        Arrays.stream(shoppingList).forEach(p ->  basket.addItem(new ShoppingListItem(p)));
        BigDecimal invoiceSubTotal = basket.getShoppingListItems().stream().map(basketItem -> {
            BigDecimal itemPrice = getProductPrice(basketItem.getProductCode());
            BigDecimal lineTotal = itemPrice.multiply(new BigDecimal(basketItem.getQuantity()))
                    .setScale( myNumDecimals, RoundingMode.HALF_UP);;
            return lineTotal; }).reduce(BigDecimal.ZERO, BigDecimal::add);
        return invoiceSubTotal;
    }

    static void setProductOffering(Set<Product> catalogue) {
        ShoppingTill.PRODUCT_SET.addAll(catalogue);
    }
}

