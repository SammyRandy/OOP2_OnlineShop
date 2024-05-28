package onlineshop;

public class BillingDetails {
    private String firstname;
    private String lastname;
    private String companyname;
    private String country;
    private String address;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String email;

    public BillingDetails(String firstname, String lastname, String companyname, String country, String address, String address2, String city, String state, String zip, String phone, String email) {
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
    }

    public BillingDetails() {

    }

    // Getter and setter methods for each property

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
                '}';
    }
}
