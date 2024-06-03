package onlineshop;

import onlineshop.enums.ShoppingCost;
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

/**
 * The ShopController class handles requests related to the online shop.
 */
@Controller
public class ShopController {
    private static final int PAGE_SIZE = 12;
    private static Logger log = LogManager.getLogger(Shop.class);
    public final static String ARTICLES = "articles";

    @Autowired
    Cart cart;

    @Autowired
    Customer customer;

    /**
     * Redirects to the home page.
     *
     * @param model The model to add attributes to.
     * @return The name of the view to redirect to.
     */
    @GetMapping(value = {"/"})
    public String root(Model model) {
        return "redirect:/index.html";
    }

    /**
     * Displays the home page.
     *
     * @param model The model to add attributes to.
     * @param page  The current page number.
     * @param sort  The sorting option.
     * @return The name of the view to display.
     */
    @GetMapping(value = {"/index.html"})
    public String homePage(Model model,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "sort", defaultValue = "") String sort) {

        List<Plushies> allArticles = Shop.getArticles();
        for (Plushies p : allArticles) {
            if (p.getInStock() <= 0){
                Shop.removeArticle(p.getArticleNo());
            }
        }
        // Sorting
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

        model.addAttribute("isNameAsc", "nameAsc".equals(sort));
        model.addAttribute("isNameDesc", "nameDesc".equals(sort));
        model.addAttribute("isPriceAsc", "priceAsc".equals(sort));
        model.addAttribute("isPriceDesc", "priceDesc".equals(sort));
        model.addAttribute("isSizeAsc", "sizeAsc".equals(sort));
        model.addAttribute("isSizeDesc", "sizeDesc".equals(sort));

        // Pagination
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

        addCartAttributes(model);

        return "index";
    }


    /**
     * Displays the current view of the given name.
     *
     * @param name  The given name of the mustache file
     * @param model The model to add attributes to.
     * @return The name of the view to display.
     */
    @GetMapping(value = {"/{name}.html"})
    public String htmlMapping(@PathVariable String name, Model model) {
        addCartAttributes(model);
        addCartPriceAttributes(model);

        return name;
    }


    /**
     * Displays the checkout page.
     *
     * @param model The model to add attributes to.
     * @return The name of the view to display.
     */
    @GetMapping(value = {"/checkout.html"})
    public String checkoutPage(@RequestParam(required = false) Integer articleNo, @RequestParam(required = false) Integer quantity, Model model) {
        if (cart.getCartSize() == 0) {
            model.addAttribute("cartIsEmpty", true);
        } else {
            model.addAttribute("cartIsEmpty", false);
        }
        // Add the articleNo to the model if it's provided
        if (articleNo != null) {
            Article orderNowPlushie = (Article) Shop.getPlushiebyID(articleNo);
            if (quantity > orderNowPlushie.getInStock()) {
                quantity = orderNowPlushie.getInStock();
            }
            double price = orderNowPlushie.getPrice() * quantity;
            price = Math.round(price * 100.0) / 100.0;

            double shippingCosts = ShoppingCost.SHIPPING.getValue();
            double taxes = ShoppingCost.TAX_RATE.getValue();

            String taxOnDisplay = (int) (taxes * 100) + "%";
            double OrderNowGrandTotal = Math.round((price+ shippingCosts + (price * taxes)) * 100) / 100.0;
            model.addAttribute("orderNowPlushie", orderNowPlushie);
            model.addAttribute("quantityInOrderNow", quantity);
            model.addAttribute("priceInOrderNow", price);
            model.addAttribute("shippingCostsInOrder", shippingCosts);
            model.addAttribute("taxesInOrder", taxOnDisplay);
            model.addAttribute("OrderNowGrandTotal", OrderNowGrandTotal);
        }
        addCartAttributes(model);
        addCartPriceAttributes(model);
        return "checkout";
    }


    /**
     * Displays the order list page.
     *
     * @param model The model to add attributes to.
     * @return The name of the view to display.
     */
    @GetMapping(value = {"/order-list.html"})
    public String orderlistPage(Model model) {

        List<Order> orders = customer.getOrders();
        System.out.println(customer.getTotalSpend());
        model.addAttribute("orders", orders);
        model.addAttribute("ordersTotalSpend", customer.getTotalSpend());

        addCartAttributes(model);
        return "order-list";
    }


    /**
     * Displays the details page for a specific item.
     *
     * @param id    The ID of the item to display details for.
     * @param model The model to add attributes to.
     * @return The name of the view to display.
     */
    @GetMapping(value = {"/details.html"})
    public String detailsPage(@RequestParam int id, Model model) {

        Plushies plushie = Shop.getPlushiebyID(id);
        model.addAttribute("plushie", plushie);

        addCartAttributes(model);
        return "details";
    }

    /**
     * Displays the order page for a specific order.
     *
     * @param OrderCount The order number.
     * @param model      The model to add attributes to.
     * @return The name of the view to display.
     */
    @GetMapping(value = {"/order.html"})
    public String orderPage(@RequestParam(value = "orderCount", defaultValue = "1") int OrderCount, Model model) {

        Order order = customer.getOrderByID(OrderCount);
        System.out.println("price in orderpage; " + order.getTotal());
        model.addAttribute("orderItems", order.getOrder_items());
        model.addAttribute("orderTotalPrice", order.getTotal());
        model.addAttribute("orderSubtotalPrice", order.getSubTotal());

        addCartAttributes(model);

        addOrderPriceAttributes(model, order);
        return "order";
    }

    /**
     * Adds cart attributes to the model.
     *
     * @param model The model to add attributes to.
     */
    private void addCartAttributes(Model model) {
        model.addAttribute("cartItems", cart.getCart_items());
        model.addAttribute("cartTotalPrice", cart.getGrandTotal());
        model.addAttribute("cartSubtotalPrice", cart.getSubTotal());
        model.addAttribute("cartSize", cart.getCartSize() > 0 ? cart.getCartSizeByCart(cart) : 0);
    }

    /**
     * Adds cart price attributes to the model.
     *
     * @param model The model to add attributes to.
     */
    private void addCartPriceAttributes(Model model) {
        double shippingCosts = ShoppingCost.SHIPPING.getValue();
        double taxes = ShoppingCost.TAX_RATE.getValue();
        String taxOnDisplay = (int) (taxes * 100) + "%";
        double CartGrandTotal = Math.round((cart.getGrandTotal() + shippingCosts + (cart.getGrandTotal() * taxes)) * 100) / 100.0;
        model.addAttribute("shippingCosts", shippingCosts);
        model.addAttribute("taxOnDisplay", taxOnDisplay);
        model.addAttribute("grandTotal", CartGrandTotal);
    }

    /**
     * Adds order price attributes to the model.
     *
     * @param model The model to add attributes to.
     * @param order The order to calculate prices for.
     */
    private void addOrderPriceAttributes(Model model, Order order) {
        double shippingCosts = ShoppingCost.SHIPPING.getValue();
        double taxes = ShoppingCost.TAX_RATE.getValue();
        String taxOnDisplay = (int) (taxes * 100) + "%";
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
