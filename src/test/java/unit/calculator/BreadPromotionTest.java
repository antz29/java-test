package unit.calculator;

import calculator.BreadPromotion;
import item.Item;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThat;

public class BreadPromotionTest {

    private final Item testItem = Item.BREAD;

    @Test
    public void noPromotionWhenhtereIsOnlyBread() {
        Map<Item, Integer> items = new HashMap<>();
        items.put(testItem, 10);
        BigDecimal price = new BreadPromotion().calculatePrice(items, testItem, LocalDateTime.now());

        assertThat(BigDecimal.valueOf(8), Matchers.comparesEqualTo(price));
    }

    @Test
    public void noPromotionWhenDateOutOfRange() {
        Map<Item, Integer> items = new HashMap<>();
        items.put(testItem, 10);
        BigDecimal price = new BreadPromotion().calculatePrice(items, testItem, LocalDateTime.now().plusMonths(5));

        assertThat(BigDecimal.valueOf(8), Matchers.comparesEqualTo(price));
    }

    @Test
    public void calculateZeroIfItemIsMissingInItemList() {
        Map<Item, Integer> items = new HashMap<>();
        items.put(Item.SOUP, 10);
        BigDecimal price = new BreadPromotion().calculatePrice(items, testItem, LocalDateTime.now());

        assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(price));
    }

    @Test
    public void calculateValidDiscountWhenSoupIsMoreThanBread() {
        Map<Item, Integer> items = new HashMap<>();
        items.put(Item.SOUP, 10);
        items.put(testItem, 1);
        BigDecimal price = new BreadPromotion().calculatePrice(items, testItem, LocalDateTime.now());

        assertThat(BigDecimal.valueOf(0.4), Matchers.comparesEqualTo(price));
    }

    @Test
    public void calculateValidDiscountWhenSoupIsLessThanBread() {
        Map<Item, Integer> items = new HashMap<>();
        items.put(Item.SOUP, 2);
        items.put(testItem, 10);
        BigDecimal price = new BreadPromotion().calculatePrice(items, testItem, LocalDateTime.now());

        assertThat(BigDecimal.valueOf(7.6), Matchers.comparesEqualTo(price));
    }

    @Test
    public void calculateValidDiscountWhenSoupIsNotEnoughForDiscount() {
        Map<Item, Integer> items = new HashMap<>();
        items.put(Item.SOUP, 1);
        items.put(testItem, 10);
        BigDecimal price = new BreadPromotion().calculatePrice(items, testItem, LocalDateTime.now());

        assertThat(BigDecimal.valueOf(8), Matchers.comparesEqualTo(price));
    }
}