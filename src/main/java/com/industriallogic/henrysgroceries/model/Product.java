package com.industriallogic.henrysgroceries.model;

import java.math.BigDecimal;
import java.util.Objects;


public class Product {
    private String productCode;
    private String name;
    private BigDecimal price;
    private MeasurementUnit unit;

    public Product(String productCode, String name, BigDecimal price, MeasurementUnit unit) {
        this.productCode = productCode;
        this.name = name;
        this.price = price;
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(getProductCode(), product.getProductCode()) &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getPrice(), product.getPrice()) &&
                getUnit() == product.getUnit();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductCode(), getName(), getPrice(), getUnit());
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MeasurementUnit getUnit() {
        return unit;
    }

    public void setUnit(MeasurementUnit unit) {
        this.unit = unit;
    }

}
