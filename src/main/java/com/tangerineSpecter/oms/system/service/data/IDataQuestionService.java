package com.tangerinespecter.oms.system.service.data;

import com.tangerinespecter.oms.common.query.QuestionQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataQuestion;
import com.tangerinespecter.oms.system.domain.vo.data.QuestionInfoVo;
import com.tangerinespecter.oms.system.service.BaseService;

public interface IDataQuestionService extends BaseService<QuestionQueryObject, DataQuestion> {

    /**
     * 添加问题
     *
     * @param param 请求参数
     */
    void insertInfo(QuestionInfoVo param);

    /**
     * 修改问题
     *
     * @param param 请求参数
     */
    void update(QuestionInfoVo param);

    /**
     * 删除问题
     *
     * @param id 问题id
     */
    void delete(Long id);
}
