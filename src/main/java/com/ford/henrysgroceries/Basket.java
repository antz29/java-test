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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Basket:\n");
        products.forEach(product -> display(sb, product));
        sb.append("Total: £").append(calculateTotal()).append("\n");
        return sb.toString();
    }

    private StringBuilder display(StringBuilder sb, Product product) {
        return sb.append(product.getName()).append(" ").append(product.getPrice()).append("\n");
    }
}
