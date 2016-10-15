package gui;

import com.arangodb.ArangoException;
import helpers.ArangoDbManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.conn.HttpHostConnectException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.ResourceBundle;

public class AutorizationController implements Initializable {

    @FXML
    private AnchorPane autorizationPane;
    @FXML
    private TextField user;
    @FXML
    private PasswordField password;

    private Stage primaryStage;

    @FXML
    public void onSignInButton() throws IOException {

        ArangoDbManager.User = user.getText();
        ArangoDbManager.Password = password.getText();

        ArangoDbManager arangoDbManager = new ArangoDbManager();

        try {
            arangoDbManager.tryConnect();
        } catch (ArangoException e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);

            if (e.getErrorMessage() == "Unauthorized") {
                alert.setContentText("Login or password are incorrect");
            } else {
                alert.setContentText("Database is unavailable");
            }

            alert.showAndWait();

            return;
        }

        try {
            //Создаем объект свойст
            Properties properties = new Properties();
            File file = new File("properties.properties");
            //Загружаем свойства из файла
            properties.load(new FileInputStream(file));
            //Устанавливаем значение свойста
            properties.setProperty("user", user.getText());
            properties.setProperty("password", password.getText());
            //Сохраняем свойства в файл.
            properties.store(new FileOutputStream(file), null);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
    public void initialize(URL arg0, ResourceBundle arg1) {
    }
}
