/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Detrich Lange
 */


package baseline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

class FileReader {

    public Inventory readInventoryFromFile(String filePath) throws IOException {
        //Taking a path string as an argument, attempts to open a file.

        Path importPath = Paths.get(filePath);
        if(Files.exists(importPath)){
            //If the file path ends in .html, call parseHTMLFile
            if(filePath.endsWith(".html")){
                return parseHTMLFile(importPath);
            }
            //Else if the file path ends in .json, call parseJSONFile
            else if(filePath.endsWith(".json")){
                return parseJSONFile(importPath);
            }
            //Else if the file path ends in .txt, call parseTSVFile
            else if(filePath.endsWith(".txt")){
                System.out.println("Found text file");
                return parseTSVFile(importPath);
            }
            else{
                //If the file has a bad extension, return null.
                return null;
            }
        }else{
            //If you somehow manage to try to read a file that doesn't exist, return null.
            return null;
        }
    }

    private Inventory parseTSVFile(Path importFile){
        //Make a new Inventory
        Inventory importInv = new Inventory();

        String nextSerial;
        String nextName;
        String nextValue;

        try(Scanner fileInput = new Scanner(importFile)){
            //Add the tab delimiter
            fileInput.useDelimiter("\\t|\r\n");
            //Skip the first line of the file
            fileInput.nextLine();

            //While the import file has a next line:
            while(fileInput.hasNext()){
                //Read until encountering a tab, saving the result as a serial number
                nextSerial = fileInput.next();
                //Read until encountering a tab, saving the result as a name
                nextName = fileInput.next();
                //Read until end of line, saving the result as a price
                nextValue = fileInput.next();

                //Add a new InventoryItem to the Inventory using those three parameters, repeat loop
                importInv.addEntry(new InventoryItem(nextSerial, nextName, nextValue));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //When the importFile has no more lines, return the Inventory
        return importInv;
    }

    private Inventory parseHTMLFile(Path importFile){
        //Make a new Inventory
        //Read lines from the importFile until you get the line "<th>Value</th>"
        //Skip one line
        //If the next line is not "</table>":
            //Read the next line as a string, remove the <td> and </td> tags, save the result as a serial number
            //Read the next line as a string, remove the <td> and </td> tags, save the result as a name
            //Read the next line as a string, remove the <td> and </td> tags, save the result as a value
            //Add a new InventoryItem to the Inventory using those three parameters
            //Skip one line, repeat loop
        //Else, return the inventory

        return null;
    }

    private Inventory parseJSONFile(Path importFile) throws IOException {
        //Make a Gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //Make a BufferedReader
        BufferedReader bufferedReader = new BufferedReader(
                new java.io.FileReader("data/test.json"));

        //Make an Inventory from Gson.fromJson(BufferedReader, Inventory.class)
        Inventory importInv = gson.fromJson(bufferedReader, Inventory.class);
        bufferedReader.close();

        //Return the Inventory
        return importInv;
    }
}

class FileWriter {
    public boolean writeInventoryToFile(String filePath, Inventory saveData){
        //Taking a file path string and an Inventory object as arguments, this method opens a formatter for the file
        //at the specified path.

        //If the path ends in .html, call writeHTMLFile and return true
        //Else if the path ends in .json, call writeJSONFile and return true
        //Else if the path ends in .txt, call writeTSVFile and return true

        //If the path doesn't end in any of those extensions, or the file can't be found, return false

        return false;
    }

    public boolean writeHTMLFile(File targetFile, Inventory saveData){
        //Create a stringbuilder outputHTML
        //Add the initial HTML tags and style data to outputHTML
        //Add the table header to outputHTML
        //For each item in the Inventory:
            //Add the serial number, name, and value, separated by table formatting tags, to outputHTML
        //Add the HTML closing tags to outputHTML
        //Write outputHTML to targetFile

        return false;
    }

    public boolean writeJSONFile(File targetFile, Inventory saveData){
        //Create a Gson from a GsonBuilder with PrettyPrinting on
        //Create a formatter
        //Print the Gson.toJson(saveData) to the targetFile

        return false;
    }
}

class Inventory {
    private List<InventoryItem> items = new ArrayList<>();

    public void addEntry(InventoryItem newEntry){
        //Adds a new entry to the list
        items.add(newEntry);
    }

    public InventoryItem getEntry(int index){
        //Returns the item at the specified index
        return items.get(index);
    }

    public int getLength(){
        //Returns the number of items in the inventory
        return items.size();
    }

    public void deleteEntry(int index){
        //Discards the item at the specified index
        items.remove(index);
    }

    public void clearList(){
        //Discards all items from the inventory
        items.clear();
    }

    public boolean checkItemValue(double value){
        //If the value double is >= 0:
            //Return true
        //Else:
            //Return false

        return false;
    }

    public boolean checkItemName(String name){
        //If the name string is >=2 chars and <=256:
            //Return true
        //Else:
            //Return false

        return false;
    }

    public boolean checkItemSerial(String serialNumber){
        //If the serialNumber string fits the format A-XXX-XXX-XXX:
            //If the serialNumber string is not identical to the serialNumber attribute of any InventoryItem in
            // the list:
                //Return true
        //Return false

        return false;
    }
}

class InventoryItem {

    //Each ToDoListEntry
    private String itemSerialNumber;
    private String itemName;
    private String itemValue;

    public InventoryItem(String serialNumber, String name, String value){
        //Creates a new item with attributes initialized.
        this.itemSerialNumber = serialNumber;
        this.itemName = name;
        this.itemValue = value;
    }

    public void setItemSerialNumber(String serialNumber){
        //Sets the new serial number for this item.
    }

    public void setItemName(String name){
        //Sets the new name for the item
    }

    public void setItemValue(String value){
        //Sets the new value for the item
    }

    public String getItemSerialNumber(){
        //Returns the serial number of the item
        return itemSerialNumber;
    }

    public String getItemName(){
        //Returns the name of the item
        return itemName;
    }

    public String getItemValue(){
        //Returns the value of the item
        return itemValue;
    }
}


public class InventoryManagementApplication extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws Exception {
        Inventory startupList = new Inventory();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene.fxml"));
        Parent root = loader.load();
        FXMLController controller = loader.getController();

        Scene scene = new Scene(root);
        controller.setData(startupList, scene);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());

        stage.setTitle("To-Do List");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}