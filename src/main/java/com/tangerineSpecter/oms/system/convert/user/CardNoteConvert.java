package com.tangerinespecter.oms.system.convert.user;

import com.tangerinespecter.oms.system.convert.BaseConvert;
import com.tangerinespecter.oms.system.domain.entity.UserCardNote;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author 丢失的橘子
 */
@Mapper
public interface CardNoteConvert extends BaseConvert {

    CardNoteConvert INSTANCE = Mappers.getMapper(CardNoteConvert.class);

    @Mappings({
            @Mapping(target = "uid", expression = "java(uid())")
    })
    UserCardNote convert(CardNoteInfoVo vo);

}
