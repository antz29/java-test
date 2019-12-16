package com.industriallogic.henrysgroceries.offers;

import com.industriallogic.henrysgroceries.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ComboDiscountOffer implements Offer {

    private String offerName;
    private Product qualifyingProduct;
    private Integer qualifyingProductQuantity;
    private Product offerOnProduct;
    private BigDecimal discountFactor;
    private LocalDate offerStartDate;
    private LocalDate offerEndDate;

    public ComboDiscountOffer(String offerName, Product qualifyingProduct, Integer qualifyingProductQuantity, Product offerOnProduct, BigDecimal discountFactor, LocalDate offerStartDate, LocalDate offerEndDate) {
        this.offerName = offerName;
        this.qualifyingProduct = qualifyingProduct;
        this.qualifyingProductQuantity = qualifyingProductQuantity;
        this.offerOnProduct = offerOnProduct;
        this.discountFactor = discountFactor;
        this.offerStartDate = offerStartDate;
        this.offerEndDate = offerEndDate;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

}

