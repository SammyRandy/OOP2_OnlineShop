package onlineshop;

import onlineshop.enums.ShoppingCost;
import onlineshop.merchandise.Article;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The Order class represents an order placed in an online shop.
 */
@Component
public class Order {
    /** Formatted date and time of the order creation */
    protected String formatDate;

    /** Total cost of the items in the order */
    protected double Total;

    /** Grand total including shipping costs and taxes */
    protected double grandTotal;

    /** Billing details associated with the order */
    protected BillingDetails billingDetails;

    /** Unique order number */
    protected int orderNumber;

    /** Subtotal of the items in the order */
    private double subTotal;

    /** Formatter for date and time */
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /** List of articles in the order */
    protected List<Article> items = new ArrayList<>();

    /**
     * Constructs a new Order object with the given billing details.
     *
     * @param billingDetails The billing details associated with the order.
     */
    public Order(BillingDetails billingDetails) {
        this.billingDetails = billingDetails;
        this.formatDate = LocalDateTime.now().format(formatter);
    }

    /**
     * Default constructor for the Order class.
     */
    public Order() {
        this.formatDate = formatDate;
    }

    /**
     * Returns the list of articles in the order.
     *
     * @return The list of articles in the order.
     */
    public List<Article> getOrder_items() {
        System.out.println("Here are my items" + items);
        return items;
    }

    /**
     * Returns the billing details associated with the order.
     *
     * @return The billing details associated with the order.
     */
    public BillingDetails getBillingDetails() {
        return billingDetails;
    }

    /**
     * Returns the order number.
     *
     * @return The order number.
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Sets the order number.
     *
     * @param orderNumber The order number to set.
     */
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * Sets the billing details for the order.
     *
     * @param billingDetails The billing details to set.
     */
    public void setBillingDetails(BillingDetails billingDetails) {
        this.billingDetails = billingDetails;
        System.out.println(orderNumber);
        System.out.println(this.billingDetails.toString());
    }

    /**
     * Sets the list of articles in the order.
     *
     * @param items The list of articles to set in the order.
     */
    public void setOrder_items(List<Article> items) {
        this.items = items;
    }

    /**
     * Returns the total cost of the items in the order.
     *
     * @return The total cost of the items in the order.
     */
    public double getTotal() {
        double total = 0;
        for (Article item : items) {
            total += item.getPrice();
        }
        Total = total;
        Total = Math.round(Total * 100.0) / 100.0;
        return Total;
    }

    /**
     * Returns the grand total including shipping costs and taxes.
     *
     * @return The grand total of the order.
     */
    public double getGrandTotal(){
        double shippingCosts = ShoppingCost.SHIPPING.getValue();
        double taxes = ShoppingCost.TAX_RATE.getValue();

        double totalPrice = Math.round((this.getTotal() + shippingCosts + (this.getTotal() * taxes)) * 100) / 100.0;
        this.grandTotal = totalPrice;
        return grandTotal;
    }

    /**
     * Returns the subtotal of the items in the order.
     *
     * @return The subtotal of the items in the order.
     */
    public double getSubTotal() {
        double total = 0;
        for (Article item : items) {
            total += item.getPricePerUnit();
        }
        subTotal = total;

        subTotal = Math.round(subTotal * 100.0) / 100.0;
        return subTotal;
    }

    /**
     * Adds a list of articles to the order.
     *
     * @param OrderFromModel The list of articles to add to the order.
     */
    public void addOrder(List<Article> OrderFromModel) {
        items.addAll(OrderFromModel);
    }

    /**
     * Adds an article to the order. If the article is already in the order, it increments the quantity.
     *
     * @param item The article to add to the order.
     * @param Order The order to which the article is to be added.
     */
    public void addItemToOrder(Article item, Order Order) {
        if (Order.getOrder_items().contains(item)) {
            System.out.println("Item is already in the Order");
            for (Article OrderItem : items) {
                if (OrderItem.equals(item)) {
                    OrderItem.incrementQuantity();
                    break;
                }
            }
        } else {
            items.add(item);
        }
    }

    /**
     * Returns the date the order was created.
     *
     * @return The date the order was created.
     */
    public String getDate() {
        return formatDate;
    }

    /**
     * Returns the size of the order in terms of the total quantity of all items.
     *
     * @param Order The order for which to determine the size.
     * @return The total quantity of all items in the order.
     */
    public int getOrderSizeByOrder(Order Order) {
        int size = 0;
        for (Article item : Order.getOrder_items()) {
            size += item.getQuantity();
        }
        return size;
    }

    /**
     * Returns the number of unique items in the order.
     *
     * @return The number of unique items in the order.
     */
    public int getOrderSize() {
        return items.size();
    }

    /**
     * Returns the address associated with the order's billing details.
     *
     * @return The address associated with the order's billing details.
     */
    public String getAddress() {
        System.out.println(orderNumber);
        BillingDetails details = getBillingDetails();
        if (details != null) {
            System.out.println(details.getAddress());
            return details.getAddress();
        } else {
            System.out.println("BillingDetails is null");
            return null;
        }
    }

    /**
     * Returns the payment method associated with the order's billing details.
     *
     * @return The payment method associated with the order's billing details.
     */
    public String getPaymentMethod() {
        BillingDetails details = getBillingDetails();
        if (details != null) {
            System.out.println(details.getPaymentMethod());
            return details.getPaymentMethod();
        }
        return null;
    }

    /**
     * Returns a string representation of the Order object.
     *
     * @return A string representation of the Order object.
     */
    @Override
    public String toString() {
        return "Order{" +
                "formatDate='" + formatDate + '\'' +
                ", grandTotal=" + grandTotal +
                ", Total=" + Total +
                ", subTotal=" + subTotal +
                ", orderNumber=" + orderNumber +
                ", items=" + items +
                ", billingDetails=" + billingDetails +
                '}';
    }
}
