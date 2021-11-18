package baseline;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    public void readTSVFile_test() throws IOException {
        //This test attempts to read the TSV file in the data folder and convert it to an Inventory object.

        FileReader testReader = new FileReader();
        Inventory testInv = new Inventory();
        File testFile = new File("data/test.txt");
        Path importPath = Paths.get("data/test.txt");

        testInv = testReader.readInventoryFromFile("data/test.txt");
        assertEquals("1499.00", testInv.getEntry(0).getItemValue());
        assertEquals("Xbox Series X", testInv.getEntry(0).getItemName());
        assertEquals("A-XB1-24A-XY3", testInv.getEntry(0).getItemSerialNumber());
        assertEquals("3000.00", testInv.getEntry(1).getItemValue());
        assertEquals("A literal horse", testInv.getEntry(1).getItemName());
        assertEquals("A-QB3-24Z-333", testInv.getEntry(1).getItemSerialNumber());
    }

}