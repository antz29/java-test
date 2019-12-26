package com.jfsoftware.henrys;

import java.math.BigDecimal;

public class ReceiptLine {
    private final Product.Unit unit;
    private final Product product;
    private final BigDecimal price;

    public ReceiptLine(final Product.Unit unit, final Product product, final BigDecimal price) {
        this.unit = unit;
        this.product = product;
        this.price = price;
    }

    static ReceiptLine aReceiptContaining(final Product.Unit unit, final Product product, final BigDecimal price) {
        return new ReceiptLine(unit, product, price);
    }

    public Product.Unit getUnit() {
        return unit;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }
}