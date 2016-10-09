package logic;

import java.io.File;

public class FileDS {

    private String file_type;
    private String file_delimeter;
    private Boolean file_header;
    private FileColumns[] file_columns;

    public void setFile_type(String file_type){
        this.file_type = file_type;
    }

    public String getFile_type(){
        return file_type;
    }

    public void setFile_delimeter(String file_delimeter){
        this.file_delimeter = file_delimeter;
    }

    public String getFile_delimeter(){
        return file_delimeter;
    }

    public void setFile_header(Boolean file_header){
        this.file_header = file_header;
    }

    public boolean getFile_header(){return this.file_header; }

    public void setFile_columns(FileColumns[] file_columns){ this.file_columns = file_columns;}

    public FileDS(){

    }
}
