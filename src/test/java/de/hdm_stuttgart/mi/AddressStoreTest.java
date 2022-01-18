package de.hdm_stuttgart.mi;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AddressStoreTest {

    @Test
    public void testRead() throws IOException {
        AddressStore addressStore = new AddressStore("src/test/resources/testread.txt");
        Person peter = new Person("Peter", "Müller", "", "+49 176 587 987");
        Person laura = new Person("Laura", "Müller", "müller@web.de", "0176 532-687");

        Person[] persons = addressStore.read();

        Assert.assertEquals(2, persons.length);
        Assert.assertEquals(peter.getFirstName(), persons[1].getFirstName());
        Assert.assertEquals(peter.getLastName(), persons[1].getLastName());
        Assert.assertEquals(peter.getEmail(), persons[1].getEmail());
        Assert.assertEquals(peter.getPhoneNumber(), persons[1].getPhoneNumber());

        Assert.assertEquals(laura.getFirstName(), persons[0].getFirstName());
        Assert.assertEquals(laura.getLastName(), persons[0].getLastName());
        Assert.assertEquals(laura.getEmail(), persons[0].getEmail());
        Assert.assertEquals(laura.getPhoneNumber(), persons[0].getPhoneNumber());
    }

    @Test
    public void testWrite() throws IOException {
        AddressStore addressStore = new AddressStore("src/test/resources/testwrite.txt");
        Person peter = new Person("Peter", "Müller", "", "+49 176 587 987");
        Person laura = new Person("Laura", "Müller", "müller@web.de", "0176 532-687");
        Person anna = new Person("Anna", "Winter", "winter@web.de", "");
        Person[] persons = {peter, laura, anna};

        addressStore.write(persons);

        //Quelle:https://stackoverflow.com/questions/27379059/determine-if-two-files-store-the-same-content
        byte[] f1 = Files.readAllBytes(Path.of("src/test/resources/testwriteexpected.txt"));
        byte[] f2 = Files.readAllBytes(Path.of("src/test/resources/testwrite.txt"));
        String s1 = new String(f1);
        String s2 = new String(f2);
        Assert.assertEquals(s1, s2);
    }
}
