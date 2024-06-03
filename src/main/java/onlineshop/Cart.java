package onlineshop;

import onlineshop.merchandise.Article;
import onlineshop.merchandise.Plushies;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Cart class represents a shopping cart for an online shop.
 */
@Component
@SessionScope
public class Cart {
    /** Total cost of the items in the cart */
    protected double grandTotal;

    /** Subtotal of the items in the cart */
    protected double subTotal;

    /** List of articles in the cart */
    List<Article> items = new ArrayList<>();

    public Cart() {
    }

    /**
     * Returns the list of articles in the cart.
     *
     * @return The list of articles in the cart.
     */
    public List<Article> getCart_items() {
        return items;
    }

    /**
     * Sets the list of articles in the cart.
     *
     * @param items The list of articles to set in the cart.
     */
    public void setCart_items(List<Article> items) {
        this.items = items;
    }


    /**
     * Returns the total cost of the items in the cart.
     *
     * @return The total cost of the items in the cart.
     */
    public double getGrandTotal() {
        double total = 0;
        for (Article item : items) {
            total += item.getPrice();
        }
        grandTotal = total;

        grandTotal = Math.round(grandTotal * 100.0) / 100.0;
        return grandTotal;
    }

    /**
     * Returns the subtotal of the items in the cart.
     *
     * @return The subtotal of the items in the cart.
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
     * Retrieves an item from the cart by its article number.
     *
     * @param articleNo The article number of the item to retrieve.
     * @return The item with the specified article number, or null if not found.
     */
    public Article getCart_item(int articleNo) {
        for (Article item : items) {
            if (item.getArticleNo() == articleNo) {
                return item;
            }
        }
        return null;
    }


    public void addCart(List<Article> CartFromModel) {
        items.addAll(CartFromModel);
    }


    /**
     * Adds an Item to the Cart
     *
     * @param item The article number of the item to retrieve.
     * @param cart The cart retrieved from the ShoppingController
     */
    public void addItemToCart(Article item, Cart cart) {
        if (cart.getCart_items().contains(item)) {
            for (Article cartItem : items) {
                if (cartItem.equals(item)) {
                    cartItem.incrementQuantity();
                    break;
                }
            }
        } else {
            items.add(item);
        }
    }

    /**
     * Adds an Item to the Cart in regard to the quantity
     *
     * @param item     The article number of the item to retrieve.
     * @param cart     The cart retrieved from the ShoppingController.
     * @param quantity The quantity selected by the user.
     */
    public void addItemToCart(Article item, Cart cart, Integer quantity) {
        if (cart.getCart_items().contains(item)) {
            for (Article cartItem : items) {
                if (cartItem.equals(item)) {
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                    break;
                }
            }
        } else {
            items.add(item);
        }

    }

    /**
     * Removes an article from the cart.
     *
     * @param item The article to remove from the cart.
     * @param cart The cart from which the article is to be removed.
     */
    public void removeItemFromCart(Article item, Cart cart) {
        if (cart.getCart_items().contains(item)) {
            cart.getCart_items().remove(item);
        }
    }

    /**
     * Returns the size of the cart in terms of the total quantity of all items.
     *
     * @param cart The cart for which to determine the size.
     * @return The total quantity of all items in the cart.
     */
    public int getCartSizeByCart(Cart cart) {
        int size = 0;
        for (Article item : cart.getCart_items()) {
            size += item.getQuantity();
        }
        return size;
    }


    /**
     * Returns the number of unique items in the cart.
     *
     * @return The number of unique items in the cart.
     */
    public int getCartSize() {
        return items.size();
    }

    /**
     * Changes the stock of the articles
     *
     * @param Items contains the items in the cart of the customer.
     */
    public void changeStockage(List<Article> Items) {
        for (Article item : Items) {
            for (Plushies plushies : Shop.getArticles()) {
                if (plushies.getArticleNo() == item.getArticleNo()) {
                    int newStock = plushies.getInStock() - item.getQuantity();
                    if (newStock >= 0) {
                        plushies.setInStock(newStock);
                    } else {
                        // Handle the error condition here
                        // For example, throw an exception or log an error message
                        System.err.println("Error: Insufficient stock available");
                    }
                }
            }
        }
    }

    /**
     * Returns a string representation of the Cart object.
     *
     * @return A string representation of the Cart object.
     */
    @Override
    public String toString() {
        return "Cart{" +
                "grandTotal=" + grandTotal +
                ", subTotal=" + subTotal +
                ", items=" + items +
                '}';
    }
}
