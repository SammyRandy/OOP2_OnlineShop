package onlineshop.merchandise;

import java.util.Objects;

public class Article {
  private static int articleCounter = 1;
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
  /** quantity of item */
  protected int quantity = 1;
  /** URL to the original image*/
  protected String imageOnInspect;
  /** How many in Stock of item */
  protected int inStock;


  protected boolean showQuantity;

  public Article() {
    this.articleNo = Article.articleCounter++;
  }


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
    return Math.round((price * quantity) * 100.0) / 100.0;
  }

  public double getPricePerUnit() {
    return price;
  }

  public double getPriceOnSale() {
    priceOnSale = price * 1.1;
    priceOnSale = Math.round(priceOnSale * 100.0) / 100.0;
    return priceOnSale;
  }

  public String getName() {
    return name;
  }

  public int getQuantity() {
    return quantity;
  }
  public void setQuantity(int quantity) {
    if (quantity > 0){
      this.quantity = quantity;
    }
    else {
      System.out.println("Invalid Quantity");
    }

  }

  public void incrementQuantity() {
    this.quantity++;
  }

  public void decrementQuantity() {
    if (this.quantity > 0) {
      this.quantity--;
    }
    else{
      System.out.println("You don't have enough quantity");
    }
  }

  public boolean getShowQuantity(){

      showQuantity = quantity > 1;

    return showQuantity;
  }

  public int getInStock() {
    return inStock;
  }
  public void setInStock(int inStock) {
    this.inStock = inStock;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Article other = (Article) obj;
    return Objects.equals(this.articleNo, other.articleNo);
  }

  @Override
  public String toString() {
    return "Article{" +
            "articleNo=" + articleNo +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", image='" + image + '\'' +
            ", quantity=" + quantity +
            ", showQuantity=" + showQuantity +
            '}';
  }
}
