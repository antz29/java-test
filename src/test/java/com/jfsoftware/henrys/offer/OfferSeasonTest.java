package com.jfsoftware.henrys.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class OfferSeasonTest {

    private OfferSeasonDetails offerSeasonDetails;
    private LocalDate yesterday;
    private LocalDate today;
    private LocalDate tomorrow;
    private LocalDate twoDaysFromToday;
    private LocalDate threeDaysFromToday;
    private LocalDate buyDate;

    @BeforeEach
    void createOfferDetails() {
        offerSeasonDetails = new OfferSeasonDetails();
        yesterday = LocalDate.now().minusDays(1);
        today = yesterday.plusDays(1);
        tomorrow = today.plusDays(1);
        twoDaysFromToday = today.plusDays(2);
        threeDaysFromToday = today.plusDays(3);
    }

    @Test
    void offerWithBuyDateBetweenStartDateAndEndDateIsAnInSeasonOffer() {
        offerSeasonDetails.setStartDate(today);
        offerSeasonDetails.setEndDate(twoDaysFromToday);
        buyDate = tomorrow;
        offerSeasonDetails.setBuyDate(buyDate);
        assertThat(offerSeasonDetails.isOfferInSeason()).isTrue();
    }

    @Test
    void offerWithBuyDateTheSameAsStartDateIsAnInSeasonOffer() {
        offerSeasonDetails.setStartDate(today);
        offerSeasonDetails.setEndDate(twoDaysFromToday);
        buyDate = today;
        offerSeasonDetails.setBuyDate(buyDate);
        assertThat(offerSeasonDetails.isOfferInSeason()).isTrue();
    }

    @Test
    void offerWithBuyDateTheSameAsEndDateIsAnInSeasonOffer() {
        offerSeasonDetails.setStartDate(today);
        offerSeasonDetails.setEndDate(twoDaysFromToday);
        buyDate = twoDaysFromToday;
        offerSeasonDetails.setBuyDate(buyDate);
        assertThat(offerSeasonDetails.isOfferInSeason()).isTrue();
    }

    @Test
    void offerWithBuyDateGreaterThanEndDateIsAnOutOfSeasonOffer() {
        offerSeasonDetails.setStartDate(today);
        offerSeasonDetails.setEndDate(twoDaysFromToday);
        buyDate = threeDaysFromToday;
        offerSeasonDetails.setBuyDate(buyDate);
        assertThat(offerSeasonDetails.isOfferInSeason()).isFalse();
    }

    @Test
    void offerWithBuyDateLessThanStartDateIsAnOutOfSeasonOffer() {
        offerSeasonDetails.setStartDate(today);
        offerSeasonDetails.setEndDate(twoDaysFromToday);
        buyDate = yesterday;
        offerSeasonDetails.setBuyDate(buyDate);
        assertThat(offerSeasonDetails.isOfferInSeason()).isFalse();
    }
}