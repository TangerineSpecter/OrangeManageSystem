package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.QuestionQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataQuestionMapper extends BaseMapper<DataQuestion> {

    List<DataQuestion> queryForPage(QuestionQueryObject qo);
}
