package com.zdxt.common.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Rockly on 2019/7/29 16:14.
 */

@Data
@ApiModel(value = "基础返回类",description = "基础返回类")
public class Result<T> implements Serializable {

    @ApiModelProperty(example = "200")
    private int resultCode;
    @ApiModelProperty(example = "SUCCESS")
    private String message;

    private T data;


    public Result() {
    }

    public Result(int reultCode, String message) {
        this.resultCode = reultCode;
        this.message = message;
    }



}
