package onlineshop;

import onlineshop.merchandise.Article;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class Order {
    private LocalDate date;
    private double grandTotal;
    BillingDetails billingDetails;
    int orderNumber;


    List<Article> items = new ArrayList<>();

    public Order(BillingDetails billingDetails) {
        this.billingDetails = billingDetails;
        this.date = LocalDate.now(); // Set current date
    }

    public Order() {
        this.date = LocalDate.now(); // Set current date
    }

    public List<Article> getOrder_items() {
        return items;
    }

    public BillingDetails getBillingDetails() {
        return billingDetails;
    }

    public int getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setBillingDetails(BillingDetails billingDetails) {
        this.billingDetails = billingDetails;
        System.out.println(orderNumber);
        System.out.println(this.billingDetails.toString());
    }

    public void setOrder_items(List<Article> items) {
        this.items = items;
    }

    public double getGrandTotal() {
        double total = 0;
        for (Article item : items) {
            total += item.getPrice();
        }
        grandTotal = total;
        grandTotal = Math.round(grandTotal * 100.0) / 100.0;
        return grandTotal;
    }

    public Article getOrder_item(int articleNo) {
        for (Article item : items) {
            if (item.getArticleNo() == articleNo) {
                return item;
            }
        }
        return null;
    }

    public void addOrder(List<Article> OrderFromModel) {
        items.addAll(OrderFromModel);
    }

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

    public LocalDate getDate() {
        return date;
    }

    public void removeItemFromOrder(Article item, Order Order) {
        if (Order.getOrder_items().contains(item)) {
            Order.getOrder_items().remove(item);
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "items=" + items +
                '}';
    }

    public int getOrderSizeByOrder(Order Order) {
        int size = 0;
        for (Article item : Order.getOrder_items()) {
            size += item.getQuantity();
        }
        return size;
    }

    public int getOrderSize() {
        return items.size();
    }

    public void transferFromCart(Cart cart) {
        this.items = new ArrayList<>(cart.getCart_items());
        this.grandTotal = cart.getGrandTotal();
    }

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
}
