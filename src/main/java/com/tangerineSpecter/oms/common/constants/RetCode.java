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
     * 操作频繁
     */
    public static final RetCode SYSTEM_ERROR = new RetCode(3, "服务器错误，请联系管理员");

    /**
     * 验证失败
     */
    public static final RetCode VERIFY_CODE_ERROR = new RetCode(4, "验证码错误");

    /**
     * 账号已被注册
     */
    public static final RetCode REGISTER_REPEAT = new RetCode(100, "该帐号已注册，请重新申请！");

    /**
     * 账号密码错误
     */
    public static final RetCode ACCOUNTS_PASSWORD_ERROR = new RetCode(101, "密码错误，请重新确认后登录！");

    /**
     * 注册账号不存在
     */
    public static final RetCode REGISTER_ACCOUNTS_NOT_EXIST = new RetCode(102, "您输入的帐号不存在，请注册后重新登录！");

    /**
     * 账号不存在
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
     * 账号旧密码错误
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
     * 密码长度不能小于6位
     */
    public static final RetCode PASSWORD_LENGTH_TOO_SHORT = new RetCode(115, "密码不能小于6位");
    /**
     * 账号未在线
     */
    public static final RetCode ACCOUNT_NOT_LOGIN = new RetCode(116, "账号未在线");
    /**
     * 定时任务已存在
     */
    public static final RetCode TASK_EXIST = new RetCode(117, "定时任务已存在");
    /**
     * 未知的平台
     */
    public static final RetCode UNKNOWN_PLATFORM = new RetCode(118, "未知的平台");
    /**
     * 任务执行异常
     */
    public static final RetCode TASK_EXECUTE_ERROR = new RetCode(119, "任务执行异常");
    /**
     * 任务路径不存在
     */
    public static final RetCode TASK_EXECUTE_NOT_EXIST = new RetCode(120, "任务路径不存在");
    /**
     * 参数错误
     */
    public static final RetCode PARAM_ERROR = new RetCode(200, "参数错误");
    /**
     * 数据异常
     */
    public static final RetCode DATA_EXCEPTION = new RetCode(201, "数据异常");

    /**
     * 交易记录不存在
     */
    public static final RetCode TRADE_RECORD_NOT_EXIST = new RetCode(1000, "交易记录不存在");
    /**
     * 收藏内容不存在
     */
    public static final RetCode WORK_COLLECTION_NOT_EXIST = new RetCode(1001, "收藏内容不存在");
    /**
     * 交易逻辑不存在
     */
    public static final RetCode TRADE_LOGIC_NOT_EXIST = new RetCode(1002, "交易逻辑不存在");

    /**
     * 健康记录已存在
     */
    public static final RetCode HEALTH_RECORD_EXIST = new RetCode(2000, "健康记录已存在");

    /**
     * 视频地址有误
     */
    public static final RetCode VIDEO_URL_ERROR = new RetCode(3000, "视频地址有误");
    /**
     * 分析内容不存在
     */
    public static final RetCode NLP_CONTENT_NOT_EXIST = new RetCode(3001, "缺少分析内容");

    public RetCode fillArgs(Object... args) {
        int errorCode = this.errorCode;
        String errorDesc = this.errorDesc + ":" + Arrays.toString(args);
        return new RetCode(errorCode, errorDesc);
    }
}
