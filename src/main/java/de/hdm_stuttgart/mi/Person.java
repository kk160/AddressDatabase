package de.hdm_stuttgart.mi;

import java.util.Objects;

/**
 * Class Person constructor
 */
public class Person {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;

    /**
     * @param firstName   first name
     * @param lastName    last name
     * @param email       email
     * @param phoneNumber phone number
     */
    public Person(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(email, person.email) &&
                Objects.equals(phoneNumber, person.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, phoneNumber);
    }

}
