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
        BigDecimal total = valueOf(0.0);
        for (Product product : products.keySet()) {
            switch (product) {
                case SOUP:
                    total = total.add(SOUP.getPrice().multiply(valueOf(products.get(product))));
                    break;
                case BREAD:
                    total = total.add(breadPriceIncludingDiscount(orderDate));
                    break;
                case MILK:
                    total = total.add(MILK.getPrice().multiply(valueOf(products.get(product))));
                    break;
                case APPLE:
                    total = total.add(applePriceIncludingDiscount(orderDate));
                    break;
                default:
                    System.out.println("no match");
            }
        }
        return total;
    }

    private BigDecimal breadPriceIncludingDiscount(LocalDate orderDate) {
        if (isBreadOfferValid(orderDate)) {
            if (products.containsKey(SOUP) && products.get(SOUP) >= 2) {
                int numberOfDiscounts = products.get(SOUP) / 2;
                BigDecimal breadPriceTotal = (BREAD.getPrice().divide(valueOf(2))).multiply(valueOf(numberOfDiscounts));
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
