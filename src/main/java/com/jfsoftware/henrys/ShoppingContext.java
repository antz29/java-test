package com.jfsoftware.henrys;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ShoppingContext {
    final private List<StockItem> basket = new ArrayList<>();
    private Set<Offer> offers;
    private long daysFromNowToBuy;

    public ShoppingContext(Set<Offer> offers) {
        this.offers = offers;
    }

    public void addItemToBasket(StockItem stockItem) {
        basket.add(stockItem);
    }

    public void setDaysFromNowToBuy(final long daysFromNowToBuy) {
        this.daysFromNowToBuy = daysFromNowToBuy;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalDiscount = offers
                .stream()
                .map(offer -> offer.calculateOfferDiscount(this))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        return calculateTotalNonDiscountedPrice().subtract(totalDiscount);
    }

    private BigDecimal calculateTotalNonDiscountedPrice() {
        return basket
                .stream()
                .map(StockItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public LocalDate calculateBuyDate() {
        return LocalDate.now().plusDays(daysFromNowToBuy);
    }

    public List<StockItem> getBasket() {
        return basket;
    }
    public void printReceipt() {
        if (basketIsEmpty()) {
            new Receipt()
                    .setItems(basket)
                    .setTotalPrice(getTotalPrice())
                    .print();
        }
    }

    private boolean basketIsEmpty() {
        return basket.size() > 0;
    }
}