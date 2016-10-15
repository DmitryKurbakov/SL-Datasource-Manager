package dataobject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataSource {

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;
    private String created;
    private String updated;
    private TgtLoadSchedule[] tgt_load_schedule;
    private DsAuth ds_auth;
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

    public FileDS getFile_ds() {
        return file_ds;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDs_type(String ds_type) {
        this.ds_type = ds_type;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setTgt_db(String tgt_db) {
        this.tgt_db = tgt_db;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public void setCreated(String created) {
        this.created = created;
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

    public DataSource(String ds_type, String name, String created_by) {

        this.ds_type = ds_type;
        this.name = name;
        created = new SimpleDateFormat("dd.MM.yyyy hh:mm").format(new Date());
        updated = new SimpleDateFormat("dd.MM.yyyy hh:mm").format(new Date());
        this.created_by = created_by;
        updated_by = created_by;
    }
}
