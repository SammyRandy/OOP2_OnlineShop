package onlineshop.merchandise;

import onlineshop.Shop;

import java.util.Objects;

public class Article {
  // A counter to generate unique article numbers
  protected static int articleCounter = 1;

  /** Unique article number */
  protected int articleNo;

  /** Display-name of this Article */
  protected String name;

  /** Shop price */
  protected double price;

  /** On sale price */
  protected double priceOnSale;

  /** URL to the resized image */
  protected String image;

  /** URL to the original image*/
  protected String imageOnInspect;

  /** How many in Stock of item */
  protected int inStock;

  /** Quantity of item */
  protected int quantity = 1;

  /** Determines if quantity is to be shown */
  protected boolean showQuantity;

  /**
   * Default constructor for Article. Assigns a unique article number.
   */
  public Article() {
    this.articleNo = Article.articleCounter++;
  }

  /**
   * Constructs a new Article object with the specified details.
   *
   * @param articleNo     The unique article number.
   * @param name          The display name of the article.
   * @param price         The price of the article.
   * @param image         The URL to the resized image of the article.
   * @param quantity      The quantity of the article.
   * @param showQuantity  Flag indicating whether to show the quantity.
   * @param imageOnInspect The URL to the original image of the article.
   * @param inStock       The quantity of the article in stock.
   */
  public Article(int articleNo, String name, double price, String image, int quantity, boolean showQuantity, String imageOnInspect, int inStock) {
    this.articleNo = Article.articleCounter++;
    this.name = name;

    this.price = price;
    this.image = image;
    this.quantity = quantity;
    this.showQuantity = showQuantity;
    this.imageOnInspect = imageOnInspect;
    this.inStock = inStock;

  }

  /**
   * Constructs a new Article object as a copy of the specified Article.
   *
   * @param other The Article object to copy.
   */
  public Article(Article other) {
    this.articleNo = other.articleNo; // Generally, you might want to assign a new articleNo
    this.name = other.name;
    this.price = other.price;
    this.priceOnSale = other.priceOnSale;
    this.image = other.image;
    this.imageOnInspect = other.imageOnInspect;
    this.inStock = other.inStock;
    this.quantity = other.quantity;
    this.showQuantity = other.showQuantity;
  }

  public int getArticleNo() {
    return articleNo;
  }

  public String getImage() {
    return image;
  }

  public String getImageOnInspect(){
    return imageOnInspect;
  }

  public double getPrice() {
    return Math.round((price * quantity) * 100.0) / 100.0; // Rounds to 2 decimals
  }

  public double getPricePerUnit() {
    return price;
  }
  public double getPriceOnSale() {
    priceOnSale = price * 1.1;
    priceOnSale = Math.round(priceOnSale * 100.0) / 100.0; // Rounds to 2 decimals
    return priceOnSale;
  }

  public String getName() {
    return name;
  }

  public int getQuantity() {
    return quantity;
  }
  public void setQuantity(int quantity) {
    if (quantity > 0 && quantity <= getInStock()){
      this.quantity = quantity;
    }
  }

  public void incrementQuantity() {
    int increasedQuantity = quantity + 1;
    if (increasedQuantity < getInStock()) {
      this.quantity++;
    }
  }

  public void decrementQuantity() {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  /**
   * Returns a boolean indicating whether to show the quantity of the article.
   *
   * @return true if the quantity of the article is greater than 1, false otherwise.
   */
  public boolean getShowQuantity(){

      showQuantity = quantity > 1;

    return showQuantity;
  }

  public int getInStock() {
    if (inStock <= 0) {
      Shop.removeArticle(getArticleNo());
    }
    return inStock;
  }
  public void setInStock(int inStock) {
    this.inStock = inStock;
  }

  /**
   * Compares the given object to another object based on the articleNo
   * @param obj represents the given object to the function
   * @return A boolean that is true, when the 2 given objects have the same articleNo
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Article other = (Article) obj;
    return Objects.equals(this.articleNo, other.articleNo);
  }

  /**
   * Returns a string representation of the Article object.
   *
   * @return A string representation of the Article object.
   */
  @Override
  public String toString() {
    return "Article{" +
            "articleNo=" + articleNo +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", priceOnSale=" + priceOnSale +
            ", image='" + image + '\'' +
            ", quantity=" + quantity +
            ", imageOnInspect='" + imageOnInspect + '\'' +
            ", inStock=" + inStock +
            ", showQuantity=" + showQuantity +
            '}';
  }
}
