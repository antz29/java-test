package com.ford.henrysgroceries.products;

import java.math.BigDecimal;

public class Product {

    private final String name;

    private final String unit;

    private final BigDecimal price;

    public Product(String name, String unit, BigDecimal price) {
        this.name = name;
        this.unit = unit;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
