package com.tangerinespecter.oms.system.convert.system;

import com.tangerinespecter.oms.system.convert.BaseConvert;
import com.tangerinespecter.oms.system.domain.dto.system.MessageDto;
import com.tangerinespecter.oms.system.domain.entity.SystemNotice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 丢失的橘子
 */
@Mapper
public interface MessageConvert extends BaseConvert {

    MessageConvert INSTANCE = Mappers.getMapper(MessageConvert.class);

    List<MessageDto.ChildrenDto> convert(List<SystemNotice> list);

    @Mappings({
            @Mapping(target = "avatar", constant = "../../admin/images/notice.png"),
            @Mapping(target = "context", source = "content"),
            @Mapping(target = "time", expression = "java(formatDate(notice.getCreateTime()))")
    })
    MessageDto.ChildrenDto convert(SystemNotice notice);

}
