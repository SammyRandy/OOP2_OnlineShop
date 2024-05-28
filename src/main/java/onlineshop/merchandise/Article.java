package onlineshop.merchandise;

import java.util.Objects;

public class Article {
  private static int articleCounter = 1;
  /** Unique article number */
  protected int articleNo;
  /** Display-name of this Article */
  protected String name;
  /** Manufacturer of this Article */
  protected String manufacturer;
  /** Shop price */
  protected double price;
  /** URL to the image */
  protected String image;
  /** quantity of item */
  protected int quantity = 1;

  protected boolean showQuantity;

  public Article() {
    this.articleNo = Article.articleCounter++;
  }

  public Article(String name, String manufacturer) {
    this.name = name;
    this.manufacturer = manufacturer;
  }

  public Article(int articleNo, String name, double price, String image, int quantity, boolean showQuantity) {
    this.articleNo = Article.articleCounter++;
    this.name = name;

    this.price = price;
    this.image = image;
    this.quantity = quantity;
    this.showQuantity = showQuantity;

  }

  public int getArticleNo() {
    return articleNo;
  }

  public String getImage() {
    return image;
  }


  public double getPrice() {
    return Math.round((price * quantity) * 100.0) / 100.0;
  }

  public double getPricePerUnit() {
    return price;
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
    boolean show = false;

    if (quantity > 1)
    {
      show = true;
    }
    showQuantity = show;

    return showQuantity;
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
            ", manufacturer='" + manufacturer + '\'' +
            ", price=" + price +
            ", image='" + image + '\'' +
            ", quantity=" + quantity +
            ", showQuantity=" + showQuantity +
            '}';
  }
}
