package com.grocery.henry.util;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class DiscountUtil {
    public static boolean isBreadOfferValid(LocalDate orderDate) {
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = startDate.plusDays(7);
        return orderDate.isAfter(startDate) && orderDate.isBefore(endDate);
    }

    public static boolean isMilkOfferValid(LocalDate orderDate) {
        LocalDate startDate = LocalDate.now().plusDays(3);
        LocalDate endDate = startDate.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        return orderDate.isAfter(startDate) && orderDate.isBefore(endDate);
    }
}
