package com.tangerinespecter.oms.job.message;

/**
 * 基础消息接口
 *
 * @author 丢失的橘子
 */
public interface IBaseMessage {

    /**
     * 获取标题
     *
     * @return 消息标题
     */
    String getTitle();

    /**
     * 获取消息内容
     *
     * @return 消息内容
     */
    String getContent();
}
