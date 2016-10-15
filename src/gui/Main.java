package gui;

import helpers.ArangoDbManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            //Создаем объект свойст
            Properties properties = new Properties();
            File file = new File("properties.properties");
            //Загружаем свойства из файла
            properties.load(new FileInputStream(file));
            ArangoDbManager.User = properties.getProperty("user");
            ArangoDbManager.Password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Parent root;
            Pane myPane;

            if (new ArangoDbManager().isAvailable()) {
                root = FXMLLoader.load(getClass().getResource("main_form.fxml"));
                myPane = FXMLLoader.load(getClass().getResource("main_form.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("autorization.fxml"));
                myPane = FXMLLoader.load(getClass().getResource("autorization.fxml"));
            }

            Scene scene = new Scene(myPane);

            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
