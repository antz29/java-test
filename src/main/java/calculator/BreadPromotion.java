package calculator;

import item.Item;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public class BreadPromotion implements PriceCalculator {

    private static final BigDecimal TWO = BigDecimal.valueOf(2);
    private static final PromotionValidity promotionValidity = new PromotionValidity(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(7));

    @Override
    public BigDecimal calculatePrice(Map<Item, Integer> items, Item item, LocalDateTime calculationTime) {
        if (!items.containsKey(item)) {
            return BigDecimal.ZERO;
        }
        if (promotionValidity.checkPromotionValid(calculationTime)) {
            Integer discountedItems = getDiscountedItems(items, item);
            BigDecimal discountedPrice = item.getPrice().divide(TWO).multiply(BigDecimal.valueOf(discountedItems));
            Integer undiscountedItemCount = items.get(item) - discountedItems;
            if (undiscountedItemCount > 0) {
                return discountedPrice.add(item.getPrice().multiply(BigDecimal.valueOf(undiscountedItemCount)));
            } else {
                return discountedPrice;
            }
        }
        return item.getPrice().multiply(BigDecimal.valueOf(items.get(item)));
    }

    private Integer getDiscountedItems(Map<Item, Integer> items, Item item) {
        return Math.min(items.get(item), Optional.ofNullable(items.get(Item.SOUP)).orElse(0) / 2);
    }

}
