package com.tangerinespecter.oms.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author TangerineSpecter
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RetCode {

    private int errorCode;
    private String errorDesc;

    /**
     * 成功
     */
    public static final RetCode SUCCESS = new RetCode(0, "成功");

    /**
     * 失败
     */
    public static final RetCode FAIL = new RetCode(1, "失败");

    /**
     * 帐号已被注册
     */
    public static final RetCode REGISTER_REPEAT = new RetCode(100, "该帐号已注册，请重新申请！");

    /**
     * 帐号密码错误
     */
    public static final RetCode ACCOUNTS_PASSWORD_ERROR = new RetCode(101, "密码错误，请重新确认后登录！");

    /**
     * 帐号不存在
     */
    public static final RetCode ACCOUNTS_NOT_EXIST = new RetCode(102, "您输入的帐号不存在，请注册后重新登录！");

    /**
     * 参数错误
     */
    public static final RetCode PARAM_ERROR = new RetCode(200, "参数错误");
}
