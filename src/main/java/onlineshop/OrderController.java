package onlineshop;

import onlineshop.merchandise.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * The OrderController class handles HTTP requests related to orders.
 */
@RestController
@RequestMapping("/api")
public class OrderController {

    /**
     * Shows the count of the order
     */
    protected int orderCount = 1;

    @Autowired
    Customer customer;

    @Autowired
    Cart cart;

    /**
     * Adds the current cart to a new order with the specified billing details.
     *
     * @param billingDetails The billing details for the new order.
     * @return A ResponseEntity containing the order number as a string.
     */
    @PostMapping("/addCartToOrder")
    public ResponseEntity<String> addCartToOrder(@RequestBody BillingDetails billingDetails) {
        Order order = new Order(billingDetails);

        order.setOrder_items(cart.getCart_items());
        cart.changeStockage(cart.getCart_items());
        order.setOrderNumber(orderCount);

        customer.addToOrder(order);
        cart.setCart_items(new ArrayList<>());

        String orderCountString = String.valueOf(orderCount++);
        return ResponseEntity.ok(orderCountString);
    }

    /**
     * Adds a single item to a new order with the specified billing details.
     *
     * @param billingDetails The billing details for the new order.
     * @return A ResponseEntity containing the order number as a string.
     */
    @PostMapping("/addItemToOrder")
    public ResponseEntity<String> addItemToOrder(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Integer quantity,
            @RequestBody BillingDetails billingDetails) {
        
        Order order = new Order(billingDetails);
        
        Article item = (Article) Shop.getPlushiebyID(id);

        Article itemInOrder = new Article(item);

        if (quantity != null) {
            itemInOrder.setQuantity(quantity);
        } else {
            itemInOrder.setQuantity(1);
        }

        order.addItemToOrder(itemInOrder);
        order.setOrderNumber(orderCount);
        customer.addToOrder(order);
        cart.changeStockage(order.getOrder_items());
        String orderCountString = String.valueOf(orderCount++);

        return ResponseEntity.ok(orderCountString);
    }



}
