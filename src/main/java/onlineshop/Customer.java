package onlineshop;

import onlineshop.enums.Gender;
import onlineshop.merchandise.Article;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The Customer class represents a customer in an online shop.
 */
@Component
@SessionScope
public class Customer {
    /** Generates a new customer number for each customer. */
    protected static Integer customerCounter = 1;

    /** Converts the date string into a LocalDate. */
    protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.mm.yyyy");

    /** Unique customer number */
    protected int customerNo;

    /** First name of the customer */
    protected String firstname;

    /** Surname of the customer */
    protected String surname;

    /** Gender of the customer */
    protected Gender gender;

    /** Birthdate of the customer */
    protected LocalDate birthDate;

    /** Shopping cart of the customer */
    protected Cart cart;

    /** List of orders placed by the customer */
    protected List<Order> orders = new ArrayList<>();

    /** Total amount spent by the customer */
    protected double totalSpend;

    public Customer() {
        this.customerNo = customerCounter++;
    }

    /**
     * Constructs a new Customer object with the specified details.
     *
     * @param firstname The first name of the customer.
     * @param surname   The surname of the customer.
     * @param gender    The gender of the customer.
     * @param birthDate The birthdate of the customer in the format "dd.MM.yyyy".
     * @param cart      The shopping cart of the customer.
     */
    public Customer(String firstname, String surname, Gender gender, String birthDate, Cart cart) {
        this();
        this.firstname = firstname;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = LocalDate.parse(birthDate, formatter);
        this.cart = cart;
    }

    public void addToOrder(Order order) {
        orders.add(order);
        System.out.println(orders);
    }

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }

    public int getCustomerNo() {
        return customerNo;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Cart getCart() {
        return cart;
    }

    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return The order with the specified ID, or null if not found.
     */
    public Order getOrderByID(int id) {
        id -= 1;
        if (id >= 0 && id < orders.size()) {
            System.out.println("order in cusrtiner: " + orders.get(id));
            return orders.get(id);
        } else {
            System.out.println("Invalid order ID: " + id);
            return null;
        }
    }

    /**
     * Calculates the total amount spent by the customer.
     *
     * @return The total amount spent by the customer.
     */
    public double getTotalSpend() {
        double total = 0;
        for (Order order : orders) {
            total += order.getGrandTotal();
        }
        return total;
    }

    public void setTotalSpend(double totalSpend) {
        this.totalSpend = totalSpend;
    }

    public List<Article> getOrderByID_List(Order order){
        return order.getOrder_items();
    }

    public static Integer getCustomerCounter() {
        return customerCounter;
    }

    public static void setCustomerCounter(Integer customerCounter) {
        Customer.customerCounter = customerCounter;
    }

    public static void setFormatter(DateTimeFormatter formatter) {
        Customer.formatter = formatter;
    }

    public void setCustomerNo(int customerNo) {
        this.customerNo = customerNo;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
