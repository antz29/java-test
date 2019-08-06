package unit.calculator;

import calculator.PromotionValidity;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PromotionValidityTest {

    @Test
    public void checkPromotionValidNow() {
        PromotionValidity promotion = new PromotionValidity(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        assertTrue(promotion.checkPromotionValid(LocalDateTime.now()));
    }

    @Test
    public void checkPromotionValidForFutureDate() {
        PromotionValidity promotion = new PromotionValidity(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5));
        assertTrue(promotion.checkPromotionValid(LocalDateTime.now().plusDays(2)));
    }

    @Test
    public void checkPromotionValidForPastDate() {
        PromotionValidity promotion = new PromotionValidity(LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(3));
        assertTrue(promotion.checkPromotionValid(LocalDateTime.now().minusDays(4)));
    }

    @Test
    public void checkPromotionValidForTheSameDayItEnds() {
        PromotionValidity promotion = new PromotionValidity(LocalDateTime.now().minusDays(5), LocalDateTime.now().plusDays(3));
        assertTrue(promotion.checkPromotionValid(LocalDateTime.now().plusDays(3).minusSeconds(5)));
    }

    @Test
    public void checkPromotionValidForTheSameDayItStarts() {
        PromotionValidity promotion = new PromotionValidity(LocalDateTime.now().minusDays(5), LocalDateTime.now().plusDays(3));
        assertTrue(promotion.checkPromotionValid(LocalDateTime.now().minusDays(5).plusSeconds(5)));
    }

    @Test
    public void checkPromotionInvalidWhenDateBeforeStartTime() {
        PromotionValidity promotion = new PromotionValidity(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        assertFalse(promotion.checkPromotionValid(LocalDateTime.now().minusDays(4)));
    }

    @Test
    public void checkPromotionInvalidWhenDateAfterEndTime() {
        PromotionValidity promotion = new PromotionValidity(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        assertFalse(promotion.checkPromotionValid(LocalDateTime.now().plusDays(4)));
    }
}