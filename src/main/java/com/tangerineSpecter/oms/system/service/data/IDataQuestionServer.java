package com.tangerinespecter.oms.system.service.data;

import com.tangerinespecter.oms.common.query.QuestionQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.data.QuestionInfoVo;

public interface IDataQuestionServer {

    ServiceResult queryForPage(QuestionQueryObject qo);

    ServiceResult insertInfo(QuestionInfoVo vo);

    ServiceResult update(QuestionInfoVo vo);

    ServiceResult delete(Long id);
}
