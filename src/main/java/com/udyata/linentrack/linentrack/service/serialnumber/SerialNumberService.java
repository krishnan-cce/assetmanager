package com.udyata.linentrack.linentrack.service.serialnumber;

public interface SerialNumberService {
    String[] generateNextSerialNumber(String formName);
    void updateSerialNumber(String formName, String nextSerialNumber);
}
