package gui;

import data_object.DataSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    private static Stage primaryStage;
    private ArrayList<DataSource> connections;

    @FXML
    private AnchorPane main_pane;

    @FXML
    private Button create_button;

    @FXML
    private void onCreateButton(ActionEvent event) throws Exception {

        primaryStage = new Stage();
        primaryStage.setTitle("Step 1");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("step1.fxml"));
        Pane pane = (Pane) loader.load();
        Step1Controller sc = (Step1Controller) loader.getController();
        sc.setPrevStage(primaryStage);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        Stage st = (Stage) main_pane.getScene().getWindow();
        //st.close();
        primaryStage.show();
    }

    @FXML
    private void onEditButton(ActionEvent event) throws Exception {
        primaryStage = new Stage();
        primaryStage.setTitle("Step 2");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("step2.fxml"));
        Pane pane = (Pane) loader.load();
        Step2Controller sc = (Step2Controller) loader.getController();
        sc.setCurrentConnection(connections.get(0));
        sc.setPrevStage(primaryStage);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        Stage st = (Stage) main_pane.getScene().getWindow();
        primaryStage.show();
    }

    /*
    public MainController(Stage primaryStage)throws Exception{
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("main_form.fxml"));
        primaryStage.setTitle("SL Datasource Manager");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }
    */


    public MainController() {
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }
}
