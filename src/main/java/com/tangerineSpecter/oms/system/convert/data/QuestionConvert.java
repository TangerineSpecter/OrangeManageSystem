package com.tangerinespecter.oms.system.convert.data;

import com.tangerinespecter.oms.system.convert.BaseConvert;
import com.tangerinespecter.oms.system.domain.entity.DataQuestion;
import com.tangerinespecter.oms.system.domain.vo.data.QuestionInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author 丢失的橘子
 */
@Mapper
public interface QuestionConvert extends BaseConvert {

    QuestionConvert INSTANCE = Mappers.getMapper(QuestionConvert.class);

    DataQuestion convert(QuestionInfoVo vo);
}
