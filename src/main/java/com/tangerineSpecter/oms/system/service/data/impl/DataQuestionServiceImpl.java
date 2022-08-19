package com.tangerinespecter.oms.system.service.data.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.tangerinespecter.oms.common.query.QuestionQueryObject;
import com.tangerinespecter.oms.system.convert.data.QuestionConvert;
import com.tangerinespecter.oms.system.domain.entity.DataQuestion;
import com.tangerinespecter.oms.system.domain.vo.data.QuestionInfoVo;
import com.tangerinespecter.oms.system.mapper.DataQuestionMapper;
import com.tangerinespecter.oms.system.service.data.IDataQuestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DataQuestionServiceImpl implements IDataQuestionService {

    @Resource
    private DataQuestionMapper dataQuestionMapper;

    @Override
    public PageInfo<DataQuestion> queryForPage(QuestionQueryObject qo) {
        return PageMethod.startPage(qo.getPage(), qo.getLimit())
                .doSelectPageInfo(() -> dataQuestionMapper.queryForPage(qo));
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
