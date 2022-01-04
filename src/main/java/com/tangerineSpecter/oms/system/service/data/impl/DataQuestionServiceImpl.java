package com.tangerinespecter.oms.system.service.data.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.query.QuestionQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.DataQuestion;
import com.tangerinespecter.oms.system.domain.vo.data.QuestionInfoVo;
import com.tangerinespecter.oms.system.mapper.DataQuestionMapper;
import com.tangerinespecter.oms.system.service.data.IDataQuestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataQuestionServiceImpl implements IDataQuestionService {

    @Resource
    private DataQuestionMapper dataQuestionMapper;

    @Override
    public ServiceResult queryForPage(QuestionQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<DataQuestion> pageList = dataQuestionMapper.queryForPage(qo);
        PageInfo<DataQuestion> stockPageInfo = new PageInfo<>(pageList);
        return ServiceResult.pageSuccess(pageList, stockPageInfo.getTotal());
    }

    @Override
    public ServiceResult insertInfo(QuestionInfoVo vo) {
        DataQuestion question = DataQuestion.builder().question(vo.getQuestion()).content(vo.getContent())
                .isDel(CommonConstant.IS_DEL_NO).build();
        dataQuestionMapper.insert(question);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult update(QuestionInfoVo vo) {
        DataQuestion dataQuestion = dataQuestionMapper.selectById(vo.getId());
        dataQuestion.setQuestion(vo.getQuestion());
        dataQuestion.setContent(vo.getContent());
        dataQuestionMapper.updateById(dataQuestion);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult delete(Long id) {
        dataQuestionMapper.deleteById(id);
        return ServiceResult.success();
    }
}
