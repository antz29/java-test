package com.industriallogic.henrysgroceries;

import com.industriallogic.henrysgroceries.exception.ProductNotFoundException;
import com.industriallogic.henrysgroceries.model.ShoppingBasket;
import com.industriallogic.henrysgroceries.provider.ProductProvider;
import com.industriallogic.henrysgroceries.service.PriceShoppingBasketService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class PriceShoppingBasketApplication implements CommandLineRunner {

    @Autowired
    private PriceShoppingBasketService pricingService;
    @Autowired
    private ProductProvider  productProvider;

    public static void main(String[] args) {
        SpringApplication.run(PriceShoppingBasketApplication.class, args);
    }

    @Override
    public void run(String... args)  {
        startShopping();
    }

    public void startShopping() {
        ShoppingBasket basket = new ShoppingBasket();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                LOGGER.info("Enter 1. To add products to basket | Enter 2. To checkout | Enter X. To EXIT");
                String option = scanner.next();
                switch (option) {
                    case "1":
                        addProductsToBasket(basket, scanner);
                        break;
                    case "2":
                        checkout(basket);
                        break;
                    case "X":
                        return;
                    default:
                        LOGGER.info("Invalid entry, please re-enter '1' OR '2' OR 'X'");
                }
            }
        }
    }

    private void addProductsToBasket(ShoppingBasket basket, Scanner scanner) {
        LOGGER.info("Enter products separated with space to add to basket: ");
        scanner.nextLine();
        List<String> productsNameList = Arrays.asList(StringUtils.split(scanner.nextLine()));
        productsNameList.forEach(productName -> {
            try {
                BigDecimal curntTotalAmount = basket.addProductToBasket(productProvider.getProduct(productName));
                LOGGER.info("Added .. " + productName + " to basket");
                LOGGER.info("Current Total  Amount " + curntTotalAmount);
            } catch (ProductNotFoundException e) {
                LOGGER.error("Product not found for .. " + productName);
            }
        });
    }

    private void checkout(ShoppingBasket basket) {
        BigDecimal price = basket.getCurTotalAmount();
        BigDecimal discount = pricingService.getTotalDiscount(basket);
        LOGGER.info("Total to Pay " + price.subtract(discount).setScale(2));
    }

}
