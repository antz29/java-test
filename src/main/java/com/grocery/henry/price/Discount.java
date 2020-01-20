package com.grocery.henry.price;

import com.grocery.henry.domain.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static java.math.BigDecimal.valueOf;

public interface Discount {
    default BigDecimal calculatePrice(Map<Product, Integer> products, Product product, LocalDate orderDate) {
        return product.getPrice().multiply(valueOf(products.get(product)));
    }
}
