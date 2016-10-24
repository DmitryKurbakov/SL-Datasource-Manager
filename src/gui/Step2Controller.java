package gui;

import org.codehaus.jackson.map.ObjectMapper;
import dataobject.DataSource;
import dataobject.DsParams;
import dataobject.FileDS;
import helpers.ArangoDbManager;
import helpers.TypeDef;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class Step2Controller implements Initializable {


    private static Stage prevStage;
    private static Stage primaryStage;
    public TextArea logConsole;

    ArangoDbManager arangoDbManager;
    private String pathToFile;
    private boolean isUpdate;

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    private DataSource currentConnection;

    @FXML
    private AnchorPane step2_pane;

    @FXML
    private AnchorPane paneCollectionName;

    @FXML
    private AnchorPane paneDatabaseName;

    @FXML
    private AnchorPane optionsPane;

    @FXML
    private AnchorPane parsingArea;

    @FXML
    private Tab autorizationTab;

    @FXML
    private TextField collectionName;

    @FXML
    private ChoiceBox loadFreq;

    @FXML
    private ChoiceBox saveOption;

    @FXML
    private TextField nameOfSource;

    @FXML
    private TextArea sourceDescr;

    @FXML
    private TextField databaseName;

    @FXML
    private TextField type;

    @FXML
    private TextArea parsing;

    @FXML
    private TextArea httpRequestParams;

    @FXML
    private Label labelBrowse;

    @FXML
    private Button saveButton;

    @FXML
    private Button browseButton;

    @FXML
    private TextField urlField;

    public void setHttpRequestParams(String httpRequestParams) {
        this.httpRequestParams.setText(httpRequestParams);
    }

    public DataSource getCurrentConnection() {
        return currentConnection;
    }

    void setCurrentConnection(DataSource currentConnection) {
        this.currentConnection = currentConnection;
    }

    void setPrevStage(Stage prevStage) {
        Step2Controller.prevStage = prevStage;
    }

    void setNameOfSource(String nameOfSource) {
        this.nameOfSource.setText(nameOfSource);
    }

    void setType(String type) {
        this.type.setText(type);
    }

    void setCollectionName(String collectionName) {
        this.collectionName.setText(collectionName);
    }

    void setLoadFreq(String loadFreq) {
        switch (loadFreq) {
            case "Online":
                this.loadFreq.setValue("Online");
                break;
            case "Daily":
                this.loadFreq.setValue("Daily");
                break;
            case "Weekly":
                this.loadFreq.setValue("Weekly");
                break;
            case "Monthly":
                this.loadFreq.setValue("Monthly");
                break;
        }
    }

    public void setSaveOption(ChoiceBox saveOption) {
        this.saveOption = saveOption;
    }

    void setSourceDescr(String sourceDescr) {
        this.sourceDescr.setText(sourceDescr);
    }

    void setDatabaseName(String databaseName) {
        this.databaseName.setText(databaseName);
    }

    public void setUrlField(String urlField) {
        this.urlField.setText(urlField);
    }

    public Step2Controller() {
        arangoDbManager = new ArangoDbManager();
    }

    public void onSaveButton() throws Exception {

        if (nameOfSource.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You should choose filename");

            alert.showAndWait();
            saveButton.setDisable(true);
            return;
        }

        setAttributes();

        if (saveOption.getValue().equals("Save to disc")) {
            saveFile();
            logConsole.appendText("Data was successfully written on disc\n");
            return;
        } else if (!isUpdate) {
            if (arangoDbManager.createDocument(currentConnection.getTgt_db(),
                    currentConnection.getTgt_collection(), currentConnection)) {
                logConsole.appendText("Data was successfully saved to database\n");
            } else {
                logConsole.appendText("Could not save data to database\n");
            }
        } else {
            currentConnection.setUpdated_by(ArangoDbManager.User);
            if (arangoDbManager.updateDocument(currentConnection.getTgt_db(), currentConnection.getTgt_collection(),
                    currentConnection, currentConnection.getKey())) {
                logConsole.appendText("Data was successfully updated in database\n");
            } else {
                logConsole.appendText("Could not update data in database\n");
            }
        }

        prevStage.close();
    }

    @FXML
    public void onBrowseButton() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
            fileChooser.setTitle("Choose file");
            pathToFile = fileChooser.showOpenDialog(prevStage).getAbsolutePath();
        } catch (NullPointerException ignored) {
        }
        urlField.setText(pathToFile);
    }


    private void createFileSource() {
        FileInFileSystem source = null;
        currentConnection.setFile_ds(new FileDS());
        try {
            source = new FileInFileSystem(currentConnection, pathToFile);
            parseFile(source);
            saveButton.setDisable(false);
        } catch (NullPointerException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You should choose file");
            alert.showAndWait();

            saveButton.setDisable(true);
            return;
        }
    }

    public void createRestSource() {
        saveButton.setDisable(false);
        if (urlField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You should enter valid URL");
            alert.showAndWait();

            saveButton.setDisable(true);
            return;
        }

        Rest rest = new Rest(currentConnection, urlField.getText(), parseHttpRequestParams());
        String result = rest.testConnection("GET");
        parseRest(result);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        paneCollectionName.setVisible(false);
        paneDatabaseName.setVisible(false);

        saveOption.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            switch (saveOption.getValue().toString()) {
                case "Save to disc":
                    paneCollectionName.setVisible(true);
                    paneDatabaseName.setVisible(true);
                    break;
                case "Save to database":
                    paneCollectionName.setVisible(false);
                    paneDatabaseName.setVisible(false);
            }
        });

    }

    @FXML
    public void onLoadButton() {

        saveButton.setDisable(false);

        switch (currentConnection.getDs_type()) {
            case "File":
                createFileSource();
                break;
            case "REST":
                createRestSource();
        }

    }

    private void setAttributes() {
        currentConnection.setName(nameOfSource.getText());
        currentConnection.setDesc(sourceDescr.getText());
        currentConnection.setTgt_db(databaseName.getText());
        currentConnection.setTgt_collection(collectionName.getText());
        currentConnection.setTgt_load_freq(loadFreq.getValue().toString());
    }

    @FXML
    public void onPreviousButton() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Step 1");
        Pane myPane = FXMLLoader.load(getClass().getResource("step1.fxml"));
        Scene scene = new Scene(myPane);
        stage.setScene(scene);
        prevStage.close();
        stage.show();
    }

    @FXML
    public void onExitButton() {
        prevStage.close();
        if (isUpdate) {
            logConsole.appendText("Updating new source was canceled\n");
        } else {
            logConsole.appendText("Creating new source was canceled\n");
        }
    }

    private void parseFile(FileInFileSystem file) {
        List<String[]> ol = file.getRs();
        parsing.setWrapText(true);
        for (String[] anOl : ol) {
            String joinedString = Arrays.toString(anOl);
            parsing.appendText("\n" + joinedString);
        }
    }

    private void parseRest(String result) {
        parsing.setWrapText(true);
        parsing.setText(result);
    }

    private List<DsParams> parseHttpRequestParams() {

        List<DsParams> dsParams = new ArrayList<>();
        ObservableList ol = httpRequestParams.getParagraphs();
        for (Object anOl : ol) {
            String temp = anOl.toString();
            String[] seq = temp.split("=");
            DsParams ds = new DsParams(seq[0], seq[1], TypeDef.defType(seq[1]), false);
            dsParams.add(ds);
        }

        return dsParams;
    }

    void hidePane() {
        labelBrowse.setText("Choose file in filesystem");
        optionsPane.setVisible(false);
        autorizationTab.setDisable(true);
        step2_pane.setPrefHeight(370);
        parsing.setPrefHeight(240);
        parsingArea.setPrefHeight(240);
    }

    public void setBrowseButtonDisabled() {
        browseButton.setVisible(false);
    }

    private void saveFile() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(prevStage);

        if (file == null) {
            return;
        } else ser(file);
        prevStage.close();
    }

    private void saveDatabase() {

        if (!isUpdate) {
            arangoDbManager.createDocument(currentConnection.getTgt_db(), currentConnection.getTgt_collection(),
                    currentConnection);

        } else {
            currentConnection.setUpdated_by(ArangoDbManager.User);
            currentConnection.setUpdated(new SimpleDateFormat("dd.MM.yyyy hh:mm").format(new Date()));

            arangoDbManager.updateDocument(currentConnection.getTgt_db(), currentConnection.getTgt_collection(),
                    currentConnection, currentConnection.getKey());
        }
    }

    public void ser(File file) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, currentConnection);
        } catch (Exception ex) {
        }
    }

    public void setSaveButtonDisable(Boolean flag) {
        saveButton.setDisable(flag);
    }

    void setDatabasePropertiesChangingsDisable() {
        collectionName.setEditable(false);
        databaseName.setEditable(false);
    }
}


