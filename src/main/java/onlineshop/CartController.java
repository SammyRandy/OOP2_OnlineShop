package onlineshop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import onlineshop.merchandise.Article;
import onlineshop.merchandise.Plushies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {
    private final ObjectMapper objectMapper;

    @Autowired
    Cart cart;

    public CartController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private ResponseEntity<String> generateJsonResponse(Article item) {
        try {
            String itemJson = objectMapper.writeValueAsString(item);
            return ResponseEntity.ok().body(itemJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to convert item to JSON");
        }
    }

    @PostMapping("/addToCart")
    @ResponseBody
    public ResponseEntity<String> addToCart(@RequestBody Article item) {
        System.out.println(item.getQuantity());
        if (item.getQuantity() > 1) {
            cart.addItemToCart(item, cart, item.getQuantity());
        }
        else{cart.addItemToCart(item, cart);}

        /*System.out.println(cart.getCart_items().toString());*/
        return generateJsonResponse(item);
    }

    @PostMapping("/changeArticleQuantity")
    @ResponseBody
    public ResponseEntity<String> changeArticleQuantity(@RequestBody Article item, @RequestParam boolean increment) {

        /*System.out.println(item);*/
        Article articleInCart = cart.getCart_item(item.getArticleNo());
        if (articleInCart != null) {
            if (increment) {
                articleInCart.incrementQuantity();
            } else {
                articleInCart.decrementQuantity();
            }
        }
        /*System.out.println(articleInCart.getQuantity());*/


        return generateJsonResponse(item);
    }

    @PostMapping("/changeArticleQuantityDynamic")
    @ResponseBody
    public ResponseEntity<String> changeArticleQuantityDynamic(@RequestBody Article item, @RequestParam int value) {

        Article articleInCart = cart.getCart_item(item.getArticleNo());
        if (articleInCart != null) {
            articleInCart.setQuantity(value);
            System.out.println(articleInCart.getQuantity());
        }

        return generateJsonResponse(item);
    }

    @PostMapping("/removeArticleFromCart")
    @ResponseBody
    public ResponseEntity<String> removeArticleFromCart(@RequestBody Article item) {

        Article articleInCart = cart.getCart_item(item.getArticleNo());
        if (articleInCart != null) {
            cart.removeItemFromCart(item, cart);
        }
        return generateJsonResponse(item);
    }

    @GetMapping("/article")
    public Plushies getArticlebyId(@RequestParam int id) {
        return Shop.getPlushiebyID(id);
    }

    @GetMapping("/articles")
    public List<Plushies> getArticles() {
        return Shop.getArticles();
    }

    @GetMapping("/cart")
    public List<Article> getCart() {
        return cart.getCart_items();
    }

    @GetMapping("/articleInCart")
    public ResponseEntity<Article> getArticleInCart(@RequestParam int id) {

        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Article articleInCart = cart.getCart_item(id);

        if (articleInCart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(articleInCart);
    }


}



