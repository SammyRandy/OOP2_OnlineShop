package onlineshop.merchandise;

public class Plushies extends Article {
    protected String name;
    protected String type;
    protected int height;
    protected int length;
    protected int width;
    protected double price;
    protected String image;
    protected String size;
    protected int inStock;

    public Plushies() {
        super();
    }

    public Plushies(String name, String type) {
        super();
        this.name = name;
        this.type = type;
    }

    public Plushies(String name, String type, int height, int length, int width, double price, int inStock, String image) {
        // Call the Article constructor with appropriate arguments
        super(0, name, price, image, 1, false);

        // Set additional properties specific to Plushie
        this.name = name;
        this.inStock = inStock;
        this.type = type;
        this.height = height;
        this.length = length;
        this.width = width;
    }



    public String getName() {
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

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public String getSize(){
        this.size = width+"cm/"+height+"cm/"+length+"cm";
        return size;
    }

    @Override
    public String getImage() {
        this.image = super.getImage();
        return image;
    }

}
