package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Properties;


public class Main extends Application {
    private Stage primaryStage;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        scene = new Scene(root, 1000, 600);
        this.primaryStage.setTitle("Banking app");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();

    }

    public void setRoot(String fxmlFile) throws Exception {

        Parent root = FXMLLoader.load(Main.class.getResource(fxmlFile));
        this.primaryStage.show();

    }

    public static void main(String[] args) throws SQLException {
        launch(args);

    }
}