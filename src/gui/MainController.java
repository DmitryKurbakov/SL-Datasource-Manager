package gui;

import data_object.DataSource;
import helpers.ArangoDbManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class MainController extends Application implements Initializable {

    private static Stage primaryStage;
    private List<DataSource> connections;
    private ArangoDbManager arango;
    @FXML
    private AnchorPane main_pane;

    @FXML
    private Button create_button;

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colType;
    @FXML
    private TableColumn colCreated;
    @FXML
    private TableColumn colCreatedBy;
    @FXML
    private TableColumn colLastUpdate;
    @FXML
    private TableColumn colLastUpdatedBy;

    @Override
    public void start(Stage stage) {
    }

    @FXML
    private void onCreateButton(ActionEvent event) throws Exception {
        primaryStage = new Stage();
        primaryStage.setTitle("Step 1");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("step1.fxml"));
        Pane pane = loader.load();
        Step1Controller sc = loader.getController();
        sc.setPrevStage(primaryStage);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        Stage st = (Stage) main_pane.getScene().getWindow();
        primaryStage.show();


    }

    @FXML
    private void onEditButton(ActionEvent event) throws Exception {
        primaryStage = new Stage();
        primaryStage.setTitle("Step 2");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("step2.fxml"));
        Pane pane = loader.load();
        Step2Controller sc = loader.getController();
        sc.setCurrentConnection(connections.get(0));
        sc.setPrevStage(primaryStage);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        Stage st = (Stage) main_pane.getScene().getWindow();
        primaryStage.show();
    }

    public MainController() {
        arango = new ArangoDbManager();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colName.setCellValueFactory(new PropertyValueFactory<Row, String>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<Row, String>("type"));
        colCreated.setCellValueFactory(new PropertyValueFactory<Row, String>("created"));
        colCreatedBy.setCellValueFactory(new PropertyValueFactory<Row, String>("createdBy"));
        colLastUpdate.setCellValueFactory(new PropertyValueFactory<Row, String>("lastUpdate"));
        colLastUpdatedBy.setCellValueFactory(new PropertyValueFactory<Row, String>("lastUpdatedBy"));

        List<List<List<DataSource>>> ds = arango.readDatabases();
        if (!ds.equals(null)) {
            connections = new ArrayList<DataSource>();
            for (int i = 0; i < ds.size(); i++) {
                for (int j = 0; j < ds.get(i).size(); j++) {
                    for (int k = 0; k < ds.get(i).get(j).size(); k++) {
                        DataSource data = (DataSource) ds.get(i).get(j).get(k);
                        if (data!=null) connections.add(data);
                    }
                }
            }

            ObservableList<Row> data = FXCollections.observableArrayList();
            for (int i = 0; i < connections.size(); i++) {
                Row row = new Row();
                row.setName(connections.get(i).getName());
                row.setType(connections.get(i).getDs_type());
                row.setCreated(new SimpleDateFormat("dd.MM.yyyy hh:mm").format(connections.get(i).getCreated()));
                row.setCreatedBy(connections.get(i).getCreated_by());
                row.setLastUpdate(new SimpleDateFormat("dd.MM.yyyy hh:mm").format(connections.get(i).getUpdated()));
                row.setLastUpdateBy(connections.get(i).getUpdated_by());
                data.add(row);
            }

            tableView.setItems(data);
        }
    }

    @FXML
    public void onClose() {
        System.exit(0);
    }
}
