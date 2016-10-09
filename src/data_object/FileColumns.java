package data_object;

public class FileColumns {

    private String column_name;
    private String column_type;
    private Integer column_length;

    public void setColumn_name(String column_name){
        this.column_name = column_name;
    }
    public void setColumn_type(String column_type) { this.column_type = column_type; }
    public void setColumn_length(Integer column_length) { this.column_length = column_length; }

    public FileColumns(){

    }
}
