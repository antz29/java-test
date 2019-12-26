package com.jfsoftware.henrys;

import java.math.BigDecimal;

public interface Offer {
    BigDecimal calculateOfferDiscount(final ShoppingContext shoppingContext);
}