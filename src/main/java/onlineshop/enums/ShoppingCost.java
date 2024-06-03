package onlineshop.enums;

/**
 * The ShoppingCost enum represents different costs associated with shopping, such as shipping and tax rates.
 */
public enum ShoppingCost {
    SHIPPING(0),
    TAX_RATE(0.08);

    private final double value;

    /**
     * Constructs a new ShoppingCost enum constant with the specified value.
     *
     * @param value The value associated with the shopping cost.
     */
    ShoppingCost(double value) {
        this.value = value;
    }

    /**
     * Returns the value associated with the shopping cost.
     *
     * @return The value associated with the shopping cost.
     */
    public double getValue() {
        return value;
    }
}
