package onlineshop;

import onlineshop.enums.ShoppingCost;
import onlineshop.merchandise.Article;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class Order {
    private String formatDate;
    private double Total;
    private double grandTotal;
    BillingDetails billingDetails;
    int orderNumber;
    private double subTotal;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    List<Article> items = new ArrayList<>();

    public Order(BillingDetails billingDetails) {
        this.billingDetails = billingDetails;
        this.formatDate = LocalDateTime.now().format(formatter);
    }

    public Order() {
        this.formatDate = formatDate;
    }

    public List<Article> getOrder_items() {
        System.out.println("Here are my items" + items);
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

    public double getTotal() {
        double total = 0;
        for (Article item : items) {
            total += item.getPrice();
        }
        Total = total;
        Total = Math.round(Total * 100.0) / 100.0;
        return Total;
    }

    public double getGrandTotal(){
        double shippingCosts = ShoppingCost.SHIPPING.getValue();
        double taxes = ShoppingCost.TAX_RATE.getValue();

        double totalPrice = Math.round((this.getTotal() + shippingCosts + (this.getTotal() * taxes)) * 100) / 100.0;
        this.grandTotal = totalPrice;
        return grandTotal;

    }

    public double getSubTotal() {
        double total = 0;
        for (Article item : items) {
            total += item.getPricePerUnit();
        }
        subTotal = total;

        subTotal = Math.round(subTotal * 100.0) / 100.0;
        return subTotal;
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

    public String getDate() {
        return formatDate;
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
        this.Total = cart.getGrandTotal();
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
    public String getPaymentMethod() {
        BillingDetails details = getBillingDetails();
        if (details != null) {
            System.out.println(details.getPaymentMethod());
            return details.getPaymentMethod();
        }
        return null;
    }
}
