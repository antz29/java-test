package com.industriallogic.henrysgroceries;

import com.industriallogic.henrysgroceries.exception.ProductNotFoundException;
import com.industriallogic.henrysgroceries.model.ShoppingBasket;
import com.industriallogic.henrysgroceries.service.ShoppingBasketServiceImpl;
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
    private ShoppingBasketServiceImpl shoppingBasketService;

    public static void main(String[] args) {
        SpringApplication.run(PriceShoppingBasketApplication.class, args);
    }

    @Override
    public void run(String... args)  {
        startShopping();
    }

    public void startShopping() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                LOGGER.info("Enter 1. To add products to basket | Enter 2. To checkout | Enter X. To EXIT");
                String option = scanner.next();
                switch (option) {
                    case "1":
                        addProductsToBasket(  scanner);
                        break;
                    case "2":
                        checkout( );
                        break;
                    case "X":
                        return;
                    default:
                        LOGGER.info("Invalid entry, please re-enter '1' OR '2' OR 'X'");
                }
            }
        }
    }

    private void addProductsToBasket(  Scanner scanner) {
        LOGGER.info("Enter products separated with space to add to basket: ");
        scanner.nextLine();
        List<String> productsNameList = Arrays.asList(StringUtils.split(scanner.nextLine()));
        productsNameList.forEach(productName -> {
            try {
                BigDecimal curntTotalAmount = shoppingBasketService.addProductToBasket(productName);
                LOGGER.info(String.format("Added %s to basket. Current total amount %.2f  " ,productName, curntTotalAmount));
            } catch (ProductNotFoundException e) {
                LOGGER.error("Product not found for .. " + productName);
            }
        });
    }

    private void checkout( ) {
        BigDecimal toPay = shoppingBasketService.totalPriceToPay();
        LOGGER.info("Total to Pay after discount " + toPay);
    }

}
