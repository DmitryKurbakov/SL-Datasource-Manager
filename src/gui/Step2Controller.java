package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sources.FileInFileSystem;

import java.net.URL;
import java.util.ResourceBundle;

public class Step2Controller implements Initializable{

    private static Stage prevStage;
    private static Stage primaryStage;

    private String name;
    private String sv;
    private String filename;
    private FileInFileSystem source;

    public String getSv(){
        return this.sv;
    }
    public void setSv(String sv){
        this.sv = sv;
    }

    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }

    public void setName(String name) { this.name = name; }

    @FXML public TextField nameOfSource;
    @FXML private AnchorPane step2_pane;
    @FXML private AnchorPane table;
    @FXML private TextArea sourceDescr;
    @FXML private TextField databaseName;

    public Step2Controller(){
    }

    public void onNextButton() throws Exception{

    }

    @FXML public void onLoadButton(){

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
            fileChooser.setTitle("Choose file");
            this.filename = fileChooser.showOpenDialog(prevStage).getAbsolutePath();
        }

        catch (NullPointerException ex){}

        if (sv.equals("File")) {createFileSource();}
        else if (sv.equals("REST")) {createRestSource();}
        else if (sv.equals("JDBC")) {createJDBCSource();}
    }

    public void createFileSource(){

        source = new FileInFileSystem(this.filename);

        source.getSource().setName(this.name);
        //ObservableList<StringProperty> v = (ObservableList<StringProperty>) source.testConnection();
        //table_view.getItems().addAll(v);

    }
    public void createRestSource(){}
    public void createJDBCSource(){}

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){}

    private void setAtributes(){
        source.getSource().setDesc(sourceDescr.getText());
        source.getSource().setTgt_db(databaseName.getText());
    }

}//end of class
