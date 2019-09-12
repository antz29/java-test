package com.ford.henrysgroceries;

import com.ford.henrysgroceries.products.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Basket {

    private List<Product> products;

    public Basket() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public BigDecimal calculateTotal() {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
