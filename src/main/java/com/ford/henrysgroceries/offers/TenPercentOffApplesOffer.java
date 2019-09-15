package com.ford.henrysgroceries.offers;

import com.ford.henrysgroceries.Basket;
import com.ford.henrysgroceries.products.Product;

import java.math.BigDecimal;

import static com.ford.henrysgroceries.products.ProductHelper.apples;

public class TenPercentOffApplesOffer implements Offer {

    private Product discountedProduct;
    private BigDecimal percentage;

    public TenPercentOffApplesOffer() {
        this.discountedProduct = apples();
        this.percentage = new BigDecimal(10);
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