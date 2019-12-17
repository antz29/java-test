package com.industriallogic.henrysgroceries.offers;

import com.industriallogic.henrysgroceries.model.Product;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@Data
public class PercentageDiscountOffer implements Offer {
    @NonNull
    private String offerName;
    @NonNull
    private Product product;
    @NonNull
    private BigDecimal discountPercentage;
    @NonNull
    private LocalDate offerStartDate;
    @NonNull
    private LocalDate offerEndDate;

}
