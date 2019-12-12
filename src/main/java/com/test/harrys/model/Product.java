package com.test.harrys.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private String name;
    private String productCode;
    private BigDecimal pricePerUnit;

    public Product(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return pricePerUnit;
    }

    public void setPrice(BigDecimal price) {
        this.pricePerUnit = price;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public boolean equals(Object o) {
        Product toBeCompared = (Product)o;
        return productCode.equals(toBeCompared.getProductCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productCode);
    }

}

