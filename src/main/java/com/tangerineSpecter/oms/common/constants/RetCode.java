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
     * 同一层级下存在相同名称的部门
     */
    public static final RetCode DEPT_NAME_EXIST_ON_LEVEL = new RetCode(106, "同一层级下存在相同名称的部门！");

    /**
     * 系统置顶菜单不能超过8个
     */
    public static final RetCode SYSTEM_MENU_MORE_THAN_UPPER = new RetCode(107, "系统置顶菜单超过系统上限（8个）");

    /**
     * 系统公告不存在
     */
    public static final RetCode SYSTEM_BULLETIN_NOT_EXIST = new RetCode(108, "系统公告不存在！");
    /**
     * 系统置顶公告不能超过1个
     */
    public static final RetCode SYSTEM_BULLETIN_MORE_THAN_UPPER = new RetCode(109, "系统公告只支持置顶一个");
    /**
     * 帐号旧密码错误
     */
    public static final RetCode ACCOUNTS_PASSWORD_OLD_ERROR = new RetCode(110, "原始密码不正确，请重新输入！");
    /**
     * 文件不存在
     */
    public static final RetCode FILE_NOT_EXIST = new RetCode(111, "文件不存在！");
    /**
     * 文件上传异常
     */
    public static final RetCode FILE_UPLOAD_EXCEPTION = new RetCode(112, "文件上传异常！");
    /**
     * 存在同名角色
     */
    public static final RetCode EXIST_SAME_NAME_ROLE = new RetCode(113, "存在同名角色！");
    /**
     * 菜单地址已存在
     */
    public static final RetCode SYSTEM_MENU_HREF_EXIST = new RetCode(114, "菜单地址已存在！");
    /**
     * 参数错误
     */
    public static final RetCode PARAM_ERROR = new RetCode(200, "参数错误");

    /**
     * 交易记录不存在
     */
    public static final RetCode TRADE_RECORD_NOT_EXIST = new RetCode(1000, "交易记录不存在");
    /**
     * 收藏内容不存在
     */
    public static final RetCode WORK_COLLECTION_NOT_EXIST = new RetCode(1001, "收藏内容不存在");

    public RetCode fillArgs(Object... args) {
        int errorCode = this.errorCode;
        String errorDesc = this.errorDesc + ":" + Arrays.toString(args);
        return new RetCode(errorCode, errorDesc);
    }
}
