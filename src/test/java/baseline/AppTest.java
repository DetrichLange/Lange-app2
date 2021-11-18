package baseline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    public void readTSVFile_test() throws IOException {
        //This test attempts to read the TSV file in the data folder and convert it to an Inventory object.

        FileReader testReader = new FileReader();
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

        FileReader testReader = new FileReader();
        Inventory testInv = testReader.readInventoryFromFile("data/test.json");

        assertEquals("Xbox Series X", testInv.getEntry(0).getItemName());
        assertEquals("3000.00", testInv.getEntry(1).getItemValue());
    }

    @Test
    public void readHTMLFile_test() throws IOException {
        //This test attempts to read the HTML file in the data folder and convert it to an inventory object.
        FileReader testReader = new FileReader();
        Inventory testInv = testReader.readInventoryFromFile("data/test.html");

        assertEquals("Xbox Series X", testInv.getEntry(0).getItemName());
        assertEquals("A-QB3-24Z-333", testInv.getEntry(1).getItemSerialNumber());
    }

//    @Test
//    public void writeJSONFile_test(){
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
//        Inventory testInv = new Inventory();
//        InventoryItem testItem = new InventoryItem("A-XB1-24A-XY3", "Xbox Series X", "1499.00");
//        InventoryItem testItem2 = new InventoryItem("A-QB3-24Z-333", "A literal horse", "3000.00");
//        testInv.addEntry(testItem);
//        testInv.addEntry(testItem2);
//
//        JsonElement je = JsonParser.parseString(gson.toJson(testInv));
//        String prettyJsonString = gson.toJson(je);
//
//        try(Formatter output = new Formatter("data/test.json")) {
//            output.format(prettyJsonString);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

}