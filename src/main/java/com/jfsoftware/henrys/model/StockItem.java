package com.jfsoftware.henrys.model;

import java.math.BigDecimal;

public class StockItem {
    private final Product product;
    private final Product.Unit unit;

    public StockItem(final Product product) {
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