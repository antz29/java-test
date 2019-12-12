package com.test.harrys;

import com.test.harrys.basket.ShoppingBasket;
import com.test.harrys.model.ShoppingListItem;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.logging.Logger;

import static com.test.harrys.ShoppingTill.calculateBill;
import static com.test.harrys.ShoppingTill.getProductPrice;
import static com.test.harrys.control.InventoryControl.initialiseInventory;

public class InventoryClient extends Thread {
    private static final String ADD = "add";
    private static final String BILL = "bill";
    private static final String RESET ="reset";
    private static final Logger log = Logger.getLogger(InventoryClient.class.getName());
    private static final String COMMAND_DELIMITER = " ";
    public static final String START = "start";
    public static final String END = "end";

    ShoppingBasket basket = new ShoppingBasket();

    PrintWriter out = new PrintWriter(System.out, true);

    public InventoryClient() {
        initialiseInventory();
    }

    /**
     * starts a thread and sends the
     * client a set of instructions to use the service
     */
    public void run() {
        Scanner inputS = new Scanner(System.in);
        userPrompt();
        while (true) {
            String input = inputS.nextLine();
            if (input == null || input.equals("exit")) {
                break;
            }
            processInput(input);
        }
    }

    private void displayInventory() {
        out.println("The inventory consists of :");
        ShoppingTill.getProductOffering().stream().forEach(item -> out.println(item.getName()));
    }

    private void userPrompt() {
        out.println("Enter the word 'exit' to quit");
        out.println("Enter 'start' to start shopping");
        out.println("Enter 'add<SPACE><PRODUCT_CODE>' to add a product to the basket eg 'add soup' ");
        out.println("Enter 'bill' to display bill to customer");
        out.println("Enter 'end' to settle bill, ends session");
        out.println("Enter 'reset' to reset all system data to defaults");
        displayInventory();
    }

    /**
     * processes the input by way of interpreting the command sent across
     * the command string is delimiterred with a space and parsed to determine the command
     * and the relative parameters, the 1st string in the list is the actual command
     */
    private void processInput(String input) {
        String[] command = input.split(COMMAND_DELIMITER);
        switch (command[0]) {
            case START:
                startShopping();
                break;
            case END:
                endShopping();
                break;
            case BILL:
                displayBill();
                break;
            case ADD:
                addItemToBasket(command[1]);
                break;
            case RESET:
                out.println("Service reset to default state");
                basket = new ShoppingBasket();
                break;
            default:
                userPrompt();
                break;
        }
    }

    private void endShopping() {
        displayBill();
        basket = new ShoppingBasket();
    }

    private void startShopping() {

    }


    /**
     * adds item to basket
     * @param productCode
     */
    private void addItemToBasket(String productCode) {
        try{
            basket.addItem(new ShoppingListItem(productCode));
            out.println(String.format("added %s to basket, unit price : %s",productCode, getProductPrice(productCode)));
        }catch (IllegalArgumentException iae){
            log.warning(iae.getMessage());
            displayInventory();
        }
    }

    private void displayBill(){
        BigDecimal bill = calculateBill(basket);
        out.println(String.format("your bill is : %s", bill));
    }

    public static void main(String[] args) {
        log.info("The client has been started ");
        new InventoryClient().start();
    }
}
