package dataobject;

import java.util.List;

public class RestDs {

    private String rest_base_url;
    private List<DsParams> ds_params;
    private String rest_result_type;

    public String getRest_base_url() {
        return rest_base_url;
    }

    public List<DsParams> getDs_params() {
        return ds_params;
    }

    public String getRest_result_type() {
        return rest_result_type;
    }

    public RestDs(String rest_base_url, List<DsParams> ds_params, String rest_result_type) {
        this.rest_base_url = rest_base_url;
        this.ds_params = ds_params;
        this.rest_result_type = rest_result_type;
    }
}
