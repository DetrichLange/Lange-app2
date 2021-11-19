package baseline;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    public void readTSVFile_test() throws IOException {
        //This test attempts to read the TSV file in the data folder and convert it to an Inventory object.

        MyFileReader testReader = new MyFileReader();
        Inventory testInv;

        testInv = testReader.readInventoryFromFile("data/test.txt");
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
        assertEquals("A-XB1-24A-XY3", testScanner.next());
        assertEquals("Xbox Series X", testScanner.next());
        assertEquals("1499.00", testScanner.next());
        assertEquals("A-QB3-24Z-333", testScanner.next());
        assertEquals("A literal horse", testScanner.next());
        assertEquals("3000.00", testScanner.next());

        testScanner.close();
    }

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

}