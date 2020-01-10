package com.jfsoftware.henrys.cli;

import com.jfsoftware.henrys.model.Product;

import java.math.BigDecimal;

final class  ReceiptLine {
    private final Product.Unit unit;
    private final Product product;
    private final BigDecimal price;

    private ReceiptLine(final Product.Unit unit, final Product product, final BigDecimal price) {
        this.unit = unit;
        this.product = product;
        this.price = price;
    }

    static ReceiptLine aReceiptContaining(final Product.Unit unit, final Product product, final BigDecimal price) {
        return new ReceiptLine(unit, product, price);
    }

    Product.Unit getUnit() {
        return unit;
    }

    Product getProduct() {
        return product;
    }

    BigDecimal getPrice() {
        return price;
    }
}