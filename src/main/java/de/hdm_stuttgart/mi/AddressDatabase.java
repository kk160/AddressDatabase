package de.hdm_stuttgart.mi;

import java.util.Scanner;

/**
 * Class containing the different options
 */
public class AddressDatabase {

    /**
     * RegEx to match Person first name
     */
    public static final String NAME_REGEX = "^[A-Z][a-zA-Zäöüß -]*$";

    /**
     * RegEx to match Person surname
     */
    public static final String SURNAME_REGEX = "^[a-zA-Z][a-zA-Zäöüß '-]*$";

    /**
     * RegEx to match Person email
     */
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9][a-zA-Z0-9.-]*@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$";

    /**
     * RegEx to match Person phone number
     */
    public static final String PHONE_REGEX = "^[+]?[0-9 -]{2,}$";

    /**
     * Prints Main options
     *
     * @return user choice for next step
     */
    public static int printMainOptions() {
        Scanner scan = new Scanner(System.in);
        System.out.print("""
                Main options:
                                
                0: Browse person entries 
                1: Toggle filtering person entries 
                2: Create new person entry 
                3: Delete person entry 
                4: Exit
                Your choice:
                """);
        int input = scan.nextInt();
        while (input < 0 || input > 4) {
            System.out.println("Invalid number. Please enter a number between 0 and 4:");
            input = scan.nextInt();
        }
        return input;
    }

    /**
     * Appends new person to Person[] copy
     *
     * @param values   Person[n]
     * @param newValue Person
     * @return Person[n+1] copy
     */
    public static Person[] append(Person[] values, Person newValue) {
        Person[] copy = new Person[values.length + 1];
        //noinspection ManualArrayCopy
        for (int i = 0; i < values.length; i++) {
            copy[i] = values[i];
        }
        copy[copy.length - 1] = newValue;
        return copy;
    }

    /**
     * Option 0: Prints all person entries from the Address database
     *
     * @param persons Object Person containing all persons from AddressDatabase.txt
     */
    public static void printPersonEntries(Person[] persons) {
        for (int i = 0; i < persons.length; i++) {
            Person person = persons[i];
            String firstName = person.getFirstName();
            String lastName = person.getLastName();
            String email = person.getEmail();
            String phoneNumber = person.getPhoneNumber();
            if (email.isEmpty()) {
                email = "-";
            }
            if (phoneNumber.isEmpty()) {
                phoneNumber = "-";
            }
            System.out.format("%2d : %s %s, email: %s, phone: %s\n", i, firstName, lastName,
                    email, phoneNumber);
        }
    }

    /**
     * Option 1: Filters all persons from address database
     *
     * @return a valid nameInput
     */
    public static String toggleFilteringPersonEntries() {

        Scanner scan = new Scanner(System.in);
        System.out.format("""
                - Enter the first letter of surname (For example:A)
                - Enter 2 letters to search a range of surname (For example:AG to filter surname from A to G)
                 Your input:""");
        String nameInput = scan.nextLine();
        System.out.println();
        String nameRegex = "^[a-zA-Z]*$";
        while (!nameInput.matches(nameRegex) || nameInput.length() > 2) {
            System.out.println("Invalid input. Please enter a correct letter:");
            nameInput = scan.nextLine();
        }
        return nameInput;
    }

    /**
     * Defines regex with nameInput
     *
     * Filters Person[] persons and prints matches
     *
     * @param persons   Object Person containing all persons from AddressDatabase.txt
     * @param nameInput String containing one or two letters
     */
    public static void filter(Person[] persons, String nameInput) {
        nameInput = nameInput.toUpperCase();
        String regex;
        if (nameInput.length() < 2) {
            regex = "^" + nameInput + ".*";
        } else {
            regex = "^[" + nameInput.charAt(0) + "-" + nameInput.charAt(1) + "].*";
        }

        int matches = 0;
        for (int i = 0; i < persons.length; i++) {
            if (persons[i].getLastName().matches(regex)) {
                matches += 1;
                Person person = persons[i];
                System.out.format("%2d : %s %s, email: %s, phone: %s\n", i, person.getFirstName(), person.getLastName(),
                        person.getEmail(), person.getPhoneNumber());
            }

        }
        if (matches == 0) {
            System.out.println("No matches :(");
        }

    }


    /**
     * Option: Go to the main menu
     */
    public static void goToMainMenu() {
        Scanner scan = new Scanner(System.in);
        System.out.println();
        System.out.println("Enter: Go to the main menu");
        String nameInput= scan.nextLine();
    }


    /**
     * Option 2: Creates a new person entry
     *
     * @return new Person
     */
    public static Person createNewEntry() {

        String firstName = readAndValidateFirstName();
        String lastName = readAndValidateSurname();
        String email = readAndValidateEmail();
        String phoneNumber = readAndValidatePhoneNumber();
        System.out.println("Creating a new Person entry was successful!");
        return new Person(firstName, lastName, email, phoneNumber);
    }

    /**
     * Required: Read and validate user first name
     *
     * @return valid first name
     */
    public static String readAndValidateFirstName() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter a first given name (required field):");
        String nameInput = scan.nextLine();
        while (!nameInput.matches(NAME_REGEX)) {
            System.out.println("Invalid name. Please enter a correct name:");
            nameInput = scan.nextLine();
        }
        return nameInput;
    }

    /**
     * Required: Read and validate user surname
     *
     * @return valid surname
     */
    public static String readAndValidateSurname() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter a surname (required field):");
        String surnameInput = scan.nextLine();
        while (!surnameInput.matches(SURNAME_REGEX)) {
            System.out.println("Invalid surname. Please enter a correct surname:");
            surnameInput = scan.nextLine();
        }
        return surnameInput;
    }

    /**
     * Optional: Read and validate user email
     *
     * @return valid email
     */
    public static String readAndValidateEmail() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter an email (optional):");
        String emailInput = scan.nextLine();
        while (!emailInput.isEmpty() && !emailInput.matches(EMAIL_REGEX)) {
            System.out.println("Invalid mail. Please enter a correct email:");
            emailInput = scan.nextLine();
        }
        return emailInput;
    }

    /**
     * Optional: Read and validate user phone number
     *
     * @return valid phone number
     */
    public static String readAndValidatePhoneNumber() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter a phone number (optional):");
        String numberInput = scan.nextLine();
        String numberInputClean = numberInput.replaceAll("[ -]", "");
        while (!numberInputClean.isEmpty() && !numberInputClean.matches(PHONE_REGEX)) {
            System.out.println("Invalid phone number. Please enter a correct phone number:");
            numberInput = scan.nextLine();
            numberInputClean = numberInput.replaceAll(" -", "");
        }
        return numberInput;
    }


    /**
     * Option 3: Deletes 1 person from address database
     *
     * @param persons Object Person containing all persons from AddressDatabase.txt
     * @return a valid nInput
     */
    public static int deletePersonEntry(Person[] persons) {
        Scanner scan = new Scanner(System.in);
        System.out.format("""
                Enter the number of the person that you want to delete
                Your input:""");
        String nameInput = scan.nextLine();
        System.out.println();
        String nameRegex = "^[0-" + (persons.length - 1) + "]*$";
        while (!nameInput.matches(nameRegex)) {
            System.out.format("Invalid input. Please enter a correct input:");
            nameInput = scan.nextLine();
        }

        return Integer.parseInt(nameInput);
    }

    /**
     * Validates input
     *
     * @param nInput  1 number from 0 to persons.length -1
     * @param persons Object Person containing all persons from AddressDatabase.txt
     * @return an integer which is 0 or 1
     */
    public static int finalDecision(int nInput, Person[] persons) {
        Scanner scan = new Scanner(System.in);

        Person person = persons[nInput];
        System.out.println();
        System.out.println("Do you want to delete " + person.getFirstName() + " " + person.getLastName() + "?");
        System.out.format("""
                0 :Yes
                1 :No
                Your input:""");
        String input = scan.nextLine();
        String regex = "^[0-1]*$";
        while (!input.matches(regex)) {
            System.out.format("Invalid input. Please enter 0 or 1:");
            input = scan.nextLine();
        }


        return Integer.parseInt(input);
    }

    /**
     * Creates a copy of Person[] persons without the person who will be deleted
     *
     * @param input   should be a number from 0 to persons.length -1
     * @param persons Object Person containing all persons from AddressDatabase.txt
     * @return a new Person containing all persons who are not supposed to be deleted
     */
    public static Person[] removePerson(int input, Person[] persons) {

        Person[] personCopy = new Person[persons.length - 1];
        int position = 0;
        for (int i = 0; i < persons.length; i++) {
            if (i != input) {
                personCopy[position] = persons[i];
                position += 1;
            }

        }

        Person person = persons[input];
        System.out.println(person.getFirstName() + " " + person.getLastName() + " is deleted!");

        return personCopy;

    }


    /**
     * Option 4: Exits the program
     */
    public static void exit() {
        System.out.println("Goodbye!");
        System.exit(1);
    }


}
