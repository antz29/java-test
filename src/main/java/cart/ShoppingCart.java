package cart;

import item.Item;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private Map<Item, Integer> cart = new HashMap<>();

    public void add(Item item, Integer amount) {
        if (cart.containsKey(item)) {
            cart.put(item, cart.get(item) + amount);
        } else {
            cart.put(item, amount);
        }
    }

    public BigDecimal calculate(LocalDateTime purchaseDate) {
        return cart.keySet().stream().map(k -> k.getCalculator().calculatePrice(cart, k, purchaseDate)).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<Item, Integer> getItems() {
        return cart;
    }
}
