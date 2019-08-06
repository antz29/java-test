package item;

import java.math.BigDecimal;

public enum Item {
    SOUP(Unit.TIN, BigDecimal.valueOf(0.65), null),
    BREAD(Unit.LOAF, BigDecimal.valueOf(0.8), null),
    MILK(Unit.BOTTLE, BigDecimal.valueOf(1.3), null),
    APPLE(Unit.SINGLE, BigDecimal.valueOf(0.1), null);

    private final Unit unit;
    private final BigDecimal price;
    private final PriceCalculator calculator;

    Item(Unit unit, BigDecimal price, PriceCalculator calculator) {
        this.unit = unit;
        this.price = price;
        this.calculator = calculator;
    }

    public PriceCalculator getCalculator() {
        return null;
    }


}
