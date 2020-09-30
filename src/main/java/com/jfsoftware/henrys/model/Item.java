package com.jfsoftware.henrys.model;

import java.math.BigDecimal;

public final class Item {
    private final Product product;
    private final Product.Unit unit;

    public Item(final Product product) {
        this.product = product;
        this.unit = product.getUnit();
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return product.getPrice();
    }

    public Product.Unit getUnit() {
        return unit;
    }
}