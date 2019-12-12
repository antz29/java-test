package com.test.harrys;

import com.test.harrys.basket.ShoppingBasket;
import com.test.harrys.model.Product;
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
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class SuperMarketTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void init(){
        Set<Product> products = new HashSet<Product>();
        Product product = new Product();
        product.setName("apple");
        product.setPrice(new BigDecimal("0.10"));
        product.setProductCode("apple");
        products.add(product);

        product = new Product();
        product.setName("soup");
        product.setPrice(new BigDecimal("0.65"));
        product.setProductCode("soup");
        products.add(product);

        product = new Product();
        product.setName("bread");
        product.setPrice(new BigDecimal("0.80"));
        product.setProductCode("bread");
        products.add(product);

        product = new Product();
        product.setName("milk");
        product.setPrice(new BigDecimal("1.30"));
        product.setProductCode("milk");
        products.add(product);
        setProductOffering(products);
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
    public void calculateTotalsAfterDiscounts(){
        ShoppingTill.setDiscounts(Set.of(createDiscountWithNoDateCheck()));

        double expectedTotal =
            0.10 + 0.10 + 0.65 + 0.65 + 0.80 + 0.80 + 1.30 - 0.40;
        BigDecimal total = new BigDecimal(expectedTotal).
                setScale( 2, RoundingMode.HALF_UP);;
        String[] shoppingList =
                {"apple", "apple", "soup","soup","bread","bread","milk"};
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
        setDiscounts(Set.of(createApplesDiscount()));
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
        setDiscounts(Set.of(createApplesDiscount()));
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
        setDiscounts(Set.of(createSoupDiscount()));
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
        setDiscounts(Set.of(createApplesDiscount(),createSoupDiscount()));
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

    private ShoppingDiscount createDiscountWithNoDateCheck(){
        ShoppingDiscount discount = new ShoppingDiscount() {
            @Override
            public BigDecimal calculateDiscountAmount(ShoppingBasket basket) {
                BigDecimal discount = BigDecimal.ZERO;
                Collection<ShoppingListItem> items = basket.getShoppingListItems();
                if(items.contains(new ShoppingListItem("bread"))){
                    Optional<ShoppingListItem> op =
                            items.stream().filter(item -> item.getProductCode().equals(getProductCode()) &&
                                    item.getQuantity() >= getTriggerQuantity()).findAny();
                    if(op.isPresent()){
                        discount = BigDecimal.valueOf(0.40);
                    }
                }
                return discount;
            }
        };
        discount.setDiscountAmount(0.5);
        discount.setProductCode("soup");
        discount.setTriggerQuantity(2.0);
        discount.setDiscountDescription("Buy 2 tins of soup and get a loaf of bread half price");
        return discount;
    }

    private ShoppingDiscount createApplesDiscount(){
        ShoppingDiscount discount = new ShoppingDiscount() {
            @Override
            public BigDecimal calculateDiscountAmount(ShoppingBasket basket) {
                BigDecimal discount = BigDecimal.ZERO;

                if(isActive(basket.getShoppingDate())){
                    double discountAmount = basket.getShoppingListItems().stream()
                            .filter(item -> item.getProductCode().equals(getProductCode()))
                            .mapToDouble(item ->  getDiscountAmount() * item.getQuantity() *
                                    getProductPrice(getProductCode()).doubleValue())
                            .findFirst().orElse(0);
                    discount = new BigDecimal( discountAmount).setScale( 2, RoundingMode.HALF_UP);
                }
                return discount;
            }
        };
        LocalDate today = LocalDate.now();
        discount.setStartDate(today.plus(3, ChronoUnit.DAYS));
        discount.setEndDate(today.plus(2, ChronoUnit.MONTHS));
        discount.setDiscountAmount(0.10);
        discount.setProductCode("apple");
        discount.setTriggerQuantity(1);
        discount.setDiscountDescription("Apples have a 10% discount");
        return discount;
    }

    private ShoppingDiscount createSoupDiscount(){
        ShoppingDiscount discount = new ShoppingDiscount() {
            @Override
            public BigDecimal calculateDiscountAmount(ShoppingBasket basket) {
                BigDecimal discount = BigDecimal.ZERO;
                Collection<ShoppingListItem> items = basket.getShoppingListItems();
                if(items.contains(new ShoppingListItem("bread")) && isActive(basket.getShoppingDate())){
                    Optional<ShoppingListItem> listItem = items.stream().filter(item ->
                            item.getProductCode().equals(getProductCode()) &&
                                    item.getQuantity() >= getTriggerQuantity()).findAny();
                    if(listItem.isPresent()){
                        discount = BigDecimal.valueOf(getProductPrice("bread").doubleValue() / 2);
                    }
                }
                return discount;
            }
        };
        LocalDate today = LocalDate.now();
        discount.setStartDate(today.minus(1, ChronoUnit.DAYS));
        discount.setEndDate(today.plus(6, ChronoUnit.DAYS));
        discount.setDiscountAmount(0.5);
        discount.setProductCode("soup");
        discount.setTriggerQuantity(2.0);
        discount.setDiscountDescription("Buy 2 tins of soup and get a loaf of bread half price");
        return discount;
    }

    @After
    public void tearDown(){
        ShoppingTill.setDiscounts(Collections.EMPTY_SET);
    }
}
