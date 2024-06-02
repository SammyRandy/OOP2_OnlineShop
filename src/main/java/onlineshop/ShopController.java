package onlineshop;

import onlineshop.enums.ShoppingCost;
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
            case "sizeAsc":
                Collections.sort(allArticles, Comparator.comparingDouble(Plushies::getSize).reversed());
                break;
            case "sizeDesc":
                Collections.sort(allArticles, Comparator.comparingDouble(Plushies::getSize));
                break;
            default:
                sort = ""; // Reset to default sorting if none selected
                allArticles = Shop.getArticles();
                break;
        }

        int totalArticles = allArticles.size();
        int totalPages = (int) Math.ceil((double) totalArticles / PAGE_SIZE);

        int start = (page - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, totalArticles);

        List<Plushies> paginatedArticles = allArticles.subList(start, end);

        model.addAttribute("articles", paginatedArticles);
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
            pages.add(new Page(i, i == page, sort));
        }
        model.addAttribute("pages", pages);

        model.addAttribute("isNameAsc", "nameAsc".equals(sort));
        model.addAttribute("isNameDesc", "nameDesc".equals(sort));
        model.addAttribute("isPriceAsc", "priceAsc".equals(sort));
        model.addAttribute("isPriceDesc", "priceDesc".equals(sort));
        model.addAttribute("isSizeAsc", "sizeAsc".equals(sort));
        model.addAttribute("isSizeDesc", "sizeDesc".equals(sort));

        addCartAttributes(model);


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
        addCartAttributes(model);
        addCartPriceAttributes(model);

        return name;
    }

    @GetMapping(value = {"/checkout.html"})
    public String checkoutPage(Model model) {
        if (cart.getCartSize() == 0) {
            model.addAttribute("cartIsEmpty", true);
        } else {
            model.addAttribute("cartIsEmpty", false);
        }
        addCartAttributes(model);

        addCartPriceAttributes(model);


        return "checkout";
    }

    @GetMapping(value = {"/order-list.html"})
    public String orderlistPage(Model model) {
        List<Order> orders = customer.getOrders();
        System.out.println(customer.getTotalSpend());
        model.addAttribute("orders", orders);
        model.addAttribute("ordersTotalSpend", customer.getTotalSpend());
        addCartAttributes(model);

        return "order-list";
    }


    @GetMapping(value = {"/details.html"})
    public String detailsPage(@RequestParam int id, Model model) {
        Plushies plushie = Shop.getPlushiebyID(id);
        model.addAttribute("plushie", plushie);

        addCartAttributes(model);
        return "details";
    }



    @GetMapping(value = {"/order.html"})
    public String orderPage(@RequestParam (value = "orderCount", defaultValue = "1") int OrderCount, Model model) {
        Order order = customer.getOrderByID(OrderCount);

        model.addAttribute("orderItems", order.getOrder_items());
        model.addAttribute("orderTotalPrice", order.getTotal());
        model.addAttribute("orderSubtotalPrice", order.getSubTotal());

        addCartAttributes(model);
        addOrderPriceAttributes(model, order);

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

    private void addCartAttributes(Model model) {
        model.addAttribute("cartItems", cart.getCart_items());
        model.addAttribute("cartTotalPrice", cart.getGrandTotal());
        model.addAttribute("cartSubtotalPrice", cart.getSubTotal());
        model.addAttribute("cartSize", cart.getCartSize() > 0 ? cart.getCartSizeByCart(cart) : 0);
    }

    private void addCartPriceAttributes(Model model) {
        double shippingCosts = ShoppingCost.SHIPPING.getValue();
        double taxes = ShoppingCost.TAX_RATE.getValue();
        String taxOnDisplay = (int)(taxes * 100) + "%";
        double CartGrandTotal = Math.round((cart.getGrandTotal() + shippingCosts + (cart.getGrandTotal() * taxes)) * 100) / 100.0;


        model.addAttribute("shippingCosts", shippingCosts);
        model.addAttribute("taxOnDisplay", taxOnDisplay);
        model.addAttribute("grandTotal", CartGrandTotal);


    }

    private void addOrderPriceAttributes(Model model, Order order) {
        double shippingCosts = ShoppingCost.SHIPPING.getValue();
        double taxes = ShoppingCost.TAX_RATE.getValue();
        String taxOnDisplay = (int)(taxes * 100) + "%";
        double orderGrandTotal = Math.round((order.getTotal() + shippingCosts + (order.getTotal() * taxes)) * 100) / 100.0;

        model.addAttribute("shippingCosts", shippingCosts);
        model.addAttribute("taxOnDisplay", taxOnDisplay);
        model.addAttribute("orderGrandTotal", orderGrandTotal);

    }

    static class Page {
        private final int number;
        private final boolean isCurrent;
        private final String sort;

        Page(int number, boolean isCurrent, String sort) {
            this.number = number;
            this.isCurrent = isCurrent;
            this.sort = sort;
        }

        public int getNumber() {
            return number;
        }

        public boolean isCurrent() {
            return isCurrent;
        }

        public String getSort() {
            return sort;
        }
    }

}
