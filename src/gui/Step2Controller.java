package gui;

import data_object.DataSource;
import data_object.FileDS;
import helpers.ArangoDbManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sources.FileInFileSystem;
import sources.Rest;

import java.net.URL;
import java.util.ResourceBundle;

public class Step2Controller implements Initializable {

    private static Stage prevStage;
    private static Stage primaryStage;

    private DataSource currentConnection;

    @FXML
    private TextField collectionName;

    @FXML
    private ChoiceBox loadFreq;

    @FXML
    private TextField nameOfSource;

    @FXML
    private TextArea sourceDescr;

    @FXML
    private TextField databaseName;

    @FXML
    private TextField type;

    public DataSource getCurrentConnection() {
        return currentConnection;
    }

    public void setCurrentConnection(DataSource currentConnection) {
        this.currentConnection = currentConnection;
    }


    public void setPrevStage(Stage stage) {
        this.prevStage = stage;
    }

    public void setNameOfSource(String nameOfSource) {
        this.nameOfSource.setText(nameOfSource);
    }

    public void setType(String type) {
        this.type.setText(type);
    }

    public Step2Controller() {
    }

    public void onSaveButton() throws Exception {

    }

    @FXML
    public void onLoadButton() {

        setAtributes();

        ArangoDbManager arangoDbManager = new ArangoDbManager();
        arangoDbManager.createDocument(currentConnection);
        DataSource ds = arangoDbManager.readDocument();

//        switch (currentConnection.getName()) {
//            case "File":
//                createFileSource();
//                break;
//            case "REST":
//                createRestSource();
//                break;
//            case "JDBC":
//                createJDBCSource();
//                break;
//        }
    }

    public void createFileSource() {

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
            fileChooser.setTitle("Choose file");
            FileInFileSystem source = new FileInFileSystem(fileChooser.showOpenDialog(prevStage).getAbsolutePath());
        } catch (NullPointerException ex) {
        }
    }


    //ObservableList<StringProperty> v = (ObservableList<StringProperty>) source.testConnection();
    //table_view.getItems().addAll(v);

    public void createRestSource() {
//        Rest rest = new Rest()
    }

    public void createJDBCSource() {
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    private void setAtributes() {
        currentConnection.setDesc(sourceDescr.getText());
        currentConnection.setTgt_db(databaseName.getText());
        currentConnection.setTgt_collection(collectionName.getText());
        currentConnection.setTgt_load_freq(loadFreq.getValue().toString());
    }

}
