package onlineshop;

/**
 * The BillingDetails class represents the details of a billing address and payment method for an online shop.
 */

public class BillingDetails {
    protected String firstname;
    protected String lastname;
    protected String companyname;
    protected String country;
    protected String address;
    protected String address2;
    protected String city;
    protected String state;
    protected String zip;
    protected String phone;
    protected String email;
    protected String paymentMethod;

    /**
     * Constructs a new BillingDetails object with the specified details.
     *
     * @param firstname    The first name of the customer.
     * @param lastname     The last name of the customer.
     * @param companyname  The name of the company (if applicable).
     * @param country      The country of the billing address.
     * @param address      The street address of the billing address.
     * @param address2     Additional address information (if applicable).
     * @param city         The city of the billing address.
     * @param state        The state or region of the billing address.
     * @param zip          The ZIP or postal code of the billing address.
     * @param phone        The phone number associated with the billing address.
     * @param email        The email address associated with the billing details.
     * @param paymentMethod The payment method used for the transaction.
     */

    public BillingDetails(String firstname, String lastname, String companyname, String country, String address, String address2, String city, String state, String zip, String phone, String email, String paymentMethod) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.companyname = companyname;
        this.country = country;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
        this.paymentMethod = paymentMethod;
    }

    public BillingDetails() {

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaymentMethod() { return paymentMethod; }

    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    @Override
    public String toString() {
        return "BillingDetails{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", companyname='" + companyname + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
