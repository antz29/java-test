package com.industriallogic.henrysgroceries.offers;

import com.industriallogic.henrysgroceries.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PercentageDiscountOffer implements  Offer{
    private String offerName;
    private Product product;
    private BigDecimal discountPercentage;
    private LocalDate offerStartDate;
    private LocalDate offerEndDate;

    public PercentageDiscountOffer(String offerName, Product product, BigDecimal discountPercentage, LocalDate offerStartDate, LocalDate offerEndDate) {
        this.offerName = offerName;
        this.product = product;
        this.discountPercentage = discountPercentage;
        this.offerStartDate = offerStartDate;
        this.offerEndDate = offerEndDate;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public LocalDate getOfferStartDate() {
        return offerStartDate;
    }

    public void setOfferStartDate(LocalDate offerStartDate) {
        this.offerStartDate = offerStartDate;
    }

    public LocalDate getOfferEndDate() {
        return offerEndDate;
    }

    public void setOfferEndDate(LocalDate offerEndDate) {
        this.offerEndDate = offerEndDate;
    }

}
