package com.test.harrys;

import com.test.harrys.basket.ShoppingBasket;
import com.test.harrys.model.Product;
import com.test.harrys.model.ShoppingDiscount;
import com.test.harrys.model.ShoppingListItem;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class SuperMarketTest {

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

        ShoppingTill.setProductOffering(products);
        //addDiscounts();

    }


    @Test
    public void calculateTotalTest(){
        Double expectedTotal =
            0.10 + 0.10 + 0.65 + 0.80 + 0.80 + 1.30;
        BigDecimal total = new BigDecimal(expectedTotal).
                setScale( 2, RoundingMode.HALF_UP);;
        String[] shoppingList =
                {"apple", "apple", "soup","bread","bread","milk"};
        BigDecimal billTotal = ShoppingTill.calculateBill(shoppingList);
        assertEquals(total, billTotal);
    }

    @Test
    public void calculateTotalsAfterDiscounts(){
        ShoppingTill.setDiscounts(Set.of(createDiscountWithNoDateCheck()));

        Double expectedTotal =
            0.10 + 0.10 + 0.65 + 0.65 + 0.80 + 0.80 + 1.30 - 0.40;
        BigDecimal total = new BigDecimal(expectedTotal).
                setScale( 2, RoundingMode.HALF_UP);;
        String[] shoppingList =
                {"apple", "apple", "soup","soup","bread","bread","milk"};
        BigDecimal billTotal = ShoppingTill.calculateBill(shoppingList);
        assertEquals(total, billTotal);
    }

    @Test
    public void determineIfDiscountIsActive(){
        ShoppingDiscount discount = createDiscountWithDateCheck();
        assertTrue(discount.isActive(LocalDate.now()));
        assertFalse(discount.isActive(LocalDate.now().plus(8,ChronoUnit.DAYS)));
    }

    @Test
    public void applesAndABottleOfMilk(){
        ShoppingTill.setDiscounts(Set.of(createApplesDiscount()));
        String[] shoppingList =
                {"apple", "apple", "apple","apple","apple","apple","milk"};
        Double expectedTotal = 1.90;
        BigDecimal total = new BigDecimal(expectedTotal).
                setScale( 2, RoundingMode.HALF_UP);
        BigDecimal billTotal = ShoppingTill.calculateBill(shoppingList);
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
                                    item.getQuantity() >= getTriggerQuantity()).findAny();// check multiles of trigger
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
                    discount = basket.getShoppingListItems().stream()
                            .filter(item -> item.getProductCode().equals(getProductCode()))
                            .map(item -> new BigDecimal( getDiscountAmount() * item.getQuantity()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
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

    private ShoppingDiscount createDiscountWithDateCheck(){
        ShoppingDiscount discount = new ShoppingDiscount() {
            @Override
            public BigDecimal calculateDiscountAmount(ShoppingBasket basket) {
                BigDecimal discount = BigDecimal.ZERO;
                Collection<ShoppingListItem> items = basket.getShoppingListItems();
                if(items.contains(new ShoppingListItem("bread")) && isActive(basket.getShoppingDate())){
                    Optional<ShoppingListItem> op =
                            items.stream().filter(item -> item.getProductCode().equals(getProductCode()) &&
                                    item.getQuantity() >= getTriggerQuantity()).findAny();// check multiles of trigger
                    if(op.isPresent()){
                        discount = BigDecimal.valueOf(0.40);
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

    public static void addDiscounts(){// will do dates later
        Set<ShoppingDiscount> discounts = new HashSet<>();


        ShoppingDiscount discount = new ShoppingDiscount() {
            @Override
            public BigDecimal calculateDiscountAmount(ShoppingBasket basket) {
                BigDecimal discount = BigDecimal.ZERO;
                Collection<ShoppingListItem> items = basket.getShoppingListItems();
                if(items.contains(new ShoppingListItem("bread")) && isActive(basket.getShoppingDate())){
                    Optional<ShoppingListItem> op =
                            items.stream().filter(item -> item.getProductCode().equals(getProductCode()) &&
                                    item.getQuantity() >= getTriggerQuantity()).findAny();// check multiles of trigger
                    if(op.isPresent()){
                        discount = BigDecimal.valueOf(0.40);
                    }
                }
                return discount;
            }
        };
        discount.setStartDate(LocalDate.now());
        discount.setEndDate(LocalDate.now());
        discount.setDiscountAmount(0.5);
        discount.setProductCode("soup");
        discount.setTriggerQuantity(2.0);
        discount.setDiscountDescription("Buy 2 tins of soup and get a loaf of bread half price");
        discounts.add(discount);

        discount = new ShoppingDiscount() {
            @Override
            public BigDecimal calculateDiscountAmount(ShoppingBasket basket) {
                return null;
            }
        };

        discount.setDiscountAmount(0.333);
        discount.setProductCode("Lime");
        discount.setTriggerQuantity(3.0);
        discount.setDiscountDescription("Apples have a 10% discount");
        discounts.add(discount);

        ShoppingTill.setDiscounts(discounts);
    }


}
