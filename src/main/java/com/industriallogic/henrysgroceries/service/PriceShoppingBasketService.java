package com.industriallogic.henrysgroceries.service;

import com.industriallogic.henrysgroceries.model.Product;
import com.industriallogic.henrysgroceries.model.ShoppingBasket;
import com.industriallogic.henrysgroceries.provider.OffersProvider;
import com.industriallogic.henrysgroceries.provider.ProductProvider;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class PriceShoppingBasketService {

    private ProductProvider productProvider;

    private OffersProvider offersProvider;

    public PriceShoppingBasketService(ProductProvider productProvider, OffersProvider offersProvider) {
        this.productProvider = productProvider;
        this.offersProvider = offersProvider;
    }

    public BigDecimal priceShoppingBasket(ShoppingBasket shoppingBasket) {
        Map<Product, Integer> basket = shoppingBasket.getProductsInBasket();
        BigDecimal totalDiscount = offersProvider.getOffers().stream()
                .map(offer -> offer.getDiscount(shoppingBasket))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPrice  = basket.entrySet().stream()
                .map(e -> e.getKey().getPrice().multiply(BigDecimal.valueOf(e.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalPrice.subtract(totalDiscount).setScale(2);
    }
}
