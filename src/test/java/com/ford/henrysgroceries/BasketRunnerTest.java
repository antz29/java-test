package com.ford.henrysgroceries;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Scanner;

import static com.ford.henrysgroceries.products.ProductHelper.milk;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BasketRunnerTest {
    private static final String INSTRUCTIONS = "Please enter the first letter of the product you wish to add to your basket: [S]oup, [B]read, [M]ilk, [A]pples or [Q]uit: ";

    @Mock
    private Basket basket;

    private ByteArrayOutputStream output;

    @Before
    public void setUp() {
        output = new ByteArrayOutputStream();
    }

    @Test
    public void addNothingToBasketThenQuit() {
        when(basket.toString()).thenReturn("Basket:\nTotal: £0.00\n");
        BasketRunner basketRunner = new BasketRunner(basket, new Scanner("Q\n"), new PrintStream(output));

        basketRunner.run();

        assertThat(output.toString(), is(
                INSTRUCTIONS + "\n" +
                        "Basket:\n" +
                        "Total: £0.00\n\r\n"));
    }

    @Test
    public void addMilkToBasketThenQuit() {
        BigDecimal price = milk().getPrice();
        when(basket.toString()).thenReturn("Basket:\nMilk £" + price + "\nTotal: £" + price + "\n");
        BasketRunner basketRunner = new BasketRunner(basket, new Scanner("M\nQ\n"), new PrintStream(output));

        basketRunner.run();

        assertThat(output.toString(), is(
                INSTRUCTIONS + "You added: Milk\n" +
                        "Basket:\n" +
                        "Milk £1.30\n" +
                        "Total: £1.30\n\r" +
                        "\n" +
                        INSTRUCTIONS + "\n" +
                        "Basket:\n" +
                        "Milk £1.30\n" +
                        "Total: £1.30\n\r\n"));
    }
}
