package com.jfsoftware.henrys.offer;

import com.jfsoftware.henrys.ShoppingContext;

import java.math.BigDecimal;

public interface Offer {
    BigDecimal calculateOfferDiscount(final ShoppingContext shoppingContext);
}