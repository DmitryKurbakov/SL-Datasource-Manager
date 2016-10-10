package gui;

import data_object.DataSource;
import data_object.FileDS;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sources.FileInFileSystem;
import sources.Rest;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Step2Controller implements Initializable {

    private static Stage prevStage;
    private static Stage primaryStage;

    private DataSource currentConnection;

    @FXML private TextField collectionName;

    @FXML private ChoiceBox loadFreq;

    @FXML private TextField nameOfSource;

    @FXML private TextArea sourceDescr;

    @FXML private TextField databaseName;

    @FXML private TextField type;

    @FXML private TextArea parsing;

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

        switch (currentConnection.getDs_type()) {
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
        setAtributes();
    }

    public void createFileSource() {
        FileInFileSystem source = null;
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
            fileChooser.setTitle("Choose file");
            source = new FileInFileSystem(currentConnection, fileChooser.showOpenDialog(prevStage).getAbsolutePath());
        } catch (NullPointerException ex) {
        }
        finally {
            parseFile(source);
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

    @FXML public void onPreviousButton() throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Step 1");
        Pane myPane = FXMLLoader.load(getClass().getResource("step1.fxml"));
        Scene scene = new Scene(myPane);
        stage.setScene(scene);
        prevStage.close();
        stage.show();
    }

    @FXML public void onExitButton(){
        prevStage.close();
    }

    private void parseFile(FileInFileSystem file){
        ObservableList ol = file.getRowset();
        parsing.setWrapText(true);
        parsing.setText(ol.toString());
    }

}
