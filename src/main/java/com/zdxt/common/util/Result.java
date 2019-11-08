package com.zdxt.common.util;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Rockly on 2019/7/29 16:14.
 */

@Data
public class Result<T> implements Serializable {

    private int reultCode;
    private String message;
    private T data;


    public Result() {
    }

    public Result(int reultCode, String message) {
        this.reultCode = reultCode;
        this.message = message;
    }



}
