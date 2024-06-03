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

/**
 * The CartController class handles HTTP requests related to the shopping cart.
 */
@RestController
@RequestMapping("/api")
public class CartController {
    private final ObjectMapper objectMapper;

    @Autowired
    Cart cart;

    /**
     * Constructs a CartController with the specified ObjectMapper.
     *
     * @param objectMapper The ObjectMapper to use for JSON conversion.
     */
    public CartController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Adds an article to the cart.
     *
     * @param item The article to add to the cart.
     * @return A ResponseEntity containing the JSON representation of the added article.
     */
    @PostMapping("/addToCart")
    @ResponseBody
    public ResponseEntity<String> addToCart(@RequestBody Article item) {
        if (item.getQuantity() > 1) {
            cart.addItemToCart(item, cart, item.getQuantity());
        } else {
            cart.addItemToCart(item, cart);
        }
        return generateJsonResponse(item);
    }

    /**
     * Changes the quantity of an article in the cart.
     *
     * @param item      The article whose quantity is to be changed.
     * @param increment A boolean indicating whether to increment or decrement the quantity.
     * @return A ResponseEntity containing the JSON representation of the updated article.
     */
    @PostMapping("/changeArticleQuantity")
    @ResponseBody
    public ResponseEntity<String> changeArticleQuantity(@RequestBody Article item, @RequestParam boolean increment) {
        Article articleInCart = cart.getCart_item(item.getArticleNo());
        if (articleInCart != null) {
            if (increment) {
                articleInCart.incrementQuantity();
            } else {
                articleInCart.decrementQuantity();
            }
        }
        return generateJsonResponse(item);
    }

    /**
     * Dynamically changes the quantity of an article in the cart.
     *
     * @param item  The article whose quantity is to be changed.
     * @param value The new quantity value.
     * @return A ResponseEntity containing the JSON representation of the updated article.
     */
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

    /**
     * Removes an article from the cart.
     *
     * @param item The article to remove from the cart.
     * @return A ResponseEntity containing the JSON representation of the removed article.
     */
    @PostMapping("/removeArticleFromCart")
    @ResponseBody
    public ResponseEntity<String> removeArticleFromCart(@RequestBody Article item) {
        Article articleInCart = cart.getCart_item(item.getArticleNo());
        if (articleInCart != null) {
            cart.removeItemFromCart(item, cart);
        }
        return generateJsonResponse(item);
    }

    /**
     * Retrieves a plushie by its ID.
     *
     * @param id The ID of the plushie to retrieve.
     * @return The plushie with the specified ID.
     */
    @GetMapping("/article")
    public Plushies getArticlebyId(@RequestParam int id) {
        return Shop.getPlushiebyID(id);
    }

    /**
     * Retrieves all plushies.
     *
     * @return A list of all plushies.
     */
    @GetMapping("/articles")
    public List<Plushies> getArticles() {
        return Shop.getArticles();
    }

    /**
     * Retrieves the items in the cart.
     *
     * @return A list of articles in the cart.
     */
    @GetMapping("/cart")
    public List<Article> getCart() {
        return cart.getCart_items();
    }

    /**
     * Retrieves an article in the cart by its ID.
     *
     * @param id The ID of the article to retrieve.
     * @return A ResponseEntity containing the article with the specified ID, or a 404 status if not found.
     */
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

    /**
     * Generates a JSON response for the given article.
     *
     * @param item The article to convert to JSON.
     * @return A ResponseEntity containing the JSON representation of the article.
     */
    private ResponseEntity<String> generateJsonResponse(Article item) {
        try {
            String itemJson = objectMapper.writeValueAsString(item);
            return ResponseEntity.ok().body(itemJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to convert item to JSON");
        }
    }
}
