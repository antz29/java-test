package com.industriallogic.henrysgroceries.offers;

import java.time.LocalDate;

public interface Offer {

    default boolean isOfferStillValid(LocalDate shoppingDate, LocalDate offerStartDate, LocalDate offerEndDate) {
        return ( shoppingDate.compareTo(offerStartDate) >= 0  &&  shoppingDate.compareTo(offerEndDate) <= 0) ;
    }
}
