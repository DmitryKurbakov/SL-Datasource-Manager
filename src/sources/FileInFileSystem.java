package sources;


import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import helpers.ColumnsPopulate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import data_object.DataSource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static com.sun.org.apache.xalan.internal.lib.ExsltStrings.split;

public class FileInFileSystem {

    ObservableList rowset;
    DataSource source;
    String filepath;
    Boolean hasHeader;

    public DataSource getSource() {
        return this.source;
    }


    public FileInFileSystem(String filepath){
//        source = new DataSource("File");

        this.filepath = filepath;
        source.getFile_ds().setFile_type(getFileType());
        testConnection();
    }

    public void saveConnection() {
    }

    public ObservableList testConnection() {
        try {
            CSVReader reader = new CSVReader(new FileReader(filepath));

            List<String[]> rs = reader.readAll();
            rowset = FXCollections.observableArrayList(rs);

            hasHeader = isExistHeader(rs);
            CSVParser s = reader.getParser();

            ColumnsPopulate cp = new ColumnsPopulate(rs, hasHeader);
            source.getFile_ds().setFile_delimeter(new String(new char[]{s.getSeparator()}));
            source.getFile_ds().setFile_header(hasHeader);
            source.getFile_ds().setFile_columns(cp.getFileColumns());
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }

        return rowset;
    }

    private String getFileType() {
        String[] ls = filepath.split("\\.");
        return ls[ls.length - 1];
    }

    private boolean isExistHeader(List<String[]> rs) {
        String[] a = rs.get(0);

        try {
            Integer.parseInt(a[0]);
            return false;
        } catch (ParseException ex) {
        }
        return true;
    }

    private String[] columnsName(List<String[]> rs) {
        return rs.get(0);
    }


}//End of class
