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

    public boolean checkItemValue(String value){
        //If the value double is >= 0:
            //Return true
        //Else:
            //Return false
        double valueDouble;

        try {
            valueDouble = Double.parseDouble(value);
        }catch(NumberFormatException nfe){
            return false;
        }

        return valueDouble >= 0;
    }

    public boolean checkItemName(String name){
        //If the name string is >=2 chars and <=256:
            //Return true
        //Else:
            //Return false

        return name.length() >= 2 && name.length() <= 256;
    }

    public boolean checkItemSerial(String serialNumber){
        //If the serialNumber string fits the format A-XXX-XXX-XXX:
            //If the serialNumber string is not identical to the serialNumber attribute of any InventoryItem in
            // the list:
                //Return true
        //Return false

        for(InventoryItem item : items){
            if(item.getItemSerialNumber().equals(serialNumber)){
                return false;
            }
        }
        return true;
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
        this.itemSerialNumber = serialNumber;
    }

    public void setItemName(String name){
        //Sets the new name for the item
        this.itemName = name;
    }

    public void setItemValue(String value){
        //Sets the new value for the item
        this.itemValue = value;
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
        Inventory startupInv = new Inventory();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene.fxml"));
        Parent root = loader.load();
        FXMLController controller = loader.getController();

        Scene scene = new Scene(root);
        controller.setData(startupInv, scene);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());

        stage.setTitle("To-Do List");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}