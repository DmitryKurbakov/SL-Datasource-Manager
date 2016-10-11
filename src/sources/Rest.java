package sources;

import data_object.DsParams;
import data_object.RestDs;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Rest {

    public RestDs getRestDs() {
        return restDs;
    }

    public void setRestDs(RestDs restDs) {
        this.restDs = restDs;
    }

    private RestDs restDs;

    public Rest(RestDs restDs) {
        this.restDs = restDs;
    }

    public String testConnection() {
        switch (restDs.getRest_base_url()) {
            case "GET":
                return createGetRequest();
            case "POST":
                return createPostRequest();
            default:
                return "";
        }
    }

    private String getResponse(HttpURLConnection conn) throws IOException {

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

    private String createGetRequest() {

        URL requestUrl;
        String response = "";

        try {
            if (restDs.getDs_params() == null) {
                requestUrl = new URL(restDs.getRest_base_url());
            } else {
                requestUrl = new URL(restDs.getRest_base_url() + "?" + formatList2StringParams());
            }

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

    private String createPostRequest() {

        URL requestUrl;
        String response = "";

        try {
            requestUrl = new URL(restDs.getRest_base_url());

            HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            if (restDs.getDs_params() != null) {
                writer.write(formatList2StringParams());
            } else {
                writer.write("");
            }

            writer.flush();
            writer.close();
            os.close();

            response = getResponse(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private String formatList2StringParams() throws UnsupportedEncodingException {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (DsParams dsParam : restDs.getDs_params()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(dsParam.getParam_name(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(dsParam.getParam_dvalue(), "UTF-8"));
        }

        return result.toString();
    }
}