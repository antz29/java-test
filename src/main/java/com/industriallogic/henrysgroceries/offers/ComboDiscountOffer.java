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
public class ComboDiscountOffer implements Offer {

    @NonNull
    private String offerName;
    @NonNull
    private Product qualifyingProduct;
    @NonNull
    private Integer qualifyingProdMinQnty;
    @NonNull
    private Product offerOnProduct;
    @NonNull
    private BigDecimal discountFactor;
    @NonNull
    private LocalDate offerStartDate;
    @NonNull
    private LocalDate offerEndDate;

    /**
     * Applies Combo discount offer if basket qualifies for the offer
     * @param basket - basket with products
     * @return - discount amount to be applied on the basket
     */
    @Override
    public BigDecimal getDiscount(ShoppingBasket basket) {
        if (isOfferStillValid(basket.getShoppingDate(), offerStartDate, offerEndDate)) {
            Integer qualifyingProdCountInBasket = basket.getProductsInBasket().getOrDefault(qualifyingProduct, 0);
            if (qualifyingProdCountInBasket >= qualifyingProdMinQnty) {
                int purchaseQnty = basket.getProductsInBasket().getOrDefault(offerOnProduct, 0);
                int eligibleOfferCount = Math.min(qualifyingProdCountInBasket/qualifyingProdMinQnty, purchaseQnty);
                return offerOnProduct.getPrice().multiply(discountFactor).divide(ONE_HUNDRED ).multiply( new BigDecimal(eligibleOfferCount)).setScale(2);
            }
        }
        return BigDecimal.ZERO;
    }
}

