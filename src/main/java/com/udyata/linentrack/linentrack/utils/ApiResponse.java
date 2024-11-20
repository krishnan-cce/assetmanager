package com.udyata.linentrack.linentrack.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T>{
    private boolean Status;
    private int StatusCode;
    private T Data;
    private String Message;
}

