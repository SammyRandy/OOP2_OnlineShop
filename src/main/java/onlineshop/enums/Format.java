package onlineshop.enums;

public enum Format {
    PAPERBACK("Paperback"),
    HARDCOVER("Hardcover"),
    BOARD("Board Book"),
    EBOOK("E-Book"),
    AUDIO_DOWNLOAD("Audiobook (download)"),
    AUDIO_CD("Audiobook (CD)"),
    CALENDAR("Calendar");

    public final String label;

    private Format(String label) {
        this.label = label;
    }

}
