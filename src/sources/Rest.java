package sources;

import dataobject.DataSource;
import dataobject.DsParams;
import dataobject.RestDs;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.crypto.Data;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class Rest {

    private DataSource dataSource;
    private String url;
    private List<DsParams> params;

    public Rest(DataSource dataSource, String url, List<DsParams> params) {
        this.dataSource = dataSource;
        this.url = url;
        this.params = params;
    }

    public String testConnection(String requestType) {
        switch (requestType) {
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
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while ((line = br.readLine()) != null) {
                response += line;
            }

            dataSource.setRest_ds(new RestDs(url, params, conn.getHeaderField("Content-type")));
        }

        return response;
    }

    private String createGetRequest() {

        URL requestUrl;
        String response = "";

        try {
            if (params == null || params.size() == 0) {
                requestUrl = new URL(url);
            } else {
                requestUrl = new URL(url + "?" + formatList2StringParams());
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
            requestUrl = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            if (params != null && params.size() != 0) {
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

        for (DsParams dsParam : params) {
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