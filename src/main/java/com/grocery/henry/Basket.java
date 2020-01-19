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
import static com.grocery.henry.util.DiscountUtil.isBreadOfferValid;
import static com.grocery.henry.util.DiscountUtil.isMilkOfferValid;
import static java.math.BigDecimal.valueOf;

public class Basket {
    private Map<Product, Integer> products = new HashMap();

    public void add(Product productName, int count) {
        products.put(productName, count);
    }

    public BigDecimal calculate(LocalDate orderDate) {
        BigDecimal totalPrice = valueOf(0.0);
        for (Product product : products.keySet()) {
            switch (product) {
                case SOUP:
                    totalPrice = totalPrice.add(SOUP.getPrice().multiply(valueOf(products.get(product))));
                    break;
                case BREAD:
                    totalPrice = totalPrice.add(breadPriceIncludingDiscount(orderDate));
                    break;
                case MILK:
                    totalPrice = totalPrice.add(MILK.getPrice().multiply(valueOf(products.get(product))));
                    break;
                case APPLE:
                    totalPrice = totalPrice.add(applePriceIncludingDiscount(orderDate));
                    break;
                default:
                    System.out.println("no match");
            }
        }
        return totalPrice;
    }

    private BigDecimal breadPriceIncludingDiscount(LocalDate orderDate) {
        BigDecimal breadPriceTotal;
        if (isBreadOfferValid(orderDate)) {
            if (products.containsKey(SOUP) && products.get(SOUP) >= 2) {
                int numberOfDiscounts = products.get(SOUP) / 2;
                breadPriceTotal = (BREAD.getPrice().divide(valueOf(2))).multiply(valueOf(numberOfDiscounts));
                return breadPriceTotal.add(BREAD.getPrice().multiply(valueOf((products.get(BREAD) - numberOfDiscounts))));
            }
        }
        return BREAD.getPrice().multiply(valueOf(products.get(BREAD)));
    }


    private BigDecimal applePriceIncludingDiscount(LocalDate orderDate) {
        BigDecimal totalApplePrice = APPLE.getPrice().multiply(valueOf(products.get(APPLE)));
        if (isMilkOfferValid(orderDate)) {
            return totalApplePrice.subtract(totalApplePrice.multiply(valueOf(10)).divide(valueOf(100)));
        }
        return totalApplePrice;
    }
}
