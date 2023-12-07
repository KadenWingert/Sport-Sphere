package coms309.models;

public class Person {
    private String firstName;

    private String lastName;
    private String message;
    private int age;
    private long phoneNumber;
    private String address;
    public Person(){}

    public Person(String message, int age,long phoneNumber, String address ){
        this.message = message;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    
    /** 
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public long getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @Override
    public String toString(){
        return "Name: " + this.getFirstName() + this.getLastName() +
                " Message: " + this.getMessage() +
                " address: " + this.getAddress() +
                " phone number: " + this.getPhoneNumber();
    }
}