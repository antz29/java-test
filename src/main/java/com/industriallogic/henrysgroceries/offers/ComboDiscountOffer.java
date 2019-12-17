package com.industriallogic.henrysgroceries.offers;

import com.industriallogic.henrysgroceries.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@Data
public class ComboDiscountOffer implements Offer {

    @NonNull
    private String offerName;
    @NonNull
    private Product qualifyingProduct;
    @NonNull
    private Integer qualifyingProductQuantity;
    @NonNull
    private Product offerOnProduct;
    @NonNull
    private BigDecimal discountFactor;
    @NonNull
    private LocalDate offerStartDate;
    @NonNull
    private LocalDate offerEndDate;

}

