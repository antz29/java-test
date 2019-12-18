package com.industriallogic.henrysgroceries.provider;

import com.industriallogic.henrysgroceries.exception.ProductNotFoundException;
import com.industriallogic.henrysgroceries.model.Product;
import com.industriallogic.henrysgroceries.offers.ComboDiscountOffer;
import com.industriallogic.henrysgroceries.offers.Offer;
import com.industriallogic.henrysgroceries.offers.PercentageDiscountOffer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class OffersProvider {

    private ProductProvider productProvider;

    private List<Offer> offers;

    public OffersProvider(ProductProvider productProvider) {
        this.productProvider = productProvider;
    }
    /**
     * Populate the offers available
     */
    @PostConstruct
    public void init() throws ProductNotFoundException {
        Product apples = productProvider.getProduct("Apples") ;
        Product soup = productProvider.getProduct("Soup") ;
        Product bread = productProvider.getProduct("Bread");

        LocalDate firstOfNextMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
        LocalDate endOfNextMonth = firstOfNextMonth.with(TemporalAdjusters.lastDayOfMonth());
        PercentageDiscountOffer applesOffer = new PercentageDiscountOffer("Apple 10% OFF", apples, BigDecimal.valueOf(10), LocalDate.now().plusDays(3), endOfNextMonth);
        ComboDiscountOffer discountOffer = new ComboDiscountOffer("COMBO OFFER", soup, 2, bread, BigDecimal.valueOf(50), LocalDate.now().minusDays(1), LocalDate.now().plusDays(6));
        offers = Collections.unmodifiableList(Arrays.asList(applesOffer, discountOffer));
    }

    public List<Offer> getOffers() {
        return offers;
    }
}
