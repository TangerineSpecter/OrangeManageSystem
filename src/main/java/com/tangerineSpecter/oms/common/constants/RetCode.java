package com.tangerinespecter.oms.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

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
    public static final RetCode SUCCESS = new RetCode(0, "操作成功");

    /**
     * 失败
     */
    public static final RetCode FAIL = new RetCode(1, "操作失败");
    /**
     * 操作频繁
     */
    public static final RetCode BUSY = new RetCode(2, "操作频繁");

    /**
     * 帐号已被注册
     */
    public static final RetCode REGISTER_REPEAT = new RetCode(100, "该帐号已注册，请重新申请！");

    /**
     * 帐号密码错误
     */
    public static final RetCode ACCOUNTS_PASSWORD_ERROR = new RetCode(101, "密码错误，请重新确认后登录！");

    /**
     * 注册帐号不存在
     */
    public static final RetCode REGISTER_ACCOUNTS_NOT_EXIST = new RetCode(102, "您输入的帐号不存在，请注册后重新登录！");

    /**
     * 帐号不存在
     */
    public static final RetCode ACCOUNTS_NOT_EXIST = new RetCode(102, "帐号不存在！");

    /**
     * 系统菜单不存在
     */
    public static final RetCode SYSTEM_MENU_NOT_EXIST = new RetCode(103, "系统菜单不存在！");

    /**
     * 菜单下存在子级菜单
     */
    public static final RetCode SYSTEM_MENU_CHILD_EXIST = new RetCode(104, "菜单下存在子级菜单，不能删除！");
    /**
     * 登录超时
     */
    public static final RetCode LOGIN_TIMEOUT = new RetCode(105, "登录超时，请重新进行登录！");

    /**
     * 参数错误
     */
    public static final RetCode PARAM_ERROR = new RetCode(200, "参数错误");

    public RetCode fillArgs(Object... args) {
        int errorCode = this.errorCode;
        String errorDesc = this.errorDesc + ":" + Arrays.toString(args);
        return new RetCode(errorCode, errorDesc);
    }
}
