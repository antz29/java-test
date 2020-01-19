package com.grocery.henry;

import java.util.HashMap;
import java.util.Map;

public class Basket {
    private Map<String, Integer> products = new HashMap();
    private double soup = 0.65;
    private double bread = 0.80;
    private double milk = 1.30;
    private double apple = 0.10;

    public void add(String productName, int count) {
        products.put(productName, count);
    }


    public double calculate() {
        double totalPrice = 0d;
        for (String productName : products.keySet()) {
            switch (productName) {
                case "soup":
                    System.out.println("soup");
                    totalPrice = totalPrice + (soup * products.get(productName));
                    break;
                case "bread":
                    System.out.println("bread");
                    totalPrice = totalPrice + (bread * products.get(productName));
                    break;
                case "milk":
                    System.out.println("milk");
                    totalPrice = totalPrice + (milk * products.get(productName));
                    break;
                case "apple":
                    System.out.println("apple");
                    totalPrice = totalPrice + (apple * products.get(productName));
                    break;
                default:
                    System.out.println("no match");
            }
        }
        return totalPrice;
    }
}
