package onlineshop;

import onlineshop.merchandise.Plushies;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the Shop
 */
@SpringBootApplication
public class Shop {
    private final static String RESOURCES = "src/main/resources/";
    private final static String CSV_FILE = "books.csv";
    private static Logger log = LogManager.getLogger(Shop.class);
    private final static List<Plushies> PLUSHIES = new ArrayList<>(220);

    public static void main(String[] args) {
        readArticles(CSV_FILE, PLUSHIES);
        SpringApplication.run(Shop.class, args);
    }

    /**
     * Read articles from a CSV file
     *
     * @param fileName {@link String}
     * @param plushies    {@link List}
     */
    private static void readArticles(String fileName, List<Plushies> plushies) {
        try {
            Reader in = new FileReader(RESOURCES + fileName);
            CSVFormat csvFormat = CSVFormat.EXCEL.withFirstRecordAsHeader().builder()
                    .setDelimiter(';')
                    .build();
            Iterable<CSVRecord> records = csvFormat.parse(in);

            for (CSVRecord record : records) {
                String name = record.get("Name");
                String type = record.get("Type");
                int height = Integer.parseInt(record.get("Height"));
                int length = Integer.parseInt(record.get("Length"));
                int width = Integer.parseInt(record.get("Width"));

                double price = 0.0;
                String priceString = record.get("Price");
                if (!priceString.isEmpty()) {
                    priceString = priceString.replace(',', '.');
                    price = Double.parseDouble(priceString);
                }

                int inStock = Integer.parseInt(record.get("InStock"));

                String image = record.get("Image");
                if (image.isEmpty()) image = "https://m.media-amazon.com/images/I/71PA63hllfL._AC_UF894,1000_QL80_.jpg";
                Plushies plushie = new Plushies(name, type, height, length, width, price, inStock, image);
                plushies.add(plushie);
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info(plushies.size() + " plushies imported");
    }

    public static List<Plushies> getArticles() {
        return PLUSHIES;
    }

    public static Plushies getPlushiebyID(int Id) {
        for (Plushies plushies : PLUSHIES) {
            if (plushies.getArticleNo() == Id) {
                return plushies;
            }
        }
        return null;
    }
}
