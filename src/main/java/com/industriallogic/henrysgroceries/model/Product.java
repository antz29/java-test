package com.industriallogic.henrysgroceries.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Data
public class Product {
    @NonNull private String productCode;
    @NonNull private String name;
    @NonNull private BigDecimal price;
    @NonNull private MeasurementUnit unit;
}
