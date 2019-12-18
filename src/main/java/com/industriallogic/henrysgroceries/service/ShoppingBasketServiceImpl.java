package com.industriallogic.henrysgroceries.service;

import com.industriallogic.henrysgroceries.exception.ProductNotFoundException;
import com.industriallogic.henrysgroceries.model.ShoppingBasket;
import com.industriallogic.henrysgroceries.provider.OffersProvider;
import com.industriallogic.henrysgroceries.provider.ProductProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class ShoppingBasketServiceImpl implements  ShoppingBasketService {

    private ProductProvider productProvider;

    private OffersProvider offersProvider;

    private ShoppingBasket basket;

    public ShoppingBasketServiceImpl(ProductProvider productProvider, OffersProvider offersProvider, ShoppingBasket basket) {
        this.productProvider = productProvider;
        this.offersProvider = offersProvider;
        this.basket = basket;
    }

    /**
     * Calculates the final amount to pay after applying discount if any
     * @return totalAmount to pay
     */
    @Override
    public BigDecimal totalPriceToPay( ) {
        return basket.getCurTotalAmount().subtract(getTotalDiscount()).setScale(2);
    }

    /**
     * Calculates the total discount amount to apply on the basket
     * @return total discount
     */
    @Override
    public BigDecimal getTotalDiscount() {
        return offersProvider.getOffers().stream()
                .map(offer -> offer.getDiscount(basket))
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2);
    }

    /**
     * Adds a product in to basket
     * @param productName
     * @return current total amount
     * @throws ProductNotFoundException, if product is not found in product repository
     */
    @Override
    public BigDecimal addProductToBasket(String productName) throws ProductNotFoundException {
        return basket.addProductToBasket(productProvider.getProduct(productName));
    }
}
