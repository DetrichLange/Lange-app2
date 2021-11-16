/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
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
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLController implements Initializable {

    @FXML private Button importButton = new Button();
    @FXML private Button exportButton = new Button();
    @FXML private VBox itemsListBox = new VBox();
    @FXML private ChoiceBox<String> sortByChoiceBox = new ChoiceBox<>();
    @FXML private TextField searchTermField = new TextField();
    @FXML private Button searchSerialNumberButton = new Button();
    @FXML private Button searchNameButton = new Button();
    @FXML private Button clearSearchButton = new Button();



    ToDoList workingList;
    List<HBox> boxList = new ArrayList<>();
    FileChooser fileChooser = new FileChooser();
    Scene workingScene;

    public void setData(ToDoList startupList, Scene scene) {}

    private File chooseFile() {
        return null;
    }

    private void makeNewItemBox() {}

    private void populateItemBoxes() {}

    @FXML
    private void importList() throws IOException {}

    @FXML
    private void exportList() throws IOException {}

    @FXML
    private void newItemButton() {}

    @FXML
    private void clearListAndBoxes() {}

    @FXML
    private void saveItem(int whichItem, TextArea itemDescArea, DatePicker itemDateField, CheckBox itemCompletionBox) {}

    @FXML
    private void deleteItem(int whichItem) {}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sortByChoiceBox.getItems().add("Value");
        sortByChoiceBox.getItems().add("Serial number");
        sortByChoiceBox.getItems().add("Name");
        sortByChoiceBox.setValue("Value");

    }
}