package onlineshop.enums;

public enum ShoppingCost {
    SHIPPING(0),
    TAX_RATE(0.08); // 8% tax rate

    private final double value;

    ShoppingCost(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

}
