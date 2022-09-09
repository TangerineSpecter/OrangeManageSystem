package com.tangerinespecter.oms.system.convert.system;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.tangerinespecter.oms.system.convert.BaseConvert;
import com.tangerinespecter.oms.system.domain.dto.system.MessageDto;
import com.tangerinespecter.oms.system.domain.entity.SystemNotice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
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

    /**
     * 格式化时间为yyyy-MM-dd
     *
     * @param date 时间，字符串
     * @return 格式化结果
     */
    @Named("formatDate")
    default String formatDate(String date) {
        DateTime dateTime = DateUtil.parseDateTime(date);
        return DateUtil.formatDate(dateTime);
    }
}
