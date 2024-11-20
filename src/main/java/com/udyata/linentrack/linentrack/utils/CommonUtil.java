package com.udyata.linentrack.linentrack.utils;

public class CommonUtil {

    public static String geneNextTransNo(String transNumber){
        String strId = String.valueOf(Integer.parseInt(transNumber) + 1);
        String strZeros = "";
        for(int i =0;i<transNumber.length() - strId.length();i++){
            strZeros = strZeros + '0';
        }
        strId = strZeros + strId;
        return strId;
    }

    public static String LEVEL_1_ADD = "LEVEL_1_ADD";
    public static String LEVEL_1_UPDATE = "LEVEL_1_UPDATE";
    public static String QA_FINISH = "QA";

}
