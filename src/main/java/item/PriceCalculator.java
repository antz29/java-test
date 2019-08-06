package item;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public interface PriceCalculator {

    default BigDecimal calculatePrice(Map<Item, Integer> items, Item item, LocalDateTime calculationTime) {
        return BigDecimal.ZERO;
    }
}
