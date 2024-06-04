package onlineshop;

import onlineshop.merchandise.Plushie;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the Shop.
 */
@SpringBootApplication
public class Shop {
    private final static String RESOURCES = "src/main/resources/";
    private final static String CSV_FILE = "plushies.csv";

    private static Logger log = LogManager.getLogger(Shop.class);
    private final static List<Plushie> PLUSHIES = new ArrayList<>(220);

    private static List<Plushie> ORIGINAL_PLUSHIES = Collections.unmodifiableList(PLUSHIES);

    public static void main(String[] args) {
        readArticles(CSV_FILE, PLUSHIES);
        SpringApplication.run(Shop.class, args);
    }

    /**
     * Reads articles from a CSV file.
     *
     * @param fileName The name of the CSV file to read from.
     * @param plushies The list to populate with articles read from the CSV file.
     */
    private static void readArticles(String fileName, List<Plushie> plushies) {
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
                if (image.isEmpty()) image = "/images/winston_re.jpg";

                String imageOnInspect = record.get("ImageOnInspect");
                if (imageOnInspect.isEmpty()) image = "/images/winston_re.jpg";

                Plushie plushie = new Plushie(name, type, height, length, width, price, inStock, image, imageOnInspect);
                plushies.add(plushie);
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info(plushies.size() + " plushies imported");
    }

    /**
     * Returns an unmodifiable list of all articles. Used for sorting.
     *
     * @return An unmodifiable list of all articles.
     */
    public static List<Plushie> getArticles() {
        return new ArrayList<>(ORIGINAL_PLUSHIES);
    }

    /**
     * Retrieves a plushie by its article number.
     *
     * @param Id The article number of the plushie to retrieve.
     * @return The plushie with the specified article number, or null if not found.
     */
    public static Plushie getPlushiebyID(int Id) {
        for (Plushie plushies : ORIGINAL_PLUSHIES) {
            if (plushies.getArticleNo() == Id) {
                return plushies;
            }
        }
        return null;
    }

    /**
     * Removes an article by the id by copying the unmodifiable list, removing the article from there, then updating the unmodifiable list (ORIGINAL_PLUSHIES) with the copied list (mutablePlushies)
     *
     * @param Id
     */
    public static void removeArticle(int Id) {
        List<Plushie> mutablePlushies = new ArrayList<>(ORIGINAL_PLUSHIES);
        for (Plushie plushies : mutablePlushies) {
            if (plushies.getArticleNo() == Id) {
                mutablePlushies.remove(plushies);
                break; // Exit the loop after removing the article
            }
        }
        ORIGINAL_PLUSHIES = Collections.unmodifiableList(mutablePlushies);
    }
}
