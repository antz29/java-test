package calculator;

import item.Item;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;

public class ApplePromotion implements PriceCalculator {

    private static final PromotionValidity promotionValidity = new PromotionValidity(LocalDateTime.now().plusDays(3), LocalDateTime.now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth()));

    @Override
    public BigDecimal calculatePrice(Map<Item, Integer> items, Item item, LocalDateTime calculationTime) {
        if (!items.containsKey(item)) {
            return BigDecimal.ZERO;
        }
        BigDecimal factor = getFactorBasedOnTime(calculationTime);
        return item.getPrice().multiply(BigDecimal.valueOf(items.get(item))).multiply(factor);
    }

    private BigDecimal getFactorBasedOnTime(LocalDateTime calculationTime) {
        if (promotionValidity.checkPromotionValid(calculationTime)) {
            return BigDecimal.valueOf(0.9);
        }
        return BigDecimal.ONE;
    }

}
