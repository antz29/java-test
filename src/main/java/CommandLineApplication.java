import cart.ShoppingCart;
import item.Item;

import java.time.LocalDateTime;
import java.util.Scanner;

public class CommandLineApplication {

    public static void main(String[] args) {
        ShoppingCart shoppingCart = new ShoppingCart();

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the number of apples: ");
        shoppingCart.add(Item.APPLE, scan.nextInt());

        System.out.println("Enter the number of milk: ");
        shoppingCart.add(Item.MILK, scan.nextInt());

        System.out.println("Enter the number of bread: ");
        shoppingCart.add(Item.BREAD, scan.nextInt());

        System.out.println("Enter the number of soup: ");
        shoppingCart.add(Item.SOUP, scan.nextInt());

        System.out.println("----Your shopping cart(after discounts applied and calculated for today)----");
        shoppingCart.getItems().forEach((item, number) -> showCartInfo(item, shoppingCart));
        System.out.printf("In total %f", shoppingCart.calculate(LocalDateTime.now()));
    }

    private static void showCartInfo(Item item, ShoppingCart shoppingCart) {
        System.out.printf("%d\t%s\t=\t%f\n", shoppingCart.getItems().get(item), item.name(), item.getCalculator().calculatePrice(shoppingCart.getItems(), item, LocalDateTime.now()));
    }
}
