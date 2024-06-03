package onlineshop.enums;

/**
 * The Gender enum represents different genders.
 */
public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    /** The label associated with the gender. */
    public final String label;

    /**
     * Constructs a new Gender enum constant with the specified label.
     *
     * @param label The label associated with the gender.
     */
    private Gender(String label) {
        this.label = label;
    }
}
