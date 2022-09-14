package com.tangerinespecter.oms.system.service.system;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.SystemNoticeQueryObject;
import com.tangerinespecter.oms.system.domain.entity.SystemNotice;
import com.tangerinespecter.oms.system.domain.vo.system.MessageVo;
import com.tangerinespecter.oms.system.domain.vo.system.NoticeUpdateStatusVo;

import javax.servlet.http.HttpServletResponse;

public interface ISystemNoticeService {

    /**
     * 消息推送
     *
     * @param response
     */
    void push(HttpServletResponse response);

    void messageSend(MessageVo vo);

    /**
     * 消息中心列表
     *
     * @param qo 查询参数
     * @return 分页结果
     */
    PageInfo<SystemNotice> queryForPage(SystemNoticeQueryObject qo);

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
