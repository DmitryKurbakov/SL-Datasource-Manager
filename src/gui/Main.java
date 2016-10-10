package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("main_form.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Pane myPane = FXMLLoader.load(getClass().getResource("main_form.fxml"));
        Scene scene = new Scene(myPane);



        primaryStage.setTitle("SL Datasource Manager");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();

    }


    public static void main(String[] args) {launch(args);}
}
