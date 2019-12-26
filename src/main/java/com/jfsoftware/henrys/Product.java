package com.jfsoftware.henrys;

import java.math.BigDecimal;

public enum Product {

    MILK(Unit.BOTTLE, new BigDecimal("1.30")),
    APPLE(Unit.SINGLE, new BigDecimal("0.10"));

    final private Unit unit;
    final private BigDecimal price;

    Product(Unit unit, BigDecimal price) {
        this.unit = unit;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public enum Unit {
        BOTTLE, SINGLE
    }
}