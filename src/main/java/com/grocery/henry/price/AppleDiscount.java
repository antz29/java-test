package com.grocery.henry.price;

import com.grocery.henry.domain.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static com.grocery.henry.domain.Product.APPLE;
import static com.grocery.henry.util.DiscountUtil.isMilkOfferValid;
import static java.math.BigDecimal.valueOf;

public class AppleDiscount implements Discount {
    private static int DISCOUNT_PERCENTAGE = 10;

    public BigDecimal calculatePrice(Map<Product, Integer> products, Product product, LocalDate orderDate) {
        BigDecimal totalApplePrice = product.getPrice().multiply(valueOf(products.get(APPLE)));
        if (isMilkOfferValid(orderDate)) {
            return totalApplePrice.subtract(totalApplePrice.multiply(valueOf(DISCOUNT_PERCENTAGE)).divide(valueOf(100)));
        }
        return totalApplePrice;
    }
}
