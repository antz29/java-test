package com.industriallogic.henrysgroceries.testutil;

import com.industriallogic.henrysgroceries.model.MeasurementUnit;
import com.industriallogic.henrysgroceries.model.Product;
import com.industriallogic.henrysgroceries.offers.ComboDiscountOffer;
import com.industriallogic.henrysgroceries.offers.Offer;
import com.industriallogic.henrysgroceries.offers.PercentageDiscountOffer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestMockUtil {

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
}
