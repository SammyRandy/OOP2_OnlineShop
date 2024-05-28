package onlineshop;

import onlineshop.merchandise.Article;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@SessionScope
public class Cart {
    private double grandTotal;
    private double subTotal;


    List<Article> items = new ArrayList<>();

    public Cart() {

    }

    public List<Article> getCart_items() {
        return items;
    }
    public void setCart_items(List<Article> items) {
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

    public double getSubTotal() {
        double total = 0;
        for (Article item : items) {
            total += item.getPricePerUnit();
        }
        subTotal = total;

        subTotal = Math.round(subTotal * 100.0) / 100.0;
        return subTotal;
    }

    public Article getCart_item(int articleNo) {
        for (Article item : items) {
            if (item.getArticleNo() == articleNo) {
                return item;
            }
        }
        return null;
    }


    public void addCart(List <Article> CartFromModel) {
        items.addAll(CartFromModel);
    }

    public void addItemToCart(Article item, Cart cart) {
        if(cart.getCart_items().contains(item)){
            System.out.println("Item is already in the cart");
            for (Article cartItem : items) {
                if (cartItem.equals(item)) {
                    cartItem.incrementQuantity();
                    break;
                }
            }
        }
        else{items.add(item);}
    }

    public void addItemToCart(Article item, Cart cart, Integer quantity) {
        if(cart.getCart_items().contains(item)) {
            for (Article cartItem : items) {
                if (cartItem.equals(item)) {
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                    break;
                }
            }
        }
        else{items.add(item);}

    }
    public void removeItemFromCart(Article item, Cart cart) {
        if(cart.getCart_items().contains(item)){
            cart.getCart_items().remove(item);
        }
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }


    public int getCartSizeByCart(Cart cart) {
        int size = 0;
        for (Article item : cart.getCart_items()) {
            size += item.getQuantity();
        }
        return size;
    }


    public int getCartSize() {


        return items.size();
    }
}
