package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AutorizationController implements Initializable{

    @FXML private AnchorPane autorizationPane;
    @FXML private TextField user;
    @FXML private PasswordField password;

    private Stage prevStage;
    private Stage primaryStage;

    @FXML
    public void onSignInButton() throws IOException{
        primaryStage = new Stage();
        primaryStage.setTitle("SL Datasource Manager");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_form.fxml"));
        Pane pane = loader.load();
        MainController sc = loader.getController();
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        Stage st = (Stage) autorizationPane.getScene().getWindow();
        st.close();
        primaryStage.show();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {}
}
