package de.hdm_stuttgart.mi;

import java.io.*;

/**
 * Class allowing to read and write in AddressDatabase.txt
 */
public class AddressStore {

    private final String file;

    /**
     * @param file file
     */
    public AddressStore(String file) {
        this.file = file;
    }

    /**
     * @param persons Object Person containing all persons from AddressDatabase.txt
     * @throws IOException if problem with file
     */
    public void write(Person[] persons) throws IOException {
        OutputStream outputStream = new FileOutputStream(file, false);
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < persons.length; i++) {
            Person person = persons[i];
            String firstName = person.getFirstName();
            String lastName = person.getLastName();
            String email = person.getEmail();
            String phoneNumber = person.getPhoneNumber();
            if (email.isEmpty()) {
                email = " ";
            }
            if (phoneNumber.isEmpty()) {
                phoneNumber = " ";
            }
            bufferedWriter.write(firstName + "," + lastName + "," + email + "," + phoneNumber);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    /**
     * Reads txt file and converts content to Person[]
     *
     * @return Person[]
     * @throws IOException if problem with file
     */
    public Person[] read() throws IOException {
        InputStream inStream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(inStream);
        BufferedReader bufferedReader = new BufferedReader(reader);

        Person[] persons = new Person[0];
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] arr = line.split(",", 4);

            for (int i = 0; i < arr.length; i++) {
                arr[i] = arr[i].trim();
            }
            String firstName = arr[0];
            String lastName = arr[1];
            String email = arr[2];
            String phoneNumber = arr[3];
            Person person = new Person(firstName, lastName, email, phoneNumber);
            persons = AddressDatabase.append(persons, person);
        }
        bufferedReader.close();
        return persons;
    }

}
