package com.ford.henrysgroceries.offers;

import java.time.LocalDate;

public abstract class AbstractOffer implements Offer {
    LocalDate start;
    LocalDate end;

    boolean notApplicable(LocalDate date) {
        return date.isBefore(start) || date.isAfter(end);
    }
}
