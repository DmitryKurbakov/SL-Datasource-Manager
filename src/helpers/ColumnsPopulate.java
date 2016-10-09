package helpers;


import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import logic.FileColumns;

import java.util.List;

public class ColumnsPopulate {

    private Boolean hasHeader;
    private List<String[]> ls;
    private String[] header;
    private String[] typesOfColumns;
    private Integer[] length;

    public ColumnsPopulate(List<String[]> ls, Boolean hasHeader){
        this.ls = ls;
        this.hasHeader = hasHeader;
        if (hasHeader)populateHeader();
        determinateType();
        determinateLength();
    }

    private void populateHeader(){
        header = ls.get(0);
    }

    private void determinateType(){
        String[] temp = ls.get(1);
        String[] types = new String[temp.length];
        for (int i = 0; i < temp.length; ++i) {
            if (temp[i].toLowerCase().equals("true") || (temp[i].toLowerCase().equals("false"))) {types[i] = "Boolean"; continue;}
            if (temp[i].toLowerCase().equals("y") || (temp[i].toLowerCase().equals("n"))) {types[i] = "Boolean String"; continue;}
            if (temp[i].toLowerCase().equals("1") || (temp[i].toLowerCase().equals("0"))) {types[i] = "Boolean Numeric"; continue;}
            try {
                Integer.parseInt(temp[i]);
                types[i] = "Integer";
                continue;
            }
            catch (Exception pe){
                try {
                    Double.parseDouble(temp[i]);
                    types[i] = "Number";
                    continue;
                }
                catch (Exception pex){
                    types[i] = "String";
                }
            }
        }

        typesOfColumns = types;
    }

    private void determinateLength(){
        int max = 0;
        int i = 0;
        Integer[] maxArr = new Integer[ls.get(0).length];
        for (int j = 0; j < maxArr.length; j++) {
            maxArr[j] = 0;
        }
        while (i < ls.size()){
            String[] temp = ls.get(i);
            for (int j = 0; j < temp.length; j++) {
                if (temp[j].length() > maxArr[j]){
                    maxArr[j] = temp[j].length();
                }
            }
            ++i;
        }
        length = maxArr;
    }

    public FileColumns[] getFileColumns(){
        FileColumns[] fc = new FileColumns[ls.get(0).length];
        for (int i = 0; i < fc.length; i++){
            fc[i] = new FileColumns();
            fc[i].setColumn_type(typesOfColumns[i]);
            fc[i].setColumn_length(length[i]);
            if (hasHeader) fc[i].setColumn_name(header[i]);
        }
        return fc;
    }

}//end of class
