package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("scene.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle("Calculator");
        stage.setResizable(true);
        stage.setMinWidth(200);
        stage.setMaxHeight(375);
        stage.setScene(scene);
        root.requestFocus();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
