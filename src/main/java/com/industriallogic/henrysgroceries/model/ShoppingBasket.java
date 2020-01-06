package com.industriallogic.henrysgroceries.model;


import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Component
public class ShoppingBasket {

    private final Map<Product, Integer> basket = new HashMap<>();
    private LocalDate shoppingDate = LocalDate.now();
    private BigDecimal curTotalAmount = BigDecimal.ZERO;

    public BigDecimal addProductToBasket(Product product) {
        basket.compute(product,
                (k, v) -> {
                    return v == null ? 1 : v + 1;
                });
        curTotalAmount = curTotalAmount.add(product.getPrice());
        return curTotalAmount;
    }

    public LocalDate getShoppingDate() {
        return this.shoppingDate;
    }

    public Map<Product, Integer> getProductsInBasket() {
        return Collections.unmodifiableMap(basket);
    }

    public BigDecimal getCurTotalAmount() {
        return curTotalAmount;
    }

    public void setShoppingDate(LocalDate shoppingDate) {
        this.shoppingDate = shoppingDate;
    }
}



