package com.test.harrys;

import com.test.harrys.basket.ShoppingBasket;
import com.test.harrys.model.ShoppingDiscount;
import com.test.harrys.model.ShoppingListItem;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.test.harrys.ShoppingTill.*;
import static com.test.harrys.control.InventoryControl.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class SuperMarketTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void init(){
        initialiseInventory();
    }

    @Test
    public void whenItemsAreAddedToBaskeItemShouldTally(){
        ShoppingBasket basket = new ShoppingBasket( );
        ShoppingListItem listItem = new ShoppingListItem("apple");
        basket.addItem(listItem);
        basket.addItem(listItem);
        Collection<ShoppingListItem> items = basket.getShoppingListItems();
        assertTrue(items.contains(listItem));
        Optional<ShoppingListItem> optionalItem =
                items.stream().filter(lineItem -> lineItem.getProductCode().equals("apple")).findFirst();
        assertEquals(2,optionalItem.get().getQuantity(),0.00001);
    }

    @Test
    public void whenShoppingItemIsAddedToBasketItShouldBeAvaialble(){
        ShoppingBasket basket = new ShoppingBasket( );
        ShoppingListItem listItem = new ShoppingListItem("apple");
        basket.addItem(listItem);
        Collection<ShoppingListItem> items = basket.getShoppingListItems();
        assertTrue(items.contains(listItem));
    }


    @Test()
    public void attemptToAddInvalidItemToBasketShouldThrowException(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Cannot find product with product code : ");
        ShoppingBasket basket = new ShoppingBasket( );
        ShoppingListItem listItem = new ShoppingListItem("bananas");
        basket.addItem(listItem);
    }

    @Test
    public void calculateTotalTest(){
        double expectedTotal =
            0.10 + 0.10 + 0.65 + 0.80 + 0.80 + 1.30;
        BigDecimal total = new BigDecimal(expectedTotal).
                setScale( 2, RoundingMode.HALF_UP);;
        String[] shoppingList =
                {"apple", "apple", "soup","bread","bread","milk"};
        BigDecimal billTotal = calculateBill(shoppingList);
        assertEquals(total, billTotal);
    }

    @Test
    public void determineIfDiscountIsActive(){
        ShoppingDiscount discount = createSoupDiscount();
        assertTrue(discount.isActive(LocalDate.now()));
        assertFalse(discount.isActive(LocalDate.now().plus(8,ChronoUnit.DAYS)));
    }

    @Test
    public void sixApplesAndABottleOfMilkBoughtToday(){
        String[] shoppingList =
                {"apple", "apple", "apple","apple","apple","apple","milk"};
        double expectedTotal = 1.90;
        BigDecimal total = new BigDecimal(expectedTotal).
                setScale( 2, RoundingMode.HALF_UP);
        BigDecimal billTotal = calculateBill(shoppingList);
        assertEquals(total, billTotal);
    }

    @Test
    public void sixApplesAndABottleOfMilkBoughtInFiveDays(){
        String[] shoppingList =
                {"apple","apple", "apple","apple","apple","apple","milk"};
        double expectedTotal = 1.84;
        BigDecimal total = new BigDecimal(expectedTotal).
                setScale( 2, RoundingMode.HALF_UP);

        ShoppingBasket basket = new ShoppingBasket();
        Arrays.stream(shoppingList).forEach(p ->  basket.addItem(new ShoppingListItem(p)));
        basket.setShoppingDate(LocalDate.now().plus(5,ChronoUnit.DAYS));

        BigDecimal billTotal = calculateBill(basket);
        assertEquals(total, billTotal);
    }

    @Test
    public void threeSoupTinsTwoLoafBoughtToday(){
        String[] shoppingList =
                {"soup","soup","soup","bread","bread"};
        double expectedTotal = 3.15;
        BigDecimal total = new BigDecimal(expectedTotal).
                setScale( 2, RoundingMode.HALF_UP);

        ShoppingBasket basket = new ShoppingBasket();
        Arrays.stream(shoppingList).forEach(p ->  basket.addItem(new ShoppingListItem(p)));
        basket.setShoppingDate(LocalDate.now());

        BigDecimal billTotal = calculateBill(basket);
        assertEquals(total, billTotal);
    }

    @Test
    public void threeApplesTwoSoupTinsOneLoafBoughtInFiveDays(){
        String[] shoppingList =
                {"apple","apple", "apple","soup","soup","bread"};
        double expectedTotal = 1.97;
        BigDecimal total = new BigDecimal(expectedTotal).
                setScale( 2, RoundingMode.HALF_UP);

        ShoppingBasket basket = new ShoppingBasket();
        Arrays.stream(shoppingList).forEach(p ->  basket.addItem(new ShoppingListItem(p)));
        basket.setShoppingDate(LocalDate.now().plus(5,ChronoUnit.DAYS));

        BigDecimal billTotal = calculateBill(basket);
        assertEquals(total, billTotal);
    }
}
