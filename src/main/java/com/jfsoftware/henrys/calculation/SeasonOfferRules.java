package com.jfsoftware.henrys.calculation;

import java.time.LocalDate;

public class SeasonOfferRules {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate buyDate;

    private SeasonOfferRules() {
    }

    private static SeasonOfferRules offerSeasonDetails = new SeasonOfferRules();

    public static SeasonOfferRules getInstance() {
        return offerSeasonDetails;
    }

    public boolean isOfferInSeason() {
        return (buyDate.isAfter(startDate) || buyDate.isEqual(startDate))
                && (buyDate.isBefore(endDate) || buyDate.isEqual(endDate));
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setBuyDate(LocalDate buyDate) {
        this.buyDate = buyDate;
    }
}