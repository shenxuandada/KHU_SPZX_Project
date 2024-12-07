package com.atguigu.spzx.common.exception;

import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;
import org.checkerframework.common.returnsreceiver.qual.This;

/**
 * ClassName: GuiguException
 * Description:
 *
 * @Author Refactoring
 * Create 2024/7/30 下午5:19
 * Version 1.8
 */

@Data
public class GuiguException extends  RuntimeException{
    private Integer code;

    private String message;

    private ResultCodeEnum resultCodeEnum;

    public GuiguException(ResultCodeEnum resultCodeEnum){
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

}
