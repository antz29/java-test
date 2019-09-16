package com.ford.henrysgroceries.products;

import java.math.BigDecimal;

public class ProductHelper {

    public static Product soup() {
        return new Product("Soup", "tin", new BigDecimal("0.65"));
    }

    public static Product bread() {
        return new Product("Bread", "loaf", new BigDecimal("0.80"));
    }

    public static Product milk() {
        return new Product("Milk", "bottle", new BigDecimal("1.30"));
    }

    public static Product apples() {
        return new Product("Apples", "single", new BigDecimal("0.10"));
    }
}
