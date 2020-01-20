package com.grocery.henry.domain;

import com.grocery.henry.price.AppleDiscount;
import com.grocery.henry.price.BreadDiscount;
import com.grocery.henry.price.Discount;

import java.math.BigDecimal;

public enum Product {
    SOUP(BigDecimal.valueOf(0.65), new Discount() {}),
    BREAD(BigDecimal.valueOf(0.80), new BreadDiscount()),
    MILK(BigDecimal.valueOf(1.30), new Discount() {}),
    APPLE(BigDecimal.valueOf(0.10), new AppleDiscount());

    private BigDecimal price;
    private Discount discount;

    Product(BigDecimal price, Discount discount) {
        this.price = price;
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Discount getDiscount() {
        return discount;
    }
}
