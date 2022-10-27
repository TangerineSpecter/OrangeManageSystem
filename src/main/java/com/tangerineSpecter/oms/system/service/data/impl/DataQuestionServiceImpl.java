package com.tangerinespecter.oms.system.service.data.impl;

import com.tangerinespecter.oms.common.query.QuestionQueryObject;
import com.tangerinespecter.oms.system.convert.data.QuestionConvert;
import com.tangerinespecter.oms.system.domain.entity.DataQuestion;
import com.tangerinespecter.oms.system.domain.vo.data.QuestionInfoVo;
import com.tangerinespecter.oms.system.mapper.DataQuestionMapper;
import com.tangerinespecter.oms.system.service.data.IDataQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataQuestionServiceImpl implements IDataQuestionService {

    private final DataQuestionMapper dataQuestionMapper;

    @Override
    public List<DataQuestion> list(QuestionQueryObject qo) {
        return dataQuestionMapper.queryForPage(qo);
    }

    @Override
    public void insertInfo(QuestionInfoVo vo) {
        dataQuestionMapper.insert(QuestionConvert.INSTANCE.convert(vo));
    }

    @Override
    public void update(QuestionInfoVo vo) {
        dataQuestionMapper.updateById(QuestionConvert.INSTANCE.convert(vo));
    }

    @Override
    public void delete(Long id) {
        dataQuestionMapper.deleteById(id);
    }
}
