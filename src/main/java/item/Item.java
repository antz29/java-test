package item;

import calculator.ApplePromotion;
import calculator.BreadPromotion;
import calculator.PriceCalculator;

import java.math.BigDecimal;

public enum Item {
    SOUP(Unit.TIN, BigDecimal.valueOf(0.65), new PriceCalculator() {}),
    BREAD(Unit.LOAF, BigDecimal.valueOf(0.8), new BreadPromotion()),
    MILK(Unit.BOTTLE, BigDecimal.valueOf(1.3), new PriceCalculator() {}),
    APPLE(Unit.SINGLE, BigDecimal.valueOf(0.1), new ApplePromotion());

    private final Unit unit;
    private final BigDecimal price;
    private final PriceCalculator calculator;

    Item(Unit unit, BigDecimal price, PriceCalculator calculator) {
        this.unit = unit;
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
