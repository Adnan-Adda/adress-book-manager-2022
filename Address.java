/**
 * Address represent an object with three members streetName, ZipCode and city name.
 * It has a constructor with parameter to create new object and set values of its members,
 * and has an empty constructor without parameters and with default values for all members are set
 * to "unknown". The default values also are used when passing empty string as parameter.
 * The class has also getter and setter methods and override toString method.
 */
public class Address {
    private String ZipCode;
    private String city;
    private String streetName;

    /**
     * Creates new address object with streetName, ZipCode and city name.
     * if the argument passed to constructor is empty then default value "unknown" is used.
     * @param streetName street name
     * @param ZipCode zip code
     * @param city city name
     */
    public Address(String streetName,String ZipCode,String city){
        setStreetName(streetName);
        setZipCode(ZipCode);
        setCity(city);
    }

    /**
     * Create empty constructor with default values "unknown" for street name,zip code and city name.
     */
    public Address(){
        this("","","");
    }

    public String getStreetName() {
        return streetName;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return ZipCode;
    }

    /**
     * Set street name, if empty default value "Unknown" is used.
     * @param streetName street name
     */
    public void setStreetName(String streetName) {
        if(streetName.isEmpty())
            this.streetName="Unknown";
        else
            this.streetName = streetName;
    }

    /**
     * set zip code, if empty default value "Unknown" is used.
     * @param ZipCode zip code
     */
    public void setZipCode(String ZipCode) {
        if(ZipCode.isEmpty())
            this.ZipCode="Unknown";
        else
            this.ZipCode = ZipCode;
    }

    /**
     * set city name, if empty default value "Unknown" is used.
     * @param city city name
     */
    public void setCity(String city) {
        if (city.isEmpty())
            this.city="Unknown";
        else
            this.city = city;
    }

    /**
     * Return a string of form streetName+"|"+ZipCode+"|"+city
     */
    @Override
    public String toString(){
        return streetName+"|"+ZipCode+"|"+city;
    }
}
