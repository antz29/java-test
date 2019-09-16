package com.ford.henrysgroceries.products;

import java.math.BigDecimal;

public class Product {

    private final String name;

    private final String unit;

    private final BigDecimal price;

    private BigDecimal discountPrice;

    public Product(String name, String unit, BigDecimal price) {
        this.name = name;
        this.unit = unit;
        this.price = price;
        discountPrice = BigDecimal.ZERO;
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

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public BigDecimal getDisplayPrice() {
        return hasDiscount() ? discountPrice : price;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public boolean hasDiscount() {
        return discountPrice.compareTo(BigDecimal.ZERO) != 0;
    }
}
