package de.hdm_stuttgart.mi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.regex.Pattern;

public class AddressDatabaseTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream output = System.out;

    @Before
    public void setStream() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStream() {
        System.setOut(output);
    }

    @Test
    public void filterNoMatches() throws IOException {
        AddressStore as = new AddressStore("AddressDatabase.txt");
        Person[] persons = as.read();
        AddressDatabase.filter(persons, "a");
        String noNewLine = outContent.toString().replaceAll("\n", "").replaceAll("\r", "");
        Assert.assertEquals("No matches :(", noNewLine);
    }

    @Test
    public void filterOneLetterTest() throws IOException {
        AddressStore as = new AddressStore("AddressDatabase.txt");
        Person[] persons = as.read();
        AddressDatabase.filter(persons, "c");
        Assert.assertEquals(" 1 : Julius Caesar, email: , phone: \n", outContent.toString());
    }

    @Test
    public void filterTwoLettersTest() throws IOException {
        AddressStore as = new AddressStore("AddressDatabase.txt");
        Person[] persons = as.read();
        AddressDatabase.filter(persons, "aG");
        Assert.assertEquals("""
                 0 : Frank Bone, email: , phone: 885-11-87
                 1 : Julius Caesar, email: , phone:\s
                 2 : Ee Ff, email: hh, phone: 45
                 3 : A B, email: , phone: 24
                 4 : Eve Gardener, email: , phone: 32-44-2234
                """, outContent.toString());
    }

    @Test
    public void testValidateFirstName() {
        Assert.assertTrue(Pattern.matches(AddressDatabase.NAME_REGEX, "Laura"));
        Assert.assertTrue(Pattern.matches(AddressDatabase.NAME_REGEX, "Anna-Maria"));

        Assert.assertFalse(Pattern.matches(AddressDatabase.NAME_REGEX, "amelie"));
        Assert.assertFalse(Pattern.matches(AddressDatabase.NAME_REGEX, " Anna"));
        Assert.assertFalse(Pattern.matches(AddressDatabase.NAME_REGEX, "1Peter"));
        Assert.assertFalse(Pattern.matches(AddressDatabase.NAME_REGEX, "-alex"));
    }

    @Test
    public void testValidateSurname() {
        Assert.assertTrue(Pattern.matches(AddressDatabase.SURNAME_REGEX, "Müller"));
        Assert.assertTrue(Pattern.matches(AddressDatabase.SURNAME_REGEX, "de la Vega"));
        Assert.assertTrue(Pattern.matches(AddressDatabase.SURNAME_REGEX, "O'Donnell"));

        Assert.assertFalse(Pattern.matches(AddressDatabase.SURNAME_REGEX, "007"));
        Assert.assertFalse(Pattern.matches(AddressDatabase.SURNAME_REGEX, "-Langstrumpf"));
        Assert.assertFalse(Pattern.matches(AddressDatabase.SURNAME_REGEX, " Bauer"));

    }

    @Test
    public void testValidateEmail() {
        Assert.assertTrue(Pattern.matches(AddressDatabase.EMAIL_REGEX, "laura@web.de"));
        Assert.assertTrue(Pattern.matches(AddressDatabase.EMAIL_REGEX, "amelie@web.org"));
        Assert.assertTrue(Pattern.matches(AddressDatabase.EMAIL_REGEX, "007-Agent@gmail.org"));

        Assert.assertFalse(Pattern.matches(AddressDatabase.EMAIL_REGEX, "peter@gmail.com.au"));
        Assert.assertFalse(Pattern.matches(AddressDatabase.EMAIL_REGEX, "mike*/+#@yahoo.de"));
        Assert.assertFalse(Pattern.matches(AddressDatabase.EMAIL_REGEX, " @web.de"));
    }

    @Test
    public void testValidatePhoneNumber() {
        Assert.assertTrue(Pattern.matches(AddressDatabase.PHONE_REGEX, "885-11-87"));
        Assert.assertTrue(Pattern.matches(AddressDatabase.PHONE_REGEX, "0176 97836521"));
        Assert.assertTrue(Pattern.matches(AddressDatabase.PHONE_REGEX, "+49 176 987-345"));
        Assert.assertTrue(Pattern.matches(AddressDatabase.PHONE_REGEX, "885-11-87"));
        Assert.assertTrue(Pattern.matches(AddressDatabase.PHONE_REGEX, "110"));

        Assert.assertFalse(Pattern.matches(AddressDatabase.PHONE_REGEX, "0+49 176 975-32"));
        Assert.assertFalse(Pattern.matches(AddressDatabase.PHONE_REGEX, "+49 176 65-34 de"));

    }

    @Test
    public void inputDeleteTest() throws IOException {
        AddressStore as = new AddressStore("AddressDatabase.txt");
        Person[] persons = as.read();
        Person[] copyPersons = AddressDatabase.removePerson(6, persons);
        Person[] testPersons = new Person[8];
        testPersons[0] = new Person("Frank", "Bone", "", "885-11-87");
        testPersons[1] = new Person("Julius", "Caesar", "", "");
        testPersons[2] = new Person("Ee", "Ff", "hh", "45");
        testPersons[3] = new Person("A", "B", "", "24");
        testPersons[4] = new Person("Eve", "Gardener", "", "32-44-2234");
        testPersons[5] = new Person("Mark", "P", "mark@web.de", "0176 97836521");
        testPersons[6] = new Person("Peter", "Lustig", "", "+49 176 987-45");
        testPersons[7] = new Person("Hans", "Lustig", "hans@web.org", "");

        Assert.assertEquals(testPersons[6].hashCode(), copyPersons[6].hashCode());

    }

    @Test
    public void testPrintPersonEntries() throws IOException {
        //noinspection SpellCheckingInspection
        AddressStore addressStore = new AddressStore("src/test/resources/testprintpersonentries.txt");

        Person[] persons = addressStore.read();
        AddressDatabase.printPersonEntries(persons);

        Assert.assertEquals("""
                 0 : Mark P, email: mark@web.de, phone: 0176 97836521
                 1 : Peter Lustig, email: -, phone: +49 176 987-45
                 2 : Hans Lustig, email: hans@web.org, phone: -
                """, outContent.toString());


    }

}
