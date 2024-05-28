package onlineshop;

import onlineshop.enums.Gender;
import onlineshop.merchandise.Article;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@SessionScope
public class Customer {
    /**
     * erzeugt für jeden Kunden eine neue Kundennummer
     */
    private static Integer customerCounter = 1;
    /**
     * wandelt den Date-String in ein {@link Date} um
     */
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.mm.yyyy");

    protected int customerNo;
    protected String firstname;
    protected String surname;
    protected Gender gender;
    protected LocalDate birthDate;
    protected Cart cart;
    protected List<Order> orders = new ArrayList<>();

    public Customer() {
        this.customerNo = customerCounter++;
    }

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
    public List<Article> getOrderByID(int id) {
        id -= 1;
        if (id >= 0 && id < orders.size()) {
            return getOrderByID_List(orders.get(id));
        } else {
            System.out.println("Invalid order ID: " + id);
            return null;
        }
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
