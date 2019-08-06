package unit.calculator;

import calculator.PriceCalculator;
import item.Item;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThat;

public class PriceCalculatorTest {
    private final PriceCalculator priceCalculator = new PriceCalculator(){};

    @Test
    public void calculateCorrectPrice() {
        Map<Item, Integer> items = new HashMap<>();
        items.put(Item.MILK, 10);
        BigDecimal price = priceCalculator.calculatePrice(items, Item.MILK, LocalDateTime.now());

        assertThat(BigDecimal.valueOf(13), Matchers.comparesEqualTo(price));
    }

    @Test
    public void calculateDateIneffective() {
        Map<Item, Integer> items = new HashMap<>();
        items.put(Item.MILK, 10);
        BigDecimal price1 = priceCalculator.calculatePrice(items, Item.MILK, LocalDateTime.now().plusYears(10));
        BigDecimal price2 = priceCalculator.calculatePrice(items, Item.MILK, LocalDateTime.now().minusYears(10));

        assertThat(price1, Matchers.comparesEqualTo(price2));
    }
    
}