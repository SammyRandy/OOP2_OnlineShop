package onlineshop.merchandise;

/**
 * The Plushies class represents a type of merchandise, specifically plush toys, in an online shop.
 */
public class Plushie extends Article {
    /** Name of the plushie */
    protected String name;

    /** Type of the plushie */
    protected String type;

    /** Height of the plushie */
    protected int height;

    /** Length of the plushie */
    protected int length;

    /** Width of the plushie */
    protected int width;

    /** Price of the plushie */
    protected double price;

    /** URL to the resized image of the plushie */
    protected String image;

    /** Size of the plushie */
    protected double size;

    /** Quantity of the plushie in stock */
    protected int inStock;

    /** String representation of the size of the plushie */
    protected String stringSize;

    public Plushie() {
        super();
    }

    /**
     * Constructs a new Plushies object with the specified details.
     *
     * @param name          The name of the plushie.
     * @param type          The type of the plushie.
     * @param height        The height of the plushie.
     * @param length        The length of the plushie.
     * @param width         The width of the plushie.
     * @param price         The price of the plushie.
     * @param inStock       The quantity of the plushie in stock.
     * @param image         The URL to the resized image of the article.
     * @param imageOnInspect The URL to the original image of the article.
     */
    public Plushie(String name, String type, int height, int length, int width, double price, int inStock, String image, String imageOnInspect) {
        super(0, name, price, image, 1, false, imageOnInspect, inStock);

        // Set additional properties specific to Plushie
        this.type = type;
        this.height = height;
        this.length = length;
        this.width = width;
    }

    public String getName() {
        this.name = super.getName();
        return name;
    }

    public String getType() {
        return type;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Calculates and returns the size of the plushie to be able to sort.
     *
     * @return The size of the plushie.
     */
    public double getSize() {
        this.size = width * height * length;
        return size;
    }

    /**
     * Returns a string representation of the size of the plushie.
     *
     * @return A string representation of the size of the plushie.
     */
    public String getStringSize(){
        this.stringSize = width+"cm/"+height+"cm/"+length+"cm";
        return stringSize;
    }


    public String getImage() {
        this.image = super.getImage();
        return image;
    }


    public int getInStock() {
        this.inStock = super.getInStock();
        return inStock;
    }

    public void setInStock(int inStock) {
        super.setInStock(inStock);
    }

    @Override
    public String toString() {
        return "Plushies{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", height=" + height +
                ", length=" + length +
                ", width=" + width +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", size=" + size +
                ", inStock=" + inStock +
                ", stringSize='" + stringSize + '\'' +
                '}';
    }
}
