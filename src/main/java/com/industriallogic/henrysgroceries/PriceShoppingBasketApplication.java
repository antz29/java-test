package com.industriallogic.henrysgroceries;

import com.industriallogic.henrysgroceries.service.PriceShoppingBasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PriceShoppingBasketApplication implements CommandLineRunner {

    @Autowired
    PriceShoppingBasketService pricingService;

    public static void main(String[] args) {
        SpringApplication.run(PriceShoppingBasketApplication.class, args);
    }

    @Override
    public void run(String... args)  {
        pricingService.startShopping();
    }
}
