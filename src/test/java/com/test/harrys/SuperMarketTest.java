package com.test.harrys;

import com.test.harrys.basket.ShoppingBasket;
import com.test.harrys.model.Product;
import com.test.harrys.model.ShoppingDiscount;
import com.test.harrys.model.ShoppingListItem;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

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
        addDiscounts();

    }


    @Test
    public void calculateTotalTest(){ Double expectedTotal =
            0.10 + 0.10 + 0.65 + 0.80 + 0.80 + 1.30;
        BigDecimal total = new BigDecimal(expectedTotal).
                setScale( 2, RoundingMode.HALF_UP);;
        String[] shoppingList =
                {"apple", "apple", "soup","bread","bread","milk"};
        BigDecimal billTotal = ShoppingTill.calculateBill(shoppingList);
        assertEquals(total, billTotal);
    }

    @Test
    public void calculateTotalsAfterDiscounts(){ Double expectedTotal =
            0.10 + 0.10 + 0.65 + 0.65 + 0.80 + 0.80 + 1.30 - 0.40;
        BigDecimal total = new BigDecimal(expectedTotal).
                setScale( 2, RoundingMode.HALF_UP);;
        String[] shoppingList =
                {"apple", "apple", "soup","soup","bread","bread","milk"};
        BigDecimal billTotal = ShoppingTill.calculateBill(shoppingList);
        assertEquals(total, billTotal);
    }

    public static void addDiscounts(){// will do dates later
        Set<ShoppingDiscount> discounts = new HashSet<>();


        ShoppingDiscount discount = new ShoppingDiscount() {
            @Override
            public BigDecimal calculateDiscountAmount(ShoppingBasket basket) {
                BigDecimal discount = BigDecimal.ZERO;
                Collection<ShoppingListItem> items = basket.getShoppingListItems();
                if(items.contains(new ShoppingListItem("bread")) ){
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
