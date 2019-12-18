package com.industriallogic.henrysgroceries.service;

import com.industriallogic.henrysgroceries.exception.ProductNotFoundException;

import java.math.BigDecimal;

public interface ShoppingBasketService {

    BigDecimal totalPriceToPay( ) ;

    BigDecimal getTotalDiscount( ) ;

    BigDecimal addProductToBasket(String productName) throws ProductNotFoundException ;
}
