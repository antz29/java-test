package com.ford.henrysgroceries;

import com.ford.henrysgroceries.offers.Offer;
import com.ford.henrysgroceries.products.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Basket {

    private List<Product> products;

    private List<Offer> offers;

    public Basket() {
        products = new ArrayList<>();
        offers = new ArrayList<>();
    }

    public Basket(List<Offer> offers, Product... products) {
        this.products = Arrays.asList(products);
        this.offers = offers;
    }

    public BigDecimal calculateTotal() {
        offers.forEach(offer -> offer.apply(this));

        return products.stream()
                .map(product -> product.hasDiscount() ? product.getDiscountPrice() : product.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
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
