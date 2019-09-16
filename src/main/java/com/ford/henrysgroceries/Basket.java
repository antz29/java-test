package com.ford.henrysgroceries;

import com.ford.henrysgroceries.offers.Offer;
import com.ford.henrysgroceries.products.Product;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Basket {

    private List<Product> products;
    private List<Offer> offers;
    private Clock clock;

    public Basket() {
        products = new ArrayList<>();
        offers = new ArrayList<>();
    }

    public Basket(List<Offer> offers) {
        products = new ArrayList<>();
        this.offers = offers;
    }

    public Basket(List<Offer> offers, Clock fixedClock, Product... products) {
        this.products = Arrays.asList(products);
        this.offers = offers;
        clock = fixedClock;
    }

    public BigDecimal calculateTotal() {
        offers.forEach(offer -> offer.apply(this, getDate()));

        return products.stream()
                .map(product -> product.hasDiscount() ? product.getDiscountPrice() : product.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private LocalDate getDate() {
        return clock == null ? LocalDate.now() : LocalDate.now(clock);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        calculateTotal();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Basket:\n");
        products.forEach(product -> display(sb, product));
        sb.append("Total: ").append(format(calculateTotal())).append("\n");
        return sb.toString();
    }

    private StringBuilder display(StringBuilder sb, Product product) {
        return sb.append(product.getName()).append(" ").append(format(product.getDisplayPrice())).append("\n");
    }

    private String format(BigDecimal price) {
        return NumberFormat.getCurrencyInstance().format(price);
    }
}
