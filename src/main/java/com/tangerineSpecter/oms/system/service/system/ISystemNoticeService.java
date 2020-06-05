package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.query.SystemNoticeQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
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

    ServiceResult messageSend(MessageVo vo);

    ServiceResult queryForPage(SystemNoticeQueryObject qo);

    ServiceResult batchUpdateDelStatus(NoticeUpdateStatusVo ids);

    ServiceResult batchClear(NoticeUpdateStatusVo ids);

    ServiceResult batchUpdateReadStatus(NoticeUpdateStatusVo ids);

    /**
     * 获取消息详情
     *
     * @param id 消息ID
     * @return 消息内容
     */
    SystemNotice getNoticeInfo(Long id);
}
