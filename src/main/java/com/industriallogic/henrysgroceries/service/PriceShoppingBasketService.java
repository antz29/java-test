package com.industriallogic.henrysgroceries.service;

import com.industriallogic.henrysgroceries.exception.ProductNotFoundException;
import com.industriallogic.henrysgroceries.model.Product;
import com.industriallogic.henrysgroceries.model.ShoppingBasket;
import com.industriallogic.henrysgroceries.provider.OffersProvider;
import com.industriallogic.henrysgroceries.provider.ProductProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Service
@Slf4j
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

    public void startShopping() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                LOGGER.info("Enter products separated with space to add to basket OR EXIT to quit: ");
                ShoppingBasket basket = new ShoppingBasket();
                List<String> productsNameList = Arrays.asList(StringUtils.split(scanner.nextLine()));
                if (productsNameList.contains("EXIT")) return;
                productsNameList.forEach(productName -> {
                    try {
                        Product product = productProvider.getProduct(productName);
                        basket.addProductToBasket(product);
                        LOGGER.info("Added .. " + productName + " to basket");
                    } catch (ProductNotFoundException e) {
                        LOGGER.error("Product not found for .. " + productName);
                    }
                    });
                BigDecimal bigDecimal = priceShoppingBasket(basket);
                LOGGER.info("Total to Pay " + bigDecimal);
            }
        }
    }
}
