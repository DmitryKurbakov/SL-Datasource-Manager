package helpers;

public class TypeDef {

    static public String defType(String str){
        String type = null;
        if (str.toLowerCase().equals("true") || (str.toLowerCase().equals("false"))) {type = "Boolean"; }
        if (str.toLowerCase().equals("y") || (str.toLowerCase().equals("n"))) {type = "Boolean String"; }
        if (str.toLowerCase().equals("1") || (str.toLowerCase().equals("0"))) {type = "Boolean Numeric"; }
        try {
            Integer.parseInt(str);
            type = "Integer";
        }
        catch (Exception pe){
            try {
                Double.parseDouble(str);
                type = "Number";
            }
            catch (Exception pex){
                type = "String";
            }
        }
        return type;
    }

}

