package com.grocery.henry;

import com.grocery.henry.domain.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.grocery.henry.domain.Product.APPLE;
import static com.grocery.henry.domain.Product.BREAD;
import static com.grocery.henry.domain.Product.MILK;
import static com.grocery.henry.domain.Product.SOUP;
import static java.math.BigDecimal.valueOf;

public class Basket {
    private Map<Product, Integer> products = new HashMap();

    public void add(Product product, int count) {
        products.put(product, count);
    }

    public BigDecimal calculate(LocalDate orderDate) {
        BigDecimal total = valueOf(0.0);
        for (Product product : products.keySet()) {
            switch (product) {
                case SOUP:
                    BigDecimal soupPrice = SOUP.getPrice().multiply(valueOf(products.get(product)));
                    System.out.println("Soup Price : " + soupPrice);
                    total = total.add(soupPrice);
                    break;
                case BREAD:
                    BigDecimal breadPrice = BREAD.getDiscount().calculatePrice(products, BREAD, orderDate);
                    System.out.println("Bread Price : " + breadPrice);
                    total = total.add(breadPrice);
                    break;
                case MILK:
                    BigDecimal milkPrice = MILK.getPrice().multiply(valueOf(products.get(product)));
                    System.out.println("Milk Price : " + milkPrice);
                    total = total.add(milkPrice);
                    break;
                case APPLE:
                    BigDecimal applePrice = APPLE.getDiscount().calculatePrice(products, APPLE, orderDate);
//                    BigDecimal applePrice = applePriceIncludingDiscount(orderDate);
                    System.out.println("Apple Price : " + applePrice);
                    total = total.add(applePrice);
                    break;
                default:
                    System.out.println("no match");
            }
        }
        return total;
    }

//    private BigDecimal breadPriceIncludingDiscount(LocalDate orderDate) {
//        if (isBreadOfferValid(orderDate)) {
//            if (products.containsKey(SOUP) && products.get(SOUP) >= 2) {
//                int numberOfDiscounts = products.get(SOUP) / 2;
//                BigDecimal breadPriceTotal = (BREAD.getPrice().divide(valueOf(2))).multiply(valueOf(numberOfDiscounts));
//                return breadPriceTotal.add(BREAD.getPrice().multiply(valueOf((products.get(BREAD) - numberOfDiscounts))));
//            }
//        }
//        return BREAD.getPrice().multiply(valueOf(products.get(BREAD)));
//    }
//
//
//    private BigDecimal applePriceIncludingDiscount(LocalDate orderDate) {
//        BigDecimal totalApplePrice = APPLE.getPrice().multiply(valueOf(products.get(APPLE)));
//        if (isMilkOfferValid(orderDate)) {
//            return totalApplePrice.subtract(totalApplePrice.multiply(valueOf(10)).divide(valueOf(100)));
//        }
//        return totalApplePrice;
//    }
}
