package unit;

import cart.ShoppingCart;
import item.Item;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class GroceryTest {

    @Test
    //3 tins of soup and 2 loaves of bread, bought today,
    public void applyDiscountToOneBreadWhenThreeTinsOfSoupBought() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.add(Item.SOUP, 3);
        shoppingCart.add(Item.BREAD, 2);

        BigDecimal totalPrice = shoppingCart.calculate(LocalDateTime.now());
        assertEquals(BigDecimal.valueOf(3.15), totalPrice);
    }

    @Test
    // 6 apples and a bottle of milk, bought today
    public void dontApplyDiscountWhenDiscountDateInvalid() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.add(Item.APPLE, 6);
        shoppingCart.add(Item.MILK, 1);

        BigDecimal totalPrice = shoppingCart.calculate(LocalDateTime.now());
        assertEquals(BigDecimal.valueOf(1.9), totalPrice);
    }

    @Test
    //6 apples and a bottle of milk, bought in 5 days time,
    public void applyDiscountToApplesWhenDiscountDateValid() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.add(Item.APPLE, 6);
        shoppingCart.add(Item.MILK, 1);

        BigDecimal totalPrice = shoppingCart.calculate(LocalDateTime.now().plusDays(5));
        assertEquals(BigDecimal.valueOf(1.84), totalPrice);
    }


    @Test
    //3 apples, 2 tins of soup and a loaf of bread, bought in 5 days time
    public void applyAllDiscountsWhenConditionAndDateValid() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.add(Item.APPLE, 3);
        shoppingCart.add(Item.SOUP, 2);
        shoppingCart.add(Item.BREAD, 1);

        BigDecimal totalPrice = shoppingCart.calculate(LocalDateTime.now().plusDays(5));
        assertEquals(BigDecimal.valueOf(1.97), totalPrice);
    }

    @Test
    public void shoppingCartShouldAddItems() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.add(Item.APPLE, 3);
        shoppingCart.add(Item.SOUP, 2);

        assertEquals(Integer.valueOf(3), shoppingCart.getItems().get(Item.APPLE));
        assertEquals(Integer.valueOf(2), shoppingCart.getItems().get(Item.SOUP));
        assertEquals(2, shoppingCart.getItems().keySet().size());
    }

    @Test
    public void shoppingCartShouldIncreaseTheTotalNumberOfItems() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.add(Item.APPLE, 3);
        shoppingCart.add(Item.APPLE, 2);

        assertEquals(Integer.valueOf(5), shoppingCart.getItems().get(Item.APPLE));
    }

    @Test
    public void calculateZeroIFShoppingCartEmpty() {
        ShoppingCart shoppingCart = new ShoppingCart();
        assertEquals(BigDecimal.ZERO, shoppingCart.calculate(LocalDateTime.now()));
    }
}