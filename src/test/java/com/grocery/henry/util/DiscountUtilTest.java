package com.grocery.henry.util;

import org.junit.Test;

import static com.grocery.henry.util.DiscountUtil.isBreadOfferValid;
import static com.grocery.henry.util.DiscountUtil.isMilkOfferValid;
import static java.time.LocalDate.now;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DiscountUtilTest {

    @Test
    public void shouldReturnValidBreadOffer() {
        assertTrue(isBreadOfferValid(now()));
    }

    @Test
    public void shouldNotReturnValidBreadOffer() {
        assertFalse(isBreadOfferValid(now().minusDays(2)));
    }

    @Test
    public void shouldReturnValidMilkOffer() {
        assertTrue(isMilkOfferValid(now().plusDays(5)));
    }

    @Test
    public void shouldNotReturnValidMilkOffer() {
        assertFalse(isMilkOfferValid(now()));
    }
}
