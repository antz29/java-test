package com.ford.henrysgroceries.offers;

import com.ford.henrysgroceries.Basket;

import java.time.LocalDate;

public interface Offer {

    Basket apply(Basket basket, LocalDate date);
}
