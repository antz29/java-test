package com.grocery.henry.price;

import com.grocery.henry.domain.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static com.grocery.henry.domain.Product.SOUP;
import static com.grocery.henry.util.DiscountUtil.isBreadOfferValid;
import static java.math.BigDecimal.valueOf;

public class BreadDiscount implements Discount {

    public BigDecimal calculatePrice(Map<Product, Integer> products, Product product, LocalDate orderDate) {
        if (isBreadOfferValid(orderDate)) {
            if (products.containsKey(SOUP) && products.get(SOUP) >= 2) {
                int numberOfDiscounts = products.get(SOUP) / 2;
                BigDecimal breadPriceTotal = (product.getPrice().divide(valueOf(2))).multiply(valueOf(numberOfDiscounts));
                return breadPriceTotal.add(product.getPrice().multiply(valueOf((products.get(product) - numberOfDiscounts))));
            }
        }
        return product.getPrice().multiply(valueOf(products.get(product)));
    }
}
