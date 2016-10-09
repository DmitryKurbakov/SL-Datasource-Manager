package sources;

import data_object.DataSource;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Rest {

    DataSource source;
    private String requestType;
    private String url;
    private HashMap<String, String> requestParams;

    public Rest(String url, HashMap<String, String> requestParams, String requestType) {
        this.url = url;
        this.requestParams = requestParams;
        this.requestType = requestType;
        source = new DataSource("Rest");
    }

    public DataSource getSource() {
        return source;
    }

    public void setSource(DataSource source) {
        this.source = source;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, String> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(HashMap<String, String> requestParams) {
        this.requestParams = requestParams;
    }

    public String testConnection() {
        switch (requestType) {
            case "GET":
                return createGetRequest();
            case "POST":
                return createPostRequest();
            default:
                return "";
        }
    }

    public String getResponse(HttpURLConnection conn) throws IOException {

        int responseCode = conn.getResponseCode();
        String response = "";

        if (responseCode == HttpsURLConnection.HTTP_OK) {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = br.readLine()) != null) {
                response += line;
            }
        }

        return response;
    }

    public String createGetRequest() {

        URL requestUrl;
        String response = "";

        try {
            requestUrl = new URL(url + "?" + getPostDataString());

            HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");

            response = getResponse(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public String createPostRequest() {

        URL requestUrl;
        String response = "";

        try {
            requestUrl = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString());

            writer.flush();
            writer.close();
            os.close();

            response = getResponse(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getPostDataString() throws UnsupportedEncodingException {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}