package com.test.harrys.model;

import com.test.harrys.basket.ShoppingBasket;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author kay
 *encapsulates discount that may be applied to a product based on number items purchased
 */
public abstract class ShoppingDiscount {

    private double discountAmount;
    private String productCode;
    private double triggerQuantity;
    private String discountDescription;
    private LocalDate startDate;
    private LocalDate endDate;
    


    /**
     * calculates discount that may apply to specific items purchased
     * @param item
     * @return amount of discount
     */
    public abstract BigDecimal calculateDiscountAmount(ShoppingBasket basket);

    @Override
    public boolean equals(Object o) {
        ShoppingDiscount toBeCompared = (ShoppingDiscount)o;
        return productCode.equals(toBeCompared.getProductCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productCode);
    }

    public boolean isActive(LocalDate shoppingDate){
        return startDate.isAfter(shoppingDate) && endDate.isBefore(shoppingDate);
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDiscountDescription() {
        return discountDescription;
    }

    public void setDiscountDescription(String discountDescription) {
        this.discountDescription = discountDescription;
    }

    public double getTriggerQuantity() {
        return triggerQuantity;
    }

    public void setTriggerQuantity(double triggerQuantity) {
        this.triggerQuantity = triggerQuantity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

