package com.ford.henrysgroceries;

import java.io.PrintStream;
import java.util.Scanner;

import static com.ford.henrysgroceries.products.ProductHelper.*;

public class BasketRunner {
    private final Basket basket;

    private final Scanner scanner;

    private final PrintStream printStream;

    public BasketRunner(Basket basket, Scanner scanner, PrintStream printStream) {
        this.basket = basket;
        this.scanner = scanner;
        this.printStream = printStream;
    }

    public static void main(String[] args) {
        BasketRunner basketRunner = new BasketRunner(new Basket(), new Scanner(System.in), System.out);
        basketRunner.run();
    }

    void run() {
        boolean flag = true;

        do {
            printStream.print("Please enter the first letter of the product you wish to add to your basket: [S]oup, [B]read, [M]ilk, [A]pples or [Q]uit: ");
            String input = scanner.nextLine().toUpperCase();

            switch (input) {
                case "S":
                    printStream.print("You added: Soup\n");
                    basket.addProduct(soup());
                    break;

                case "B":
                    printStream.print("You added: Bread\n");
                    basket.addProduct(bread());
                    break;

                case "M":
                    printStream.print("You added: Milk\n");
                    basket.addProduct(milk());
                    break;

                case "A":
                    printStream.print("You added: Apples\n");
                    basket.addProduct(apples());
                    break;

                case "Q":
                    printStream.print("\n");
                    flag = false;
                    break;

                default:
                    printStream.print("Product not recognised\n");
            }

            printStream.println(basket);
        } while (flag);
    }
}
