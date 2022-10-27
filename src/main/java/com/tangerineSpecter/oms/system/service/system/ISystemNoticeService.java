package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.query.SystemNoticeQueryObject;
import com.tangerinespecter.oms.system.domain.entity.SystemNotice;
import com.tangerinespecter.oms.system.domain.vo.system.MessageVo;
import com.tangerinespecter.oms.system.domain.vo.system.NoticeUpdateStatusVo;
import com.tangerinespecter.oms.system.service.BaseService;

import javax.servlet.http.HttpServletResponse;

public interface ISystemNoticeService extends BaseService<SystemNoticeQueryObject, SystemNotice> {

    /**
     * 消息推送
     *
     * @param response
     */
    void push(HttpServletResponse response);

    void messageSend(MessageVo vo);

    /**
     * 批量修改删除状态
     *
     * @param vo 修改参数
     * @return 未读消息数量
     */
    long batchUpdateDelStatus(NoticeUpdateStatusVo vo);

    /**
     * 彻底清理消息
     *
     * @param vo 清理参数
     */
    void batchClear(NoticeUpdateStatusVo vo);

    /**
     * 批量已读状态
     *
     * @param vo 修改参数
     * @return 消息未读条数
     */
    long batchUpdateReadStatus(NoticeUpdateStatusVo vo);

    /**
     * 获取消息详情
     *
     * @param id 消息ID
     * @return 消息内容
     */
    SystemNotice getNoticeInfo(Long id);
}
