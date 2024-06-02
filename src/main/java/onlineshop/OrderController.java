package onlineshop;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/api")
public class OrderController {

private int orderCount = 1;


    @Autowired
    Customer customer;

    @Autowired
    Cart cart;

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

}



