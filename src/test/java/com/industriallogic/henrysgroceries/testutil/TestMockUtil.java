package com.industriallogic.henrysgroceries.testutil;

import com.industriallogic.henrysgroceries.model.MeasurementUnit;
import com.industriallogic.henrysgroceries.model.Product;
import com.industriallogic.henrysgroceries.offers.ComboDiscountOffer;
import com.industriallogic.henrysgroceries.offers.Offer;
import com.industriallogic.henrysgroceries.offers.PercentageDiscountOffer;
import com.industriallogic.henrysgroceries.provider.ProductProvider;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class TestMockUtil {


    @SneakyThrows
    public static Map<Product, Integer> get2Soup1BreadProductMap(ProductProvider productProvider ) {
        return new HashMap<Product, Integer>() {{
            put(productProvider.getProduct("Soup"), 2);
            put(productProvider.getProduct("Bread"), 1);
        }};
    }

    public static List<Offer> getOffersList() {
        Product apples = new Product("A123", "Apple", BigDecimal.valueOf(.10), MeasurementUnit.SINGLE);
        Product bread = new Product("B123", "Bread", BigDecimal.valueOf(.80), MeasurementUnit.LOAF);
        Product milk = new Product("M123", "Milk", BigDecimal.valueOf(1.30), MeasurementUnit.BOTTLE);
        Product soup = new Product("S123", "Soup", BigDecimal.valueOf(.65), MeasurementUnit.TIN);
        LocalDate firstOfNextMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
        LocalDate endOfNextMonth = firstOfNextMonth.with(TemporalAdjusters.lastDayOfMonth());
        PercentageDiscountOffer applesOffer = new PercentageDiscountOffer("Apple 10% OFF", apples, BigDecimal.valueOf(10), LocalDate.now().plusDays(3), endOfNextMonth);
        ComboDiscountOffer discountOffer = new ComboDiscountOffer("combo Offer", soup, 2, bread, BigDecimal.valueOf(50), LocalDate.now().minusDays(1), LocalDate.now().plusDays(6));
        return Collections.unmodifiableList(Arrays.asList(applesOffer, discountOffer));
    }

    private static Map<String, Product> productList() {
        Map<String, Product> products = new HashMap();
        products.put("APPLES", new Product("A123", "Apple", BigDecimal.valueOf(.10), MeasurementUnit.SINGLE));
        products.put("BREAD", new Product("B123", "Bread", BigDecimal.valueOf(.80), MeasurementUnit.LOAF));
        products.put("MILK", new Product("M123", "Milk", BigDecimal.valueOf(1.30), MeasurementUnit.BOTTLE));
        products.put("SOUP", new Product("S123", "Soup", BigDecimal.valueOf(.65), MeasurementUnit.TIN));
        return products;
    }

}
