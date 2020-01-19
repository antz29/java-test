package com.grocery.henry;

import com.grocery.henry.domain.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.grocery.henry.domain.Product.APPLE;
import static com.grocery.henry.domain.Product.BREAD;
import static com.grocery.henry.domain.Product.MILK;
import static com.grocery.henry.domain.Product.SOUP;

public class Basket {
    private Map<Product, Integer> products = new HashMap();

    public void add(Product productName, int count) {
        products.put(productName, count);
    }


    public BigDecimal calculate() {
        BigDecimal totalPrice = BigDecimal.valueOf(0.0);
        for (Product product : products.keySet()) {
            switch (product) {
                case SOUP:
                    totalPrice = totalPrice.add(SOUP.getPrice().multiply(BigDecimal.valueOf(products.get(product))));
                    break;
                case BREAD:
                    totalPrice = totalPrice.add(getBreadDiscount());
                    break;
                case MILK:
                    totalPrice = totalPrice.add(MILK.getPrice().multiply(BigDecimal.valueOf(products.get(product))));
                    break;
                case APPLE:
                    totalPrice = totalPrice.add(APPLE.getPrice().multiply(BigDecimal.valueOf(products.get(product))));
                    break;
                default:
                    System.out.println("no match");
            }
        }
        return totalPrice;
    }

    private BigDecimal getBreadDiscount() {
        BigDecimal breadPriceTotal;
        if (products.containsKey(SOUP) && products.get(SOUP) >= 2) {
            int numberOfDiscounts = products.get(SOUP) / 2;
            breadPriceTotal = (BREAD.getPrice().divide(BigDecimal.valueOf(2))).multiply(BigDecimal.valueOf(numberOfDiscounts));

            return breadPriceTotal.add(BREAD.getPrice().multiply(BigDecimal.valueOf((products.get(BREAD) - numberOfDiscounts))));
        }
        return BREAD.getPrice().multiply(BigDecimal.valueOf(products.get(BREAD)));
    }
}
