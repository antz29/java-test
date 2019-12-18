package com.industriallogic.henrysgroceries.offers;

import com.industriallogic.henrysgroceries.model.Product;
import com.industriallogic.henrysgroceries.model.ShoppingBasket;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@Data
public class PercentageDiscountOffer implements Offer {
    @NonNull
    private String offerName;
    @NonNull
    private Product product;
    @NonNull
    private BigDecimal discountPercentage;
    @NonNull
    private LocalDate offerStartDate;
    @NonNull
    private LocalDate offerEndDate;

    public BigDecimal getDiscount(ShoppingBasket basket) {
        if (isOfferStillValid(basket.getShoppingDate(), offerStartDate, offerEndDate)) {
            Integer productQuantity = basket.getProductsInBasket().getOrDefault(product, 0);
            return product.getPrice().multiply(discountPercentage).divide(ONE_HUNDRED ).multiply(new BigDecimal(productQuantity));
        }
        return BigDecimal.ZERO;
    }
}
