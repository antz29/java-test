package item;

import calculator.ApplePromotion;
import calculator.BreadPromotion;
import calculator.PriceCalculator;

import java.math.BigDecimal;

public enum Item {
    SOUP(BigDecimal.valueOf(0.65), new PriceCalculator() {}),
    BREAD(BigDecimal.valueOf(0.8), new BreadPromotion()),
    MILK(BigDecimal.valueOf(1.3), new PriceCalculator() {}),
    APPLE(BigDecimal.valueOf(0.1), new ApplePromotion());

    private final BigDecimal price;
    private final PriceCalculator calculator;

    Item(BigDecimal price, PriceCalculator calculator) {
        this.price = price;
        this.calculator = calculator;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public PriceCalculator getCalculator() {
        return calculator;
    }


}
