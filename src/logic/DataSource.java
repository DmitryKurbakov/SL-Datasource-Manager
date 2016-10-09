package logic;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataSource {

    private SimpleDateFormat created = new SimpleDateFormat("dd.MM.yyyy hh:mm"); //Date date = new Date(); curent date
    private SimpleDateFormat updated = new SimpleDateFormat("dd.MM.yyyy hh:mm");
    private TgtLoadSchedule[] tgt_load_schedule;
    private DsAuth ds_auth;
    private ProcessingParams processing_params;
    private WebDs web_ds;
    private RestDs rest_ds;
    private FileDS file_ds;
    private String created_by;
    private String updated_by;
    private String name;
    private String desc;
    private String ds_type;
    private String tgt_db;
    private String tgt_collection;
    private String tgt_load_freq;
    private String processing_type;

    public FileDS getFile_ds(){
        return file_ds;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() { return this.name; }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public void setTgt_db(String tgt_db){ this.tgt_db = tgt_db; }

    public DataSource(String ds_type){
        this.ds_type = ds_type;
        created.format(new Date());
        defType(ds_type);
    }

    private void defType(String type){
        if (type.equals("File")){
            file_ds = new FileDS();
        }
    }

}//end of class
