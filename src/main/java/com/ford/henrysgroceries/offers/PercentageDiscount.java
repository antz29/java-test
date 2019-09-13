package com.ford.henrysgroceries.offers;

import com.ford.henrysgroceries.Basket;
import com.ford.henrysgroceries.products.Product;

import java.math.BigDecimal;

import static com.ford.henrysgroceries.products.ProductHelper.apples;
import static java.math.BigDecimal.ROUND_HALF_UP;

public class PercentageDiscount implements Offer {

    private Product discountedProduct;

    private BigDecimal percentage;

    public PercentageDiscount(Product discountedProduct, int percentage) {
        this.discountedProduct = discountedProduct;
        this.percentage = new BigDecimal(percentage);
    }

    @Override
    public Basket apply(Basket basket) {
        basket.getProducts().stream()
                .filter(product -> product.getName().equals(discountedProduct.getName()))
                .forEach(product -> product.setDiscountPrice(setDiscountPrice(product)));
        return basket;
    }

    private BigDecimal setDiscountPrice(Product product) {
        BigDecimal price = product.getPrice();
        BigDecimal discount = price.divide(new BigDecimal("100.00")).multiply(percentage);
        return price.subtract(discount);
    }
}