package com.industriallogic.henrysgroceries.service;

import com.industriallogic.henrysgroceries.model.ShoppingBasket;
import com.industriallogic.henrysgroceries.provider.OffersProvider;
import com.industriallogic.henrysgroceries.provider.ProductProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PriceShoppingBasketService {

    private ProductProvider productProvider;

    private OffersProvider offersProvider;

    public PriceShoppingBasketService(ProductProvider productProvider, OffersProvider offersProvider) {
        this.productProvider = productProvider;
        this.offersProvider = offersProvider;
    }

    public BigDecimal totalPriceToPay(ShoppingBasket shoppingBasket) {
        return shoppingBasket.getCurTotalAmount().subtract(getTotalDiscount(shoppingBasket)).setScale(2);
    }

    public BigDecimal getTotalDiscount(ShoppingBasket shoppingBasket) {
        return offersProvider.getOffers().stream()
                .map(offer -> offer.getDiscount(shoppingBasket))
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2);
    }
}
