/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Detrich Lange
 */


package baseline;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

class FileReader {

}

class FileWriter {

}

class ToDoList {

}

class ToDoListEntry {

}


public class InventoryManagementApplication extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws Exception {
        ToDoList startupList = new ToDoList();

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