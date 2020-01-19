package com.grocery.henry.util;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DiscountUtilTest {

    @Test
    public void shouldReturnValidBreadOffer() {
        assertTrue(DiscountUtil.isBreadOfferValid(LocalDate.now()));
    }

    @Test
    public void shouldNotReturnValidBreadOffer() {
        assertFalse(DiscountUtil.isBreadOfferValid(LocalDate.now().minusDays(2)));
    }

    @Test
    public void shouldReturnValidMilkOffer() {
        assertTrue(DiscountUtil.isMilkOfferValid(LocalDate.now().plusDays(5)));
    }

    @Test
    public void shouldNotReturnValidMilkOffer() {
        assertFalse(DiscountUtil.isMilkOfferValid(LocalDate.now()));
    }
}
