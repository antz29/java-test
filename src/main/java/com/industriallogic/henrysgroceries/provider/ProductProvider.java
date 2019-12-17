package com.industriallogic.henrysgroceries.provider;

import com.industriallogic.henrysgroceries.exception.ProductNotFoundException;
import com.industriallogic.henrysgroceries.model.MeasurementUnit;
import com.industriallogic.henrysgroceries.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
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

    public Product getProduct(String productName) throws ProductNotFoundException {
        return Optional.ofNullable(productList.get(productName.toUpperCase())).orElseThrow(() -> {
            LOGGER.error("No Product found for {} ", productName);
            return new ProductNotFoundException("No Product found for - " + productName);
        });
    }
}
