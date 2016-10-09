package data_object;

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

    public String getName() {
        return this.name;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public void setTgt_db(String tgt_db){
        this.tgt_db = tgt_db;
    }

    public SimpleDateFormat getCreated() {
        return created;
    }

    public void setCreated(SimpleDateFormat created) {
        this.created = created;
    }

    public SimpleDateFormat getUpdated() {
        return updated;
    }

    public void setUpdated(SimpleDateFormat updated) {
        this.updated = updated;
    }

    public TgtLoadSchedule[] getTgt_load_schedule() {
        return tgt_load_schedule;
    }

    public void setTgt_load_schedule(TgtLoadSchedule[] tgt_load_schedule) {
        this.tgt_load_schedule = tgt_load_schedule;
    }

    public DsAuth getDs_auth() {
        return ds_auth;
    }

    public void setDs_auth(DsAuth ds_auth) {
        this.ds_auth = ds_auth;
    }

    public ProcessingParams getProcessing_params() {
        return processing_params;
    }

    public void setProcessing_params(ProcessingParams processing_params) {
        this.processing_params = processing_params;
    }

    public WebDs getWeb_ds() {
        return web_ds;
    }

    public void setWeb_ds(WebDs web_ds) {
        this.web_ds = web_ds;
    }

    public RestDs getRest_ds() {
        return rest_ds;
    }

    public void setRest_ds(RestDs rest_ds) {
        this.rest_ds = rest_ds;
    }

    public void setFile_ds(FileDS file_ds) {
        this.file_ds = file_ds;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getDesc() {
        return desc;
    }

    public String getDs_type() {
        return ds_type;
    }

    public void setDs_type(String ds_type) {
        this.ds_type = ds_type;
    }

    public String getTgt_db() {
        return tgt_db;
    }

    public String getTgt_collection() {
        return tgt_collection;
    }

    public void setTgt_collection(String tgt_collection) {
        this.tgt_collection = tgt_collection;
    }

    public String getTgt_load_freq() {
        return tgt_load_freq;
    }

    public void setTgt_load_freq(String tgt_load_freq) {
        this.tgt_load_freq = tgt_load_freq;
    }

    public String getProcessing_type() {
        return processing_type;
    }

    public void setProcessing_type(String processing_type) {
        this.processing_type = processing_type;
    }

    public DataSource(String ds_type){
        this.ds_type = ds_type;
        created.format(new Date());
        defType(ds_type);
    }

    private void defType(String type){

        if (type.equals("File")){
            file_ds = new FileDS();
        }

        if (type.equals("Rest")){
//            rest_ds = new RestDs();
        }
    }
}//end of class
