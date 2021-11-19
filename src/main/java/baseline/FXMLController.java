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
import javafx.stage.Stage;

public class FXMLController implements Initializable {

    @FXML private Button importButton = new Button();
    @FXML private Button exportButton = new Button();
    @FXML private Button newItemButton = new Button();
    @FXML private VBox itemsListBox = new VBox();
    @FXML private ChoiceBox<String> sortByChoiceBox = new ChoiceBox<>();
    @FXML private TextField searchTermField = new TextField();
    @FXML private TextField newSNField = new TextField();
    @FXML private TextField newNameField = new TextField();
    @FXML private TextField newValueField = new TextField();
    @FXML private Button searchSerialNumberButton = new Button();
    @FXML private Button searchNameButton = new Button();
    @FXML private Button clearSearchButton = new Button();
    @FXML private Button testButton = new Button();

    Inventory workingInv;
    List<HBox> boxList = new ArrayList<>();
    FileChooser fileChooser = new FileChooser();
    Scene workingScene;
    Alert alertPopup = new Alert(Alert.AlertType.ERROR);

    public void setData(Inventory startupInv, Scene scene) {
        //This method is called on startup to pass the Inventory and Scene reference to the controller
        workingInv = startupInv;
        workingScene = scene;
    }

    private File chooseFile() {
        //Open a filechooser window to select a file
        //Return the chosen file
        Stage stage = (Stage) importButton.getScene().getWindow();
        return fileChooser.showOpenDialog(stage);
    }

    private void makeNewItemBox() {
        //This method is called once by newItemButton (clicking the New Item button) and called repeatedly by
        // populateItemBoxes (when you import)

        boxList.add(new HBox());
        boxList.get(boxList.size() - 1).setSpacing(8.0);
        itemsListBox.getChildren().add(boxList.get(boxList.size() - 1));

        TextField itemSNField = new TextField();
        itemSNField.setPrefHeight(26.0);
        itemSNField.setPrefWidth(120.0);
        itemSNField.setId("serial number field " + boxList.size()/2);
        itemSNField.setText(workingInv.getEntry((boxList.size()/2)).getItemSerialNumber());

        TextField itemNameField = new TextField();
        itemNameField.setPrefHeight(26.0);
        itemNameField.setPrefWidth(120.0);
        itemNameField.setId("name field " + boxList.size()/2);
        itemNameField.setText(workingInv.getEntry((boxList.size()/2)).getItemName());

        TextField itemValueField = new TextField();
        itemValueField.setPrefHeight(26.0);
        itemValueField.setPrefWidth(120.0);
        itemValueField.setId("name field " + boxList.size()/2);
        itemValueField.setText(workingInv.getEntry((boxList.size()/2)).getItemValue());

        Button itemSaveButton = new Button("Update Item #" + (boxList.size()/2+1));
        itemSaveButton.setPrefWidth(110.00);
        itemSaveButton.setUserData(boxList.size() / 2);
        itemSaveButton.setOnAction(e ->
                saveItem((int) itemSaveButton.getUserData(), itemSNField, itemNameField, itemValueField));

        Button itemDeleteButton = new Button("Delete Item");
        itemDeleteButton.setPrefWidth(90.00);
        itemDeleteButton.setUserData(boxList.size() / 2);
        itemDeleteButton.setOnAction(e ->
                deleteItem((int) itemDeleteButton.getUserData()));

        boxList.get(boxList.size() - 1).getChildren().add(new Label("SN:"));
        boxList.get(boxList.size() - 1).getChildren().add(itemSNField);
        boxList.get(boxList.size() - 1).getChildren().add(new Label("Name:"));
        boxList.get(boxList.size() - 1).getChildren().add(itemNameField);
        boxList.get(boxList.size() - 1).getChildren().add(new Label("Value:"));
        boxList.get(boxList.size() - 1).getChildren().add(itemValueField);
        boxList.get(boxList.size() - 1).getChildren().add(itemSaveButton);
        boxList.get(boxList.size() - 1).getChildren().add(itemDeleteButton);
    }

