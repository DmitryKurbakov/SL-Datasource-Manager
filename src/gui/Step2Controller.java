package gui;

import data_object.DataSource;
import data_object.RestDs;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sources.FileInFileSystem;

import java.net.URL;
import java.util.ResourceBundle;

public class Step2Controller implements Initializable {

    private static Stage prevStage;
    private static Stage primaryStage;

    private String name;
    private String sv;
    private String filename;
    private FileInFileSystem source;

    private DataSource currentConnection;

    public DataSource getCurrentConnection() {
        return currentConnection;
    }

    public void setCurrentConnection(DataSource currentConnection) {
        this.currentConnection = currentConnection;
    }

    public String getSv() {
        return this.sv;
    }

    public void setSv(String sv) {
        this.sv = sv;
    }

    public void setPrevStage(Stage stage) {
        this.prevStage = stage;
    }

    public void setName(String name) {
        this.name = name;
    }

    @FXML
    private TextField collectionName;

    @FXML
    private ChoiceBox loadFreq;

    @FXML
    public TextField nameOfSource;

    @FXML
    private AnchorPane step2_pane;

    @FXML
    private AnchorPane table;

    @FXML
    private TextArea sourceDescr;

    @FXML
    private TextField databaseName;

    public Step2Controller() {

    }

    public void onSaveButton() throws Exception {

    }

    @FXML
    public void onLoadButton() {

        switch (sv) {
            case "File":
                createFileSource();
                break;
            case "REST":
                createRestSource();
                break;
            case "JDBC":
                createJDBCSource();
                break;
        }
    }

    public void createFileSource() {

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
            fileChooser.setTitle("Choose file");
            this.filename = fileChooser.showOpenDialog(prevStage).getAbsolutePath();
        } catch (NullPointerException ex) {
        }

        source = new FileInFileSystem(this.filename);
        source.getSource().setName(this.name);
    }


    //ObservableList<StringProperty> v = (ObservableList<StringProperty>) source.testConnection();
    //table_view.getItems().addAll(v);

    public void createRestSource() {
//        RestDs restDs = new RestDs()
//        currentConnection.setRest_ds();
    }

    public void createJDBCSource() {
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    private void setAtributes() {
        source.getSource().setName(this.name);
        source.getSource().setDesc(sourceDescr.getText());
        source.getSource().setTgt_db(databaseName.getText());
        source.getSource().setTgt_collection(collectionName.getText());
        source.getSource().setTgt_load_freq(loadFreq.getValue().toString());
    }

}//end of class
