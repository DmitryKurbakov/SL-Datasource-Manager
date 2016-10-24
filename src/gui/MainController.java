package gui;


import dataobject.DataSource;
import dataobject.DsParams;
import helpers.ArangoDbManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;


public class MainController extends Application implements Initializable {

    private static Stage primaryStage;
    private List<DataSource> connections;
    private ArangoDbManager arangoDbManager;

    @FXML
    private AnchorPane main_pane;
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
    @FXML
    private TextArea log;

    public void setLog(String log) {
        this.log.setText(log);
    }

    @Override
    public void start(Stage stage) {
    }

    @FXML
    private void onCreateButton() throws Exception {
        primaryStage = new Stage();
        primaryStage.setTitle("Step 1");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("step1.fxml"));
        Pane pane = loader.load();
        Step1Controller sc = loader.getController();
        sc.setPrevStage(primaryStage);
        sc.logConsole = log;
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        Stage st = (Stage) main_pane.getScene().getWindow();
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
    }

    @FXML
    private void onEditButton() throws Exception {
        primaryStage = new Stage();
        primaryStage.setTitle("Step 2");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("step2.fxml"));
        Pane pane = loader.load();
        Step2Controller sc = loader.getController();
        sc.logConsole = log;
        DataSource selectedItem;
        int selectedRowIndex = tableView.getSelectionModel().getSelectedIndex();
        try {
            selectedItem = connections.get(selectedRowIndex);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return;
        }

        sc.setPrevStage(primaryStage);
        sc.setCurrentConnection(selectedItem);
        sc.setNameOfSource(selectedItem.getName());
        sc.setType(selectedItem.getDs_type());
        sc.setDatabaseName(selectedItem.getTgt_db());
        sc.setCollectionName(selectedItem.getTgt_collection());
        sc.setLoadFreq(selectedItem.getTgt_load_freq());
        sc.setSourceDescr(selectedItem.getDesc());
        sc.setUpdate(true);
        sc.setDatabasePropertiesChangingsDisable();
        sc.setUrlField(selectedItem.getRest_ds().getRest_base_url());
        sc.setHttpRequestParams(selectedItem.getRest_ds().getParams());

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        Stage st = (Stage) main_pane.getScene().getWindow();
        if (connections.get(selectedRowIndex).getDs_type().equals("File")) sc.hidePane();
        if (connections.get(selectedRowIndex).getDs_type().equals("REST")) sc.setBrowseButtonDisabled();
        sc.setSaveButtonDisable(false);
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
    }

    @FXML
    public void onDeleteButton() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Are you really want to delete this item?");
        alert.setContentText("Choose your option.");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {

            int selectedRowIndex = tableView.getSelectionModel().getSelectedIndex();
            DataSource selectedItem = connections.get(selectedRowIndex);

            arangoDbManager.deleteDocument(selectedItem.getTgt_db(), selectedItem.getTgt_collection(),
                    selectedItem.getKey());
        }
    }

    @FXML
    public void onSynchronizeButton() {
        loadData();
        log.appendText("Trying to synchronize data with database\n");
    }

    public MainController() {
        arangoDbManager = new ArangoDbManager();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadData();
        tableView.setEditable(false);
    }

    private void loadData() {
        colName.setCellValueFactory(new PropertyValueFactory<Row, String>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<Row, String>("type"));
        colCreated.setCellValueFactory(new PropertyValueFactory<Row, String>("created"));
        colCreatedBy.setCellValueFactory(new PropertyValueFactory<Row, String>("createdBy"));
        colLastUpdate.setCellValueFactory(new PropertyValueFactory<Row, String>("lastUpdate"));
        colLastUpdatedBy.setCellValueFactory(new PropertyValueFactory<Row, String>("lastUpdatedBy"));

        List<List<List<DataSource>>> ds = arangoDbManager.readDatabases();
        if (!ds.equals(null)) {
            connections = new ArrayList<>();
            for (List<List<DataSource>> d : ds) {
                for (List<DataSource> aD : d) {
                    for (DataSource anAD : aD) {
                        if (anAD != null) {
                            connections.add(anAD);
                        }
                    }
                }
            }

            ObservableList<Row> data = FXCollections.observableArrayList();
            for (DataSource connection : connections) {
                Row row = new Row();
                row.setName(connection.getName());
                row.setType(connection.getDs_type());
                row.setCreated(connection.getCreated());
                row.setCreatedBy(connection.getCreated_by());
                row.setLastUpdate(connection.getUpdated());
                row.setLastUpdateBy(connection.getUpdated_by());
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
