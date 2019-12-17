package com.industriallogic.henrysgroceries.provider;

import com.industriallogic.henrysgroceries.model.MeasurementUnit;
import com.industriallogic.henrysgroceries.model.Product;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ProductProvider {

    private Map<String, Product> productList;

    @PostConstruct
    public void init() {
        Map<String, Product> stock = new HashMap<>();
        stock.put("APPLES", new Product("A123","Apple",  BigDecimal.valueOf(.10) , MeasurementUnit.SINGLE));
        stock.put("BREAD", new Product("B123","Bread",  BigDecimal.valueOf(.80) , MeasurementUnit.LOAF));
        stock.put("MILK", new Product("M123", "Milk",  BigDecimal.valueOf(1.30) , MeasurementUnit.BOTTLE));
        stock.put("SOUP", new Product("S123","Soup",  BigDecimal.valueOf(.65) , MeasurementUnit.TIN));
        productList = Collections.unmodifiableMap(stock);
    }

    public Optional<Product> getProduct(String productName) {
        return Optional.ofNullable(productList.get(productName.toUpperCase()));
    }
}
