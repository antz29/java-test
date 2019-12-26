package com.jfsoftware.henrys;

import java.time.LocalDate;

public class OfferSeasonDetails {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate buyDate;

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