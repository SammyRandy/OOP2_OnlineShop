package onlineshop;

import jakarta.servlet.http.HttpSession;
import onlineshop.merchandise.Article;
import onlineshop.merchandise.Plushies;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class ShopController {
    private static final int PAGE_SIZE = 12;
    private static Logger log = LogManager.getLogger(Shop.class);
    public final static String ARTICLES = "articles";

    @Autowired
    Cart cart;
    
    @Autowired
    Customer customer;

    @GetMapping(value = {"/"})
    public String root(Model model) {
        return "redirect:/index.html";
    }

    @GetMapping(value = {"/index.html"})
    public String homePage(Model model,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "sort", defaultValue = "") String sort) {
        List<Plushies> allArticles = Shop.getArticles();

        // Sorting logic
        switch (sort) {
            case "nameAsc":
                Collections.sort(allArticles, Comparator.comparing(Plushies::getName));
                break;
            case "nameDesc":
                Collections.sort(allArticles, Comparator.comparing(Plushies::getName).reversed());
                break;
            case "priceAsc":
                Collections.sort(allArticles, Comparator.comparingDouble(Plushies::getPrice));
                break;
            case "priceDesc":
                Collections.sort(allArticles, Comparator.comparingDouble(Plushies::getPrice).reversed());
                break;
/*            case "sizeAsc":
                allArticles.sort(Comparator.comparingInt(plushie -> plushie.getHeight() * plushie.getLength() * plushie.getWidth()));
                break;
            case "sizeDec":
                allArticles.sort(Comparator.comparingInt(plushie -> plushie.getHeight() * plushie.getLength() * plushie.getWidth())).reversed();
                break;*/
        }

        int totalArticles = allArticles.size();
        int totalPages = (int) Math.ceil((double) totalArticles / PAGE_SIZE);

        int start = (page - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, totalArticles);

        List<Plushies> paginatedArticles = allArticles.subList(start, end);

        model.addAttribute(ARTICLES, paginatedArticles);
        model.addAttribute("cartItems", cart.getCart_items());
        model.addAttribute("cartTotalPrice", cart.getGrandTotal());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("isFirstPage", page == 1);
        model.addAttribute("isLastPage", page == totalPages);
        model.addAttribute("previousPage", page - 1);
        model.addAttribute("nextPage", page + 1);
        model.addAttribute("totalArticles", totalArticles);
        model.addAttribute("startArticle", start + 1);
        model.addAttribute("endArticle", end);
        model.addAttribute("selectedSort", sort);

        List<Page> pages = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pages.add(new Page(i, i == page));
        }
        model.addAttribute("pages", pages);

        if (cart.getCartSize() > 0) {
            model.addAttribute("cartSize", cart.getCartSizeByCart(cart));
        } else {
            model.addAttribute("cartSize", 0);
        }

        return "index";
    }

/*    @GetMapping(value = {"/cart.html"})
    public String cartPage(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        model.addAttribute("cartItems", cart.getCart_items());
        model.addAttribute("cartTotalPrice", cart.getGrandTotal());
        if (cart.getCartSize() > 0) {model.addAttribute("cartSize", cart.getCartSizeByCart(cart));}
        else {model.addAttribute("cartSize", 0);}
        return "cart";
    }*/


    @GetMapping(value = {"/{name}.html"})
    public String htmlMapping(@PathVariable String name, Model model) {
        model.addAttribute("cartItems", cart.getCart_items());
        model.addAttribute("cartTotalPrice", cart.getGrandTotal());
        model.addAttribute("cartSubtotalPrice", cart.getSubTotal());
        if (cart.getCartSize() > 0) {
            model.addAttribute("cartSize", cart.getCartSizeByCart(cart));
        } else {
            model.addAttribute("cartSize", 0);
        }
        return name;
    }

    @GetMapping(value = {"/order-list.html"})
    public String htmlMapping(Model model) {
        List<Order> orders = customer.getOrders();
        if(!customer.getOrders().isEmpty()) {
            System.out.println(customer.getOrders().get(0));
            // Debugging: Print orders and billing details
            for (Order order : orders) {
                System.out.println("Order ID: " + order.orderNumber);
                if (order.getBillingDetails() != null) {
                    System.out.println("Billing Address: " + order.getBillingDetails().getAddress());
                } else {
                    System.out.println("Billing Details are null");
                }
            };}

        model.addAttribute("orders", orders);
        model.addAttribute("cartItems", cart.getCart_items());
        model.addAttribute("cartTotalPrice", cart.getGrandTotal());
        model.addAttribute("cartSubtotalPrice", cart.getSubTotal());
        model.addAttribute("cartSize", cart.getCartSize() > 0 ? cart.getCartSizeByCart(cart) : 0);

        return "order-list";
    }


    @GetMapping(value = {"/details.html"})
    public String htmlMapping(@RequestParam int id, Model model) {
        Plushies plushie = Shop.getPlushiebyID(id);
        model.addAttribute("plushie", plushie);

        model.addAttribute("cartItems", cart.getCart_items());
        model.addAttribute("cartTotalPrice", cart.getGrandTotal());
        model.addAttribute("cartSubtotalPrice", cart.getSubTotal());
        if (cart.getCartSize() > 0) {
            model.addAttribute("cartSize", cart.getCartSizeByCart(cart));
        } else {
            model.addAttribute("cartSize", 0);
        }
        return "details";
    }

    @GetMapping(value = {"/order.html"})
    public String orderPage(@RequestParam (value = "orderCount", defaultValue = "1") int OrderCount, Model model) {
        List<Article> order = customer.getOrderByID(OrderCount);
        model.addAttribute("orderItems", order);
        model.addAttribute("cartItems", cart.getCart_items());
        model.addAttribute("cartTotalPrice", cart.getGrandTotal());
        model.addAttribute("cartSubtotalPrice", cart.getSubTotal());
        if (cart.getCartSize() > 0) {
            model.addAttribute("cartSize", cart.getCartSizeByCart(cart));
        } else {
            model.addAttribute("cartSize", 0);
        }
        /*model.addAttribute("orderTotalPrice", order.getGrandTotal());
       *//* model.addAttribute("orderSubtotalPrice", order.getSubTotal());*//*
        if (order.getOrderSize() > 0) {
            model.addAttribute("orderSize", order.getOrderSizeByOrder(order));
        } else {
            model.addAttribute("orderSize", 0);
        }*/
        return "order";
    }

/*    @GetMapping(value = {"/checkout.html"})
    public String checkOut(Model model) {
        model.addAttribute("cartItems", cart.getCart_items());
        model.addAttribute("cartTotalPrice", cart.getGrandTotal());
        model.addAttribute("cartSubtotalPrice", cart.getSubTotal());
        if (cart.getCartSize() > 0) {
            model.addAttribute("cartSize", cart.getCartSizeByCart(cart));
        } else {
            model.addAttribute("cartSize", 0);
        }
        return "checkout";
    }*/

    static class Page {
        private final int number;
        private final boolean isCurrent;

        Page(int number, boolean isCurrent) {
            this.number = number;
            this.isCurrent = isCurrent;
        }

        public int getNumber() {
            return number;
        }

        public boolean isCurrent() {
            return isCurrent;
        }
    }
}
