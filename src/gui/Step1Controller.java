package gui;

import dataobject.DataSource;
import helpers.ArangoDbManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Step1Controller implements Initializable {

    private static Stage prevStage;
    private static Stage primaryStage;
    public TextArea logConsole;

    @FXML
    private AnchorPane step1_pane;

    @FXML
    private TextField name;

    @FXML
    private ChoiceBox ds_type;

    @FXML
    private Button previous_button;

    @FXML
    private Button next_button;

    @FXML
    private Button exit_button;

    public void setPrevStage(Stage stage) {
        prevStage = stage;
    }

    //TODO: step1 over previous pane
    public Step1Controller() {
    }


    public void onNextButton(ActionEvent event) throws Exception {

        if (name.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You should enter name of datasource");

            alert.showAndWait();
            return;
        }

        DataSource newConnection = new DataSource(ds_type.getValue().toString(), name.getText(), ArangoDbManager.User);

        primaryStage = new Stage();
        primaryStage.setTitle("Step 2");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("step2.fxml"));
        Pane pane = loader.load();
        Step2Controller sc = loader.getController();
        sc.setPrevStage(primaryStage);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        Stage st = (Stage) step1_pane.getScene().getWindow();
        st.close();

        sc.setNameOfSource(name.getText());
        sc.setType(ds_type.getValue().toString());
        sc.setCurrentConnection(newConnection);
        sc.setUpdate(false);
        sc.logConsole = logConsole;

        if (ds_type.getValue().equals("File")) {
            logConsole.appendText("Creating new file source\n");
            sc.hidePane();
        }
        if (ds_type.getValue().equals("REST")) {
            logConsole.appendText("Creating new rest source\n");
            sc.setBrowseButtonDisabled();
        }


        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(true);

        primaryStage.show();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    @FXML
    public void onCancelButton() {
        prevStage.close();
        logConsole.appendText("Creating new source was canceled\n");
    }

}
