package com.tangerinespecter.oms.common.constant;

/**
 * @author TangerineSpecter
 */
public class RetCode {

    /**
     * 成功
     */
    public static final Integer SUCCESS_CODE = 0;
    public static final String SUCCESS_CODE_DESC = "成功";

    /**
     * 失败
     */
    public static final Integer FAIL_CODE = 1;
    public static final String FAIL_CODE_DESC = "失败";

    /**
     * 帐号已被注册
     */
    public static final Integer REGISTER_REPEAT_CODE = 100;
    public static final String REGISTER_REPEAT_CODE_DESC = "该帐号已注册，请重新申请！";

    /**
     * 帐号密码错误
     */
    public static final Integer ACCOUNTS_PASSWORD_ERROR_CODE = 101;
    public static final String ACCOUNTS_PASSWORD_ERROR_CODE_DESC = "密码错误，请重新确认后登录！";

    /**
     * 帐号不存在
     */
    public static final Integer ACCOUNTS_NOT_EXIST_CODE = 102;
    public static final String ACCOUNTS_NOT_EXIST_CODE_DESC = "您输入的帐号不存在，请注册后重新登录！";
}
