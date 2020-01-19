package com.grocery.henry;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Basket {
    private Map<String, Integer> products = new HashMap();
    private BigDecimal soup = BigDecimal.valueOf(0.65);
    private BigDecimal bread = BigDecimal.valueOf(0.80);
    private BigDecimal milk = BigDecimal.valueOf(1.30);
    private BigDecimal apple = BigDecimal.valueOf(0.10);

    public void add(String productName, int count) {
        products.put(productName, count);
    }


    public BigDecimal calculate() {
        BigDecimal totalPrice = BigDecimal.valueOf(0.0);
        for (String productName : products.keySet()) {
            switch (productName) {
                case "soup":
                    totalPrice = totalPrice.add(soup.multiply(BigDecimal.valueOf(products.get(productName))));
                    break;
                case "bread":
                    totalPrice = totalPrice.add(getBreadDiscount());
                    break;
                case "milk":
                    totalPrice = totalPrice.add(milk.multiply(BigDecimal.valueOf(products.get(productName))));
                    break;
                case "apple":
                    totalPrice = totalPrice.add(apple.multiply(BigDecimal.valueOf(products.get(productName))));
                    break;
                default:
                    System.out.println("no match");
            }
        }
        return totalPrice;
    }

    private BigDecimal getBreadDiscount() {
        BigDecimal breadPriceTotal;
        if (products.containsKey("soup") && products.get("soup") >= 2) {
            int numberOfDiscounts = products.get("soup") / 2;
            breadPriceTotal = (bread.divide(BigDecimal.valueOf(2))).multiply(BigDecimal.valueOf(numberOfDiscounts));

            return breadPriceTotal.add(bread.multiply(BigDecimal.valueOf((products.get("bread") - numberOfDiscounts))));
        }
        return bread.multiply(BigDecimal.valueOf(products.get("bread")));
    }
}
