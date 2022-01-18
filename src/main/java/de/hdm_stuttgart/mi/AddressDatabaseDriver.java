package de.hdm_stuttgart.mi;

import com.sun.tools.javac.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Class containing public static void main
 */
public class AddressDatabaseDriver {

    /**
     * @param args args
     * @throws IOException if problem with file
     */
    public static void main(String[] args) throws IOException {

        int userInput;
        AddressStore as = new AddressStore("AddressDatabase.txt");
        Person[] persons = as.read();
        do {
            switch (userInput = AddressDatabase.printMainOptions()) {
                case 0:
                    AddressDatabase.printPersonEntries(persons);
                    AddressDatabase.goToMainMenu();
                    break;
                case 1:
                    String nameInput = AddressDatabase.toggleFilteringPersonEntries();
                    AddressDatabase.filter(persons, nameInput);
                    AddressDatabase.goToMainMenu();
                    break;
                case 2:
                    Person person = AddressDatabase.createNewEntry();
                    persons = AddressDatabase.append(persons, person);
                    as.write(persons);
                    AddressDatabase.goToMainMenu();
                    break;
                case 3:
                    int input = AddressDatabase.deletePersonEntry(persons);
                    int in = AddressDatabase.finalDecision(input, persons);
                    if (in == 1) {
                        AddressDatabase.goToMainMenu();
                        break;
                    }
                    persons = AddressDatabase.removePerson(input, persons);
                    as.write(persons);
                    AddressDatabase.goToMainMenu();
                    break;
                case 4:
                    AddressDatabase.exit();
                    break;
                default:
                    break;
            }
        } while (userInput != 4);

    }
}
