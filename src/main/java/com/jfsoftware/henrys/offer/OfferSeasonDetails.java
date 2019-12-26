package com.jfsoftware.henrys.offer;

import java.time.LocalDate;

class OfferSeasonDetails {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate buyDate;

    boolean isOfferInSeason() {
        return (buyDate.isAfter(startDate) || buyDate.isEqual(startDate))
                && (buyDate.isBefore(endDate) || buyDate.isEqual(endDate));
    }

    void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    void setBuyDate(LocalDate buyDate) {
        this.buyDate = buyDate;
    }
}