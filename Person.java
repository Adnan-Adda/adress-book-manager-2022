/**
 * Person represent an object with five members first name, last name, signature, length and Address.
 * It has a constructor with parameter to create new object and set values of its members,
 * and has an empty constructor without parameters and with default values for all members are set
 * to "unknown". The default values also are used when passing empty string as parameter.
 * The class has also getter and setter methods and override toString method.
 */
public class Person {
    private String firstName;
    private String lastName;
    private String signature;
    private int length;
    private Address address;

    /**
     * Creates new address object with firstName, lastName, signature, length and address object.
     * if the argument passed to constructor is empty then default value "unknown" is used.
     * @param firstName person firstname
     * @param lastName person lastname
     * @param signature person signature
     * @param length person length in cm
     * @param address person address
     */
    public Person(String firstName,String lastName, String signature,int length,Address address){
        this.firstName=firstName;
        this.lastName=lastName;
        this.signature=signature;
        setLength(length);
        setAddress(address);
    }

    /**
     * Create empty constructor with default values "unknown"
     * for firstName, lastName, signature, length and address object.
     */
    public Person(){
        this("","","",0,new Address());
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSignature() {
        return signature;
    }

    public int getLength() {
        return length;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * set person firstname, if empty default value "Unknown" is used.
     * @param firstName person firstname
     */
    public void setFirstName(String firstName) {
        if(firstName.isEmpty())
            this.firstName="Unknown";
        else
            this.firstName = firstName;
    }

    /**
     * set person lastname, if empty default value "Unknown" is used.
     * @param lastName person last name
     */
    public void setLastName(String lastName) {
        if(lastName.isEmpty())
            this.lastName="Unknown";
        else
            this.lastName = lastName;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * set person length in cm, if length is negative default 0 is set
     * @param length person length in cm
     */
    public void setLength(int length) {
        this.length = Math.max(length, 0);
    }

    /**
     * Set person address
     * @param address adress object
     */
    public void setAddress(Address address) {
        this.address = new Address();
        this.address.setZipCode(address.getZipCode());
        this.address.setStreetName(address.getStreetName());
        this.address.setCity(address.getCity());
    }

    /**
     * return firstname space lastname
     * @return firstname and lastname
     */
    public String getFullName(){
        return firstName + " " + lastName;
    }

    /**
     * Return a string of form firstName+"|"+lastName+"|"+signature+"|"+ length+"|"+streetName+"|"+ZipCode+"|"+city
     * @return formatted string
     */
    @Override
    public String toString(){
        return firstName+"|"+lastName+"|"+signature+"|"+ length+"|"+address.toString();
    }
}

