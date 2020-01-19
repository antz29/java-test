package com.grocery.henry.domain;

import java.math.BigDecimal;

public enum Product {
    SOUP(BigDecimal.valueOf(0.65)),
    BREAD(BigDecimal.valueOf(0.80)),
    MILK(BigDecimal.valueOf(1.30)),
    APPLE(BigDecimal.valueOf(0.10));

    private BigDecimal price;

    Product(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
