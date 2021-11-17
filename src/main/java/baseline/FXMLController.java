/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Detrich Lange
 */

package baseline;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class FXMLController implements Initializable {

    @FXML private Button importButton = new Button();
    @FXML private Button exportButton = new Button();
    @FXML private VBox itemsListBox = new VBox();
    @FXML private ChoiceBox<String> sortByChoiceBox = new ChoiceBox<>();
    @FXML private TextField searchTermField = new TextField();
    @FXML private Button searchSerialNumberButton = new Button();
    @FXML private Button searchNameButton = new Button();
    @FXML private Button clearSearchButton = new Button();

    Inventory workingList;
    List<HBox> boxList = new ArrayList<>();
    FileChooser fileChooser = new FileChooser();
    Scene workingScene;

    public void setData(Inventory startupList, Scene scene) {
        //This method is called on startup to pass the Inventory and Scene reference to the controller
    }

    private File chooseFile() {
        //Open a filechooser window to select a file
        //Return the chosen file
        return null;
    }

    private void makeNewItemBox() {
        //This method is called once by newItemButton (clicking the New Item button) and called repeatedly by
        // populateItemBoxes (when you import)

        //Make a new HBox
        //Attach the HBox to the VBox of item fields
        //Make a label and text field for the item serial number
        //Make a label and text field for the item name
        //Make a label and text field for the item value
        //Make a button to update the item
        //Make a button to delete the item
        //Attach all those things we just made to the HBox so that it looks pretty

    }

    private void populateItemBoxes() {
        //This method is called when you import an inventory from a file
        //Call makeNewItemBox for each item in the inventory, so that the entire inventory is displayed in the GUI
    }

    @FXML
    private void importList() throws IOException {
        //Make a FileReader object
        //Call chooseFile to open an explorer window
        //If a file was selected (not null):
            //Call readInventoryFromFile to create a new Inventory.
            //If readInventoryFromFile returns file not found exception:
                //Create error message.
            //If readInventoryFromFile returns file invalid exception:
                //Create error message
            //If new inventory is valid:
                //Call clearListAndBoxes to empty the entries from the working Inventory
                //Set the working Inventory to the new Inventory
                //Call populateItemBoxes to make a box for each entry in the new working list
        //If a file was not selected, leave the working list and scene as they are
    }

    @FXML
    private void exportList() throws IOException {
        //Make a FileWriter object
        //Call chooseFile to open an explorer window
        //If a file was chosen:
            //Call writeListsToFile to export the data.
        //If no list was chosen, don't do anything.
    }

    @FXML
    private void newItemButton() {
        //This is called by the Add Item button in the bottom right.
        //If Inventory.checkItemValue, .checkItemName, and .checkItemSerial are all true:
            //Make a new InventoryItem using the contents of the three textfields
            //Add that InventoryItem to the Inventory
            //Make a new item box to display that item
        //Else, create error message.
    }

    @FXML
    private void clearListAndBoxes() {
        //When you click the Clear List button on the top left:
            //Tell the Inventory to clear itself.
            //Clear the nodes that displayed the old items from the GUI.
    }

    @FXML
    private void saveItem(int whichItem, TextArea itemDescArea, DatePicker itemDateField, CheckBox itemCompletionBox) {
        //This method is called by clicking the Save Item button next to an entry.
            //Each button has userData defining which entry it's associated with.
            //The button uses this userData for the whichItem parameter when it calls this method.
        //If Inventory.checkItemValue and .checkItemName are true, and the item's serial number is either unchanged or
        // passes .checkItemSerial:
            //Update the attributes of the item at the specified index
    }

    @FXML
    private void deleteItem(int whichItem) {
        //This method is called by clicking the Delete Item button next to an entry.
        //Each button has userData defining which entry it's associated with.
        //The button uses this userData for the whichItem parameter when it calls this method.

        //Tell the Inventory object which entry it should delete.
        //To prevent any gaps in the GUI, clear and re-populate the pane with the updated entries.
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sortByChoiceBox.getItems().add("Value");
        sortByChoiceBox.getItems().add("Serial number");
        sortByChoiceBox.getItems().add("Name");
        sortByChoiceBox.setValue("Value");

    }
}