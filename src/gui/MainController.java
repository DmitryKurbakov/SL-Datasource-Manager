package gui;

import data_object.DataSource;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MainController extends Application implements Initializable {

    private static Stage primaryStage;
    private ArrayList<DataSource> connections;

    @FXML
    private AnchorPane main_pane;

    @FXML
    private Button create_button;

    @FXML
    private TableView tableView;
    @FXML private TableColumn colName;
    @FXML private TableColumn colType;
    @FXML private TableColumn colCreated;
    @FXML private TableColumn colCreatedBy;
    @FXML private TableColumn colLastUpdate;
    @FXML private TableColumn colLastUpdatedBy;

    @Override
    public void start(Stage stage){
    }

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
        colName.setCellValueFactory(new PropertyValueFactory<Row, String>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<Row, String>("type"));
        colCreated.setCellValueFactory(new PropertyValueFactory<Row, String>("created"));
        colCreatedBy.setCellValueFactory(new PropertyValueFactory<Row, String>("createdBy"));
        colLastUpdate.setCellValueFactory(new PropertyValueFactory<Row, String>("lastUpdate"));
        colLastUpdatedBy.setCellValueFactory(new PropertyValueFactory<Row, String>("lastUpdatedBy"));
    }

    @FXML public void onClose(){
        System.exit(0);
    }
}
