package baseline;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    //The user shall be able to store at least 1024 inventory items
    @Test
    void bigInventory_test() throws IOException{
        //First, make a test file with 1024 items
        Formatter testWriter = new Formatter("data/bigtest.txt");
        testWriter.format("Serial Number\tName\tValue%n");
        for(int i=0;i<1000;i++){
            StringBuilder testSN = new StringBuilder();
            testSN.append("A-000-000-").append(i).append("0".repeat(13-testSN.length()));
            testWriter.format("%s\tHorse\t1000.00%n", testSN.toString());
            System.out.println(i);
        }
        for(int i=0;i<25;i++){
            StringBuilder testSN = new StringBuilder();
            testSN.append("A-000-999-").append(i).append("0".repeat(13-testSN.length()));
            testWriter.format("%s\tHorse\t1000.00%n", testSN.toString());
            System.out.println(i);
        }
        testWriter.close();

        //bigtest.txt now contains 1024 items, so by reading it into an Inventory, we have tested this requirement
        MyFileReader testReader = new MyFileReader();
        Inventory testInv = testReader.readInventoryFromFile("data/bigtest.txt");

        Scanner testScanner = new Scanner(Paths.get("data/bigtest.txt"));

        //To make sure we read it right, compare the created Inventory to the source file
        testScanner.nextLine(); //skip the first line because it just lists the attribute names
        for(int i=0;i<1024;i++){
            assertEquals(testInv.getEntry(i).getItemSerialNumber() + "\tHorse\t1000.00", testScanner.nextLine());
        }
    }


    //Each inventory item shall have a monetary value in US dollars
    //The monetary value of an item shall be greater than or equal to 0
    @Test
    void itemValue_test(){
        //The checkItemValue method should reject invalid values, so we'll check this requirement by testing that.
        Inventory testInv = new Inventory();

        assertFalse(testInv.checkItemValue("1000.000")); //Too many trailing decimals
        assertFalse(testInv.checkItemValue("100K.00")); //Has a letter
        assertFalse(testInv.checkItemValue("-1000.00")); //Negative number
        assertTrue(testInv.checkItemValue("1000.00")); //Valid value
    }


    //Each inventory item shall have a serial number in the format of A-XXX-XXX-XXX, where A must be a letter and X can be either a letter or digit
    //The serial number of an item shall be unique within the current set of inventory items
    @Test
    void itemSerialNumber_test(){
        //The checkItemSerial method should reject duplicate and invalid strings, so we'll check this requirement by testing that.
        Inventory testInv = new Inventory();
        testInv.addEntry(new InventoryItem("A-123-456-789", "Jetski", "15000.00"));

        assertFalse(testInv.checkItemSerial("0-000-000-000")); //Has a number where it should have a letter
        assertFalse(testInv.checkItemSerial("A-0000000-000")); //Has a number where it should have a hyphen
        assertFalse(testInv.checkItemSerial("A-***-000-000")); //Has a symbol where it should have a number
        assertFalse(testInv.checkItemSerial("A-123-456-789")); //Duplicates an existing serial number
        assertTrue(testInv.checkItemSerial("A-999-999-999")); //Valid serial number
    }


    //Each inventory item shall have a name
    //The name of an item shall be between 2 and 256 characters in length (inclusive)
    @Test
    void itemName_test(){
        //The checkItemName method should reject invalid names
        Inventory testInv = new Inventory();

        assertFalse(testInv.checkItemName("X")); //Too short
        assertFalse(testInv.checkItemName("X".repeat(500))); //Too long
        assertTrue(testInv.checkItemName("Super amazing awesome rocket horse #9000")); //Valid name
    }

    //The GUI shall have a control that allows the user to add a new inventory item
    @Test
    void makeNewItem_test(){
        //The GUI method calls the three above methods to check that the item's attributes are valid, then calls
        //Inventory.addEntry.
        Inventory testInv = new Inventory();
        testInv.addEntry(new InventoryItem("A-123-456-789", "Llama", "100.99"));

        assertEquals(1, testInv.getLength());
    }


    //The application shall display an error message if the user enters an existing serial number for the new item
    //The application shall display an error message if the user enters an invalid item value
    //The application shall display an error message if the user enters an invalid item name
    @Test
    void duplicateError_test(){
        //The GUI calls makeNewItemHelper, and if it returns false, creates an error alert.
        Inventory testInv = new Inventory();
        testInv.addEntry(new InventoryItem("A-123-456-789", "Horse", "1000.00"));

        //Testing that a duplicate SN returns false
        assertFalse(testInv.makeNewItemHelper("A-123-456-789", "Dog", "200.00"));

        //Testing that an invalid name returns false:
        assertFalse(testInv.makeNewItemHelper("A-999-999-999", "D", "200.00"));

        //Testing that an invalid value returns false:
        assertFalse(testInv.makeNewItemHelper("A-999-999-999", "Dog", "ABC.00"));

        //Testing that correct values return true:
        assertFalse(testInv.makeNewItemHelper("A-999-999-999", "Dog", "200.00"));
    }


    //The GUI shall have a control that allows the user to remove a single existing inventory item
    @Test
    void removeItem_test(){
        //The GUI calls Inventory.deleteEntry
        Inventory testInv = new Inventory();
        testInv.addEntry(new InventoryItem("A-123-456-789", "Cat", "50.00"));
        testInv.addEntry(new InventoryItem("B-123-456-789", "Dog", "50.00"));
        testInv.addEntry(new InventoryItem("C-123-456-789", "Bird", "50.00"));
        testInv.deleteEntry(1);

        //Three entries created, one deleted, length should be 2
        assertEquals(2, testInv.getLength());
    }


    //The GUI shall have a control that allows the user to remove all existing inventory items.
    @Test
    void clearItems_test(){
        //The GUI calls Inventory.clearList to remove all entries
        Inventory testInv = new Inventory();
        testInv.addEntry(new InventoryItem("A-123-456-789", "Cat", "50.00"));
        testInv.addEntry(new InventoryItem("B-123-456-789", "Dog", "50.00"));
        testInv.addEntry(new InventoryItem("C-123-456-789", "Bird", "50.00"));

        testInv.clearList();
        assertEquals(0, testInv.getLength());
    }


    //The GUI shall have a control that allows the user to edit the value of an existing inventory item
    //The application shall display an error message if the user enters an invalid item value
    @Test
    void editItemValue_test(){
        //The GUI calls Inventory.editItemHelper to check if the edit is valid, and displays an error message if it's not.
        Inventory testInv = new Inventory();
        testInv.addEntry(new InventoryItem("A-123-456-789", "Cat", "50.00"));
        testInv.addEntry(new InventoryItem("B-123-456-789", "Dog", "50.00"));

        //Invalid value, should return false
        assertFalse(testInv.editItemHelper(0, "Supercat", "A-123-456-789", "70000"));

        //Valid value, should return true
        assertTrue(testInv.editItemHelper(0, "Supercat", "A-123-456-789", "70.00"));
    }


    //The GUI shall have a control that allows the user to edit the serial number of an existing inventory item
    //The application shall prevent the user from duplicating the serial number
    @Test
    void editItemSerial_test(){
        //The GUI calls Inventory.editItemHelper to check if the edit is valid, and displays an error message if it's not.
        Inventory testInv = new Inventory();
        testInv.addEntry(new InventoryItem("A-123-456-789", "Cat", "50.00"));
        testInv.addEntry(new InventoryItem("B-123-456-789", "Dog", "50.00"));

        //Duplicate value, should return false
        assertFalse(testInv.editItemHelper(0, "Supercat", "B-123-456-789", "70000"));

        //Valid value, should return true
        assertTrue(testInv.editItemHelper(0, "Supercat", "A-999-999-999", "70.00"));
    }


    //The GUI shall have a control that allows the user to edit the name of an existing inventory item
    //The application shall display an error message if the user enters an invalid item name
    @Test
    void editItemName_test(){
        //The GUI calls Inventory.editItemHelper to check if the edit is valid, and displays an error message if it's not.
        Inventory testInv = new Inventory();
        testInv.addEntry(new InventoryItem("A-123-456-789", "Cat", "50.00"));
        testInv.addEntry(new InventoryItem("B-123-456-789", "Dog", "50.00"));

        //Invalid, should return false
        assertFalse(testInv.editItemHelper(0, "C", "A-123-456-789", "70000"));

        //Valid name, should return true
        assertTrue(testInv.editItemHelper(0, "Supercat", "A-123-456-789", "70.00"));
    }


    //The GUI shall have a control that allows the user to sort the inventory items by value
    @Test
    void sortValue_test(){
        //The GUI calls Inventory.sortByValue to sort the list of items
        Inventory testInv = new Inventory();

        testInv.addEntry(new InventoryItem("A-123-456-789", "Cat", "50.00"));
        testInv.addEntry(new InventoryItem("B-123-456-789", "Dog", "100.00"));
        testInv.addEntry(new InventoryItem("C-123-456-789", "Bird", "80.00"));
        testInv.sortByValue();

        assertEquals("100.00", testInv.getEntry(0).getItemValue());
    }


    //The GUI shall have a control that allows the user to sort inventory items by serial number
    @Test
    void sortSN_test(){
        //The GUI calls Inventory.sortByValue to sort the list of items
        Inventory testInv = new Inventory();

        testInv.addEntry(new InventoryItem("B-123-456-789", "Dog", "100.00"));
        testInv.addEntry(new InventoryItem("A-123-456-789", "Cat", "50.00"));
        testInv.addEntry(new InventoryItem("C-123-456-789", "Bird", "80.00"));
        testInv.sortBySN();

        assertEquals("A-123-456-789", testInv.getEntry(0).getItemSerialNumber());
    }


    //The GUI shall have a control that allows the user to sort inventory items by name
    @Test
    void sortName_test(){
        //The GUI calls Inventory.sortByValue to sort the list of items
        Inventory testInv = new Inventory();

        testInv.addEntry(new InventoryItem("B-123-456-789", "Dog", "100.00"));
        testInv.addEntry(new InventoryItem("A-123-456-789", "Cat", "50.00"));
        testInv.addEntry(new InventoryItem("C-123-456-789", "Bird", "80.00"));
        testInv.sortByName();

        assertEquals("Bird", testInv.getEntry(0).getItemName());
    }


    //The GUI shall have a control that allows the user to search for an inventory item by serial number
    @Test
    void searchSN_test(){
        //The GUI uses Inventory.searchSN on each item in the list, hiding all items that return false
        Inventory testInv = new Inventory();
        String searchTerm = "A";

        testInv.addEntry(new InventoryItem("B-123-456-789", "Dog", "100.00"));
        testInv.addEntry(new InventoryItem("A-123-456-789", "Cat", "50.00"));
        testInv.addEntry(new InventoryItem("C-123-456-789", "Bird", "80.00"));

        assertFalse(testInv.searchSN(0, searchTerm));
        assertTrue(testInv.searchSN(1, searchTerm));
        assertFalse(testInv.searchSN(2, searchTerm));
    }


    //The GUI shall have a control that allows the user to search for an inventory item by name
    @Test
    void searchName_test(){
        //The GUI uses Inventory.searchSN on each item in the list, hiding all items that return false
        Inventory testInv = new Inventory();
        String searchTerm = "at";

        testInv.addEntry(new InventoryItem("B-123-456-789", "Dog", "100.00"));
        testInv.addEntry(new InventoryItem("A-123-456-789", "Cat", "50.00"));
        testInv.addEntry(new InventoryItem("C-123-456-789", "Bird", "80.00"));

        assertFalse(testInv.searchName(0, searchTerm));
        assertTrue(testInv.searchName(1, searchTerm));
        assertFalse(testInv.searchName(2, searchTerm));
    }


    //The GUI shall have a control that allows the user to save their inventory items to a file
    //The user shall specify the file location and name for the saved data
    //The user shall be able to select the file format from among the following set of options: TSV
    // (tab-separated value), HTML, JSON

    //TSV files shall shall list one inventory item per line, separate each field within an inventory item using a tab
    // character, and end with the extension .txt
    @Test
    public void writeTSVFile_test() throws IOException {
        //This test attempts to turn an Inventory object into a TSV file in the data folder.

        MyFileWriter testWriter = new MyFileWriter();
        Inventory testInv = new Inventory();
        testInv.addEntry(new InventoryItem("A-XB1-24A-XY3", "Xbox Series X", "1499.00"));
        testInv.addEntry(new InventoryItem("A-QB3-24Z-333", "A literal horse", "3000.00"));

        testWriter.writeInventoryToFile("data/test2.txt", testInv);

        //Compare the produced file to a set of correct values and confirm that they are identical.
        Scanner testScanner = new Scanner(Paths.get("data/test2.txt"));
        testScanner.useDelimiter("\\t|\r\n");
        testScanner.nextLine();
        assertEquals("A-XB1-24A-XY3", testScanner.next());
        assertEquals("Xbox Series X", testScanner.next());
        assertEquals("1499.00", testScanner.next());
        assertEquals("A-QB3-24Z-333", testScanner.next());
        assertEquals("A literal horse", testScanner.next());
        assertEquals("3000.00", testScanner.next());

        testScanner.close();
    }


    //HTML files shall be a valid HTML document and end with the extension .html
    //The list of inventory items must be stored in a table element.
    @Test
    public void writeHTMLFile_test() throws IOException {
        //This test attempts to turn an Inventory object into a HTML file in the data folder.

        MyFileWriter testWriter = new MyFileWriter();
        Inventory testInv = new Inventory();
        testInv.addEntry(new InventoryItem("A-XB1-24A-XY3", "Xbox Series X", "1499.00"));
        testInv.addEntry(new InventoryItem("A-QB3-24Z-333", "A literal horse", "3000.00"));

        testWriter.writeInventoryToFile("data/test2.html", testInv);

        //Compare the produced file to a correct file and confirm that they are identical.
        Scanner testScanner = new Scanner(Paths.get("data/test.html"));
        Scanner testScanner2 = new Scanner(Paths.get("data/test2.html"));

        while(testScanner.hasNext()) {
            assertEquals(testScanner.nextLine(), testScanner2.nextLine());
        }

        testScanner.close();
        testScanner2.close();
    }


    //JSON files shall contain valid JSON and end with the extension .json
    @Test
    public void writeJSONFile_test() throws IOException {
        //This test attempts to turn an Inventory object into a json file in the data folder.

        MyFileWriter testWriter = new MyFileWriter();
        Inventory testInv = new Inventory();
        testInv.addEntry(new InventoryItem("A-XB1-24A-XY3", "Xbox Series X", "1499.00"));
        testInv.addEntry(new InventoryItem("A-QB3-24Z-333", "A literal horse", "3000.00"));

        testWriter.writeInventoryToFile("data/test2.json", testInv);

        //Compare the produced file to a correct file and confirm that they are identical.
        Scanner testScanner = new Scanner(Paths.get("data/test.json"));
        Scanner testScanner2 = new Scanner(Paths.get("data/test2.json"));

        while(testScanner.hasNext()) {
            assertEquals(testScanner.nextLine(), testScanner2.nextLine());
        }

        testScanner.close();
        testScanner2.close();
    }


    //The GUI shall have a control that allows the user to load inventory items from a file that was previously created
    // by the application.
    @Test
    public void readTSVFile_test() throws IOException {
        //This test attempts to read the TSV file in the data folder and convert it to an Inventory object.

        //First, write a TSV file.
        Formatter formatter = new Formatter("data/TSVreadtest.txt");
        formatter.format("Serial Number\tName\tValue%nA-XB1-24A-XY3\tXbox Series X\t1499.00%n" +
                "A-QB3-24Z-333\tA literal horse\t3000.00");
        formatter.close();

        MyFileReader testReader = new MyFileReader();
        Inventory testInv;

        testInv = testReader.readInventoryFromFile("data/TSVreadtest.txt");
        assertEquals("1499.00", testInv.getEntry(0).getItemValue());
        assertEquals("Xbox Series X", testInv.getEntry(0).getItemName());
        assertEquals("A-XB1-24A-XY3", testInv.getEntry(0).getItemSerialNumber());
        assertEquals("3000.00", testInv.getEntry(1).getItemValue());
        assertEquals("A literal horse", testInv.getEntry(1).getItemName());
        assertEquals("A-QB3-24Z-333", testInv.getEntry(1).getItemSerialNumber());
    }

    @Test
    public void readJSONFile_test() throws IOException {
        //This test attempts to read the JSON file in the data folder and convert it to an Inventory object.

        MyFileReader testReader = new MyFileReader();
        Inventory testInv = testReader.readInventoryFromFile("data/test.json");

        assertEquals("Xbox Series X", testInv.getEntry(0).getItemName());
        assertEquals("3000.00", testInv.getEntry(1).getItemValue());
    }

    @Test
    public void readHTMLFile_test() throws IOException {
        //This test attempts to read the HTML file in the data folder and convert it to an inventory object.
        MyFileReader testReader = new MyFileReader();
        Inventory testInv = testReader.readInventoryFromFile("data/test.html");

        assertEquals("Xbox Series X", testInv.getEntry(0).getItemName());
        assertEquals("A-QB3-24Z-333", testInv.getEntry(1).getItemSerialNumber());
    }
}