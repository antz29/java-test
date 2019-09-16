package com.ford.henrysgroceries.offers;

import com.ford.henrysgroceries.Basket;
import com.ford.henrysgroceries.products.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.ford.henrysgroceries.products.ProductHelper.bread;
import static com.ford.henrysgroceries.products.ProductHelper.soup;

public class BuyTwoSoupsGetBreadHalfPriceOffer extends AbstractOffer {
    public BuyTwoSoupsGetBreadHalfPriceOffer(LocalDate today) {
        start = today.minusDays(1);
        end = start.plusDays(7);
    }

    @Override
    public Basket apply(Basket basket, LocalDate date) {
        if (notApplicable(date))
            return basket;

        List<Product> products = basket.getProducts();
        long numberOfSoup = products.stream()
                .filter(product -> product.getName().equals(soup().getName()))
                .count();

        if (numberOfSoup < 2)
            return basket;

        long numberOfDiscountsToApply = numberOfSoup / 2;

        for (Product product : products) {
            if (product.getName().equals(bread().getName()) && numberOfDiscountsToApply > 0) {
                product.setDiscountPrice(product.getPrice().divide(new BigDecimal(2)));
                numberOfDiscountsToApply--;
            }
        }

        return basket;
    }
}
