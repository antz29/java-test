package com.industriallogic.henrysgroceries.offers;

import com.industriallogic.henrysgroceries.model.ShoppingBasket;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Offer {

    BigDecimal ONE_HUNDRED = new BigDecimal(100);

    default boolean isOfferStillValid(LocalDate shoppingDate, LocalDate offerStartDate, LocalDate offerEndDate) {
        return ( shoppingDate.compareTo(offerStartDate) >= 0  &&  shoppingDate.compareTo(offerEndDate) <= 0) ;
    }

    public BigDecimal getDiscount(ShoppingBasket basket);
}
