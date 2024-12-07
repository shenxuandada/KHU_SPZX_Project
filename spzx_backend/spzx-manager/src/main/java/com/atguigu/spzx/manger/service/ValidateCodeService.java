package com.atguigu.spzx.manger.service;

import com.atguigu.spzx.model.vo.system.ValidateCodeVo;

/**
 * ClassName: ValidateCodeService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/7/30 下午6:08
 * Version 1.8
 */
public interface ValidateCodeService {

    //生成图片验证码
    ValidateCodeVo generateValidateCode();
}
