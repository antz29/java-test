package com.test.harrys.control;

import com.test.harrys.basket.ShoppingBasket;
import com.test.harrys.model.Product;
import com.test.harrys.model.ShoppingDiscount;
import com.test.harrys.model.ShoppingListItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.test.harrys.ShoppingTill.*;

public class InventoryControl {

    public static void initialiseInventory(){
        initInventory();
        setDiscounts(Set.of(createApplesDiscount(),createSoupDiscount()));
    }

    public static ShoppingDiscount createApplesDiscount(){
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

    public static ShoppingDiscount createSoupDiscount(){
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

    public static void initInventory(){
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
}