    private void populateItemBoxes() {
        //This method is called when you import an inventory from a file
        //Call makeNewItemBox for each item in the inventory, so that the entire inventory is displayed in the GUI
        for(int i=0;i<workingInv.getLength();i++){
            makeNewItemBox();
        }
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


        MyFileReader fileReader = new MyFileReader();
        File importFile = chooseFile();

        if(importFile != null){
            clearListAndBoxes();
            workingInv = fileReader.readInventoryFromFile(importFile.getPath());
            populateItemBoxes();
        }

    }

    @FXML
    private void exportList() throws IOException {
        //Make a FileWriter object
        //Call chooseFile to open an explorer window
        //If a file was chosen:
            //Call writeListsToFile to export the data.
        //If no list was chosen, don't do anything.

        MyFileWriter fileWriter = new MyFileWriter();
        File exportFile = chooseFile();

        if(exportFile != null){
            fileWriter.writeInventoryToFile(exportFile.getPath(), workingInv);
        }

    }

    @FXML
    private void makeNewItem() {
        //This is called by the Add Item button in the bottom right.
        //If Inventory.checkItemValue, .checkItemName, and .checkItemSerial are all true:
            //Make a new InventoryItem using the contents of the three textfields
            //Add that InventoryItem to the Inventory
            //Make a new item box to display that item
        //Else, create error message.

        if(workingInv.checkItemName(newNameField.getText()) && workingInv.checkItemSerial(newSNField.getText())
                && workingInv.checkItemValue(newValueField.getText())){
            workingInv.addEntry(new InventoryItem(newSNField.getText(), newNameField.getText(), newValueField.getText()));
            makeNewItemBox();
            newSNField.clear();
            newNameField.clear();
            newValueField.clear();
        }else{
            alertPopup.show();
        }
    }

    @FXML
    private void clearListAndBoxes() {
        //When you click the Clear List button on the top left:
            //Tell the Inventory to clear itself.
            //Clear the nodes that displayed the old items from the GUI.
        workingInv.clearList();
        boxList.clear();
        itemsListBox.getChildren().clear();
    }

    @FXML
    private void saveItem(int whichItem, TextField itemSNField, TextField itemNameField, TextField itemValueField) {
        //This method is called by clicking the Save Item button next to an entry.
            //Each button has userData defining which entry it's associated with.
            //The button uses this userData for the whichItem parameter when it calls this method.
        //If Inventory.checkItemValue and .checkItemName are true, and the item's serial number is either unchanged or
        // passes .checkItemSerial:
            //Update the attributes of the item at the specified index

        if(workingInv.checkItemName(itemNameField.getText())
                && workingInv.checkItemValue(itemValueField.getText())
                && (  workingInv.checkItemSerial(itemSNField.getText())
                || workingInv.getEntry(whichItem).getItemSerialNumber().equals(itemSNField.getText())  )){
            workingInv.getEntry(whichItem).setItemSerialNumber(itemSNField.getText());
            workingInv.getEntry(whichItem).setItemName(itemNameField.getText());
            workingInv.getEntry(whichItem).setItemValue(itemValueField.getText());
        }else{
            itemSNField.setText(workingInv.getEntry(whichItem).getItemSerialNumber());
            itemNameField.setText(workingInv.getEntry(whichItem).getItemName());
            itemValueField.setText(workingInv.getEntry(whichItem).getItemValue());
            alertPopup.show();
        }
    }

    @FXML
    private void deleteItem(int whichItem) {
        //This method is called by clicking the Delete Item button next to an entry.
        //Each button has userData defining which entry it's associated with.
        //The button uses this userData for the whichItem parameter when it calls this method.

        //Tell the Inventory object which entry it should delete.
        //To prevent any gaps in the GUI, clear and re-populate the pane with the updated entries.

        workingInv.deleteEntry(whichItem);
        boxList.clear();
        itemsListBox.getChildren().clear();
        populateItemBoxes();
    }

    @FXML
    private void testButton() throws IOException {
        alertPopup.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sortByChoiceBox.getItems().add("Value");
        sortByChoiceBox.getItems().add("Serial number");
        sortByChoiceBox.getItems().add("Name");
        sortByChoiceBox.setValue("Value");

        alertPopup.setContentText("Invalid parameters for new item. Make sure SN is unique and in the format " +
                "A-XXX-XXX-XXX, where A is a letter and X can be a letter or a digit; name is between 2 and 256" +
                " characters inclusive; and value is a number greater than zero (do not include dollar sign).");
    }
}