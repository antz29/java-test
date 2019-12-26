package com.jfsoftware.henrys;

import java.math.BigDecimal;

public class StockItem {
    private final Product product;

    public StockItem(final Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return product.getPrice();
    }
}