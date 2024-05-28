package onlineshop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import onlineshop.merchandise.Article;
import onlineshop.merchandise.Plushies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
/*    private final ObjectMapper objectMapper;*/
private int orderCount = 1;


    @Autowired
    Customer customer;

    @Autowired
    Cart cart;

/*    public OrderController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }*/

/*    private ResponseEntity<String> generateJsonResponse(Article item) {
        try {
            String itemJson = objectMapper.writeValueAsString(item);
            return ResponseEntity.ok().body(itemJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to convert item to JSON");
        }
    }*/

    @PostMapping("/addCartToOrder")
    public ResponseEntity<String> addCartToOrder(@RequestBody BillingDetails billingDetails) {
        Order order = new Order(billingDetails);
        order.setOrder_items(cart.getCart_items());
        order.setOrderNumber(orderCount);
        customer.addToOrder(order);
        cart.setCart_items(new ArrayList<>());
        String orderCountString = String.valueOf(orderCount++);
        return ResponseEntity.ok(orderCountString);
    }




/*    @GetMapping("/order")
    public Order getOrder() {
        return order;
    }

    @GetMapping("/articleInOrder")
    public ResponseEntity<Article> getArticleInOrder(@RequestParam int id) {

        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Article articleInOrder = order.getOrder_item(id);

        if (articleInOrder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(articleInOrder);
    }*/


}



