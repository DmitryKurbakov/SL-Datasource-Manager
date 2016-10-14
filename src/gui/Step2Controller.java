package gui;

import com.arangodb.util.StringUtils;
import data_object.DataSource;
import data_object.DsParams;
import data_object.FileDS;
import data_object.RestDs;
import helpers.ArangoDbManager;
import helpers.TypeDef;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sources.FileInFileSystem;
import sources.Rest;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Step2Controller implements Initializable {


    private static Stage prevStage;
    private static Stage primaryStage;
    ArangoDbManager arangoDbManager;
    private String pathToFile;

    private DataSource currentConnection;

    @FXML private AnchorPane step2_pane;

    @FXML private AnchorPane paneCollectionName;

    @FXML private AnchorPane paneDatabaseName;

    @FXML private AnchorPane optionsPane;

    @FXML private AnchorPane parsingArea;

    @FXML private Tab autorizationTab;

    @FXML private TextField collectionName;

    @FXML private ChoiceBox loadFreq;

    @FXML private ChoiceBox saveOption;

    @FXML private TextField nameOfSource;

    @FXML private TextArea sourceDescr;

    @FXML private TextField databaseName;

    @FXML private TextField type;

    @FXML private TextArea parsing;

    @FXML private TextArea httpRequestParams;

    @FXML private TextField filePathTextField;

    @FXML private Label labelBrowse;

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

    public void setCollectionName(String collectionName) {
        this.collectionName.setText(collectionName);
    }

    public void setLoadFreq(String loadFreq) {
        switch (loadFreq) {
            case "Online":
                this.loadFreq.setValue("Online");
                break;
            case "Daily":
                this.loadFreq.setValue("Online");
                break;
            case "Weekly":
                this.loadFreq.setValue("Online");
                break;
            case "Monthly":
                this.loadFreq.setValue("Online");
                break;
        }
    }

    public void setSaveOption(ChoiceBox saveOption) {
        this.saveOption = saveOption;
    }

    public void setSourceDescr(String sourceDescr) {
        this.sourceDescr.setText(sourceDescr);
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName.setText(databaseName);
    }

    public void setHttpRequestParams(TextArea httpRequestParams) {
        this.httpRequestParams = httpRequestParams;
    }

    public void setUrlField(String urlField) {
        this.filePathTextField.setText(urlField);
    }

    public Step2Controller() {
        arangoDbManager = new ArangoDbManager();
    }

    public void onSaveButton() throws Exception {
        setAtributes();
        arangoDbManager.createDocument(currentConnection.getTgt_db(),
                currentConnection.getTgt_collection(),currentConnection);

        prevStage.close();
    }

    @FXML
    public void onBrowseButton() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
            fileChooser.setTitle("Choose file");
            pathToFile = fileChooser.showOpenDialog(prevStage).getAbsolutePath();
        }
        catch (NullPointerException ex){}
        filePathTextField.setText(pathToFile);
    }


    public void createFileSource() {
        FileInFileSystem source = null;
        try {
            source = new FileInFileSystem(currentConnection, pathToFile);
            parseFile(source);
            parseFile(source);
        }
        catch (NullPointerException ex) {}
    }

    public void createRestSource() {
        currentConnection.setRest_ds(new RestDs("url", parseHttpRequestParams(), "type"));
        Rest rest = new Rest(currentConnection.getRest_ds());
        String result = rest.testConnection("GET");
    }

    public void createJDBCSource() {
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        paneCollectionName.setVisible(false);
        paneDatabaseName.setVisible(false);

        saveOption.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                switch (saveOption.getValue().toString()) {
                    case "Save to disc":
                        paneCollectionName.setVisible(true);
                        paneDatabaseName.setVisible(true);
                        break;
                    case "Save to database":
                        paneCollectionName.setVisible(false);
                        paneDatabaseName.setVisible(false);
                }
            }
        });

    }

    @FXML public void onLoadButton(){

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
        List<String[]> ol = file.getRs();
        parsing.setWrapText(true);
        //parsing.setText(ol.toString());
        Iterator<String[]> iterator = ol.iterator();
        while (iterator.hasNext()){
            String joinedString = Arrays.toString(iterator.next());
            parsing.appendText("\n" + joinedString);
        }
    }

    private void parseRest(Rest rest){

    }

    private List<DsParams> parseHttpRequestParams(){

        List<DsParams> dsParams = new ArrayList<>();
        ObservableList ol = httpRequestParams.getParagraphs();
        Iterator<CharSequence> iterator = ol.iterator();
        while (iterator.hasNext()){
            String temp = iterator.next().toString();
            String[] seq = temp.split("=");
            DsParams ds = new DsParams(seq[0], seq[1], TypeDef.defType(seq[1]), false);
            dsParams.add(ds);
        }

        return dsParams;
    }

    public void hidePane(){
        labelBrowse.setText("Choose file in filesystem");
        optionsPane.setVisible(false);
        autorizationTab.setDisable(true);
        step2_pane.setPrefHeight(370);
        parsing.setPrefHeight(240);
        parsingArea.setPrefHeight(240);
    }

}
