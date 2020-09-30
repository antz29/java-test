package com.jfsoftware.henrys;

import com.jfsoftware.henrys.calculation.SeasonOfferRules;
import com.jfsoftware.henrys.model.Item;
import com.jfsoftware.henrys.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public final class Cart {
    final private List<Item> items = new ArrayList<>();

    public void addItem(final Item item) {
        items.add(item);
    }

    public void setDaysFromNowToBuy(final long daysFromNowToBuy) {
        SeasonOfferRules.getInstance().setBuyDate(LocalDate.now().plusDays(daysFromNowToBuy));
    }

    public List<Item> getItems() {
        return unmodifiableList(items);
    }

    public BigDecimal calculateTotalPrice() {
        return Product.getPriceCalculators()
                .stream()
                .map(c -> c.calculateDiscountedPrice(this))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }
}