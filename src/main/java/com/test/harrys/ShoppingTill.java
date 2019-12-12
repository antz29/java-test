package com.test.harrys;

import com.test.harrys.basket.ShoppingBasket;
import com.test.harrys.model.Product;
import com.test.harrys.model.ShoppingDiscount;
import com.test.harrys.model.ShoppingListItem;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class ShoppingTill {
    final static Logger LOGGER = Logger.getLogger(ShoppingTill.class);

    private static final Set<ShoppingDiscount> discounts = new HashSet<>();

    private static final Set<Product> PRODUCT_SET = new HashSet<>();

    private static final int myNumDecimals = 2;

    /**
     * get product instance using the code
     * @param productCode
     * @return gets product instance
     */
    public static Product getProductByCode(String productCode) {
        return PRODUCT_SET.stream()
                .filter(product -> product.getProductCode().equals(productCode))
                .findFirst().orElseThrow(() -> new IllegalArgumentException(
                        String.format("Cannot find product with product code : [%s] in product offerings",
                        productCode)));
    }

    /**
     * get price of product
     * @param productCode
     * @return price of related product
     */
    public static BigDecimal getProductPrice(String productCode){
        return getProductByCode(productCode).getPrice();
    }

    /**
     * adds any discounts entries to the invoice if any apply to the items purchased
     * @param listItem item purchased
     * @return total discount amount for item purchased
     */
    public static BigDecimal calculateDiscountTotal(ShoppingBasket basket, ShoppingListItem listItem){
        BigDecimal discountAmount = BigDecimal.ZERO;
        Optional<ShoppingDiscount> shoppingDiscount = discounts.stream()
                        .filter(discount -> discount.getProductCode().equals(listItem.getProductCode()))
                        .findFirst();
        if(shoppingDiscount.isPresent()){
            discountAmount = shoppingDiscount.get().calculateDiscountAmount(basket);
        }
        return discountAmount;
    }

    static BigDecimal calculateBill(ShoppingBasket basket) {
        double subTotal = basket.getShoppingListItems().stream().mapToDouble(basketItem ->
            getProductPrice(basketItem.getProductCode()).doubleValue() * basketItem.getQuantity()).sum();
        double discountTotal = basket.getShoppingListItems().stream().mapToDouble(items ->
                calculateDiscountTotal(basket, items).doubleValue()).sum();
        return BigDecimal.valueOf(subTotal - discountTotal).setScale( myNumDecimals, RoundingMode.HALF_UP);
    }

    static BigDecimal calculateBill(String[] shoppingList) {
        ShoppingBasket basket = new ShoppingBasket();
        Arrays.stream(shoppingList).forEach(p ->  basket.addItem(new ShoppingListItem(p)));
        return calculateBill(basket);
    }

    static void setProductOffering(Set<Product> catalogue) {
        ShoppingTill.PRODUCT_SET.addAll(catalogue);
    }

    public static void setDiscounts(Set<ShoppingDiscount> discounts) {
        ShoppingTill.discounts.clear();
        ShoppingTill.discounts.addAll(discounts);
    }
}

