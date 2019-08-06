package unit.calculator;

import calculator.ApplePromotion;
import item.Item;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThat;

public class ApplePromotionTest {

    private final Item testItem = Item.APPLE;

    @Test
    public void promotionNotActiveWhenDateOutRange() {
        Map<Item, Integer> items = new HashMap<>();
        items.put(testItem, 10);
        BigDecimal price = new ApplePromotion().calculatePrice(items, testItem, LocalDateTime.now());

        assertThat(BigDecimal.valueOf(1), Matchers.comparesEqualTo(price));
    }

    @Test
    public void applyPromotionWhenDateIsInRange() {
        Map<Item, Integer> items = new HashMap<>();
        items.put(testItem, 10);
        BigDecimal price = new ApplePromotion().calculatePrice(items, testItem, LocalDateTime.now().plusDays(5));

        assertThat(BigDecimal.valueOf(0.9), Matchers.comparesEqualTo(price));
    }

    @Test
    public void calculateZeroIfItemIsMissingInItemList() {
        Map<Item, Integer> items = new HashMap<>();
        items.put(Item.BREAD, 1);
        BigDecimal price = new ApplePromotion().calculatePrice(items, testItem, LocalDateTime.now().plusDays(5));

        assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(price));
    }

    @Test
    public void calculateCorrectDiscountWithManyItemsInList() {
        Map<Item, Integer> items = new HashMap<>();
        items.put(testItem, 5);
        items.put(Item.BREAD, 10);
        items.put(Item.MILK, 10);
        items.put(Item.SOUP, 10);
        BigDecimal price = new ApplePromotion().calculatePrice(items, testItem, LocalDateTime.now().plusDays(5));

        assertThat(BigDecimal.valueOf(0.45), Matchers.comparesEqualTo(price));
    }


    @Test
    public void promotionEndsAfterFollowingMonthEndDate() {
        Map<Item, Integer> items = new HashMap<>();
        items.put(testItem, 10);
        BigDecimal priceAfter = new ApplePromotion().calculatePrice(items, testItem, LocalDateTime.now().plusMonths(2).withDayOfMonth(1).plusSeconds(5));
        BigDecimal priceEndDate = new ApplePromotion().calculatePrice(items, testItem, LocalDateTime.now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).minusSeconds(5));

        assertThat(BigDecimal.ONE, Matchers.comparesEqualTo(priceAfter));
        assertThat(BigDecimal.valueOf(0.9), Matchers.comparesEqualTo(priceEndDate));
    }

}