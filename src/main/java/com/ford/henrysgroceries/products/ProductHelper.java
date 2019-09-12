package com.ford.henrysgroceries.products;

import java.math.BigDecimal;

public class ProductHelper {

    public static Product soup() {
        return new Product() {
            public String getProduct() {
                return "soup";
            }

            public String getUnit() {
                return "tin";
            }

            public BigDecimal getPrice() {
                return new BigDecimal("0.65");
            }
        };
    }

    public static Product bread() {
        return new Product() {
            public String getProduct() {
                return "bread";
            }

            public String getUnit() {
                return "loaf";
            }

            public BigDecimal getPrice() {
                return new BigDecimal("0.80");
            }
        };
    }

    public static Product milk() {
        return new Product() {
            public String getProduct() {
                return "milk";
            }

            public String getUnit() {
                return "bottle";
            }

            public BigDecimal getPrice() {
                return new BigDecimal("1.30");
            }
        };
    }

    public static Product apples() {
        return new Product() {
            public String getProduct() {
                return "apples";
            }

            public String getUnit() {
                return "single";
            }

            public BigDecimal getPrice() {
                return new BigDecimal("0.10");
            }
        };
    }
}