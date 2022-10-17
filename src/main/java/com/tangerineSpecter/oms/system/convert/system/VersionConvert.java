package com.tangerinespecter.oms.system.convert.system;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.tangerinespecter.oms.system.convert.BaseConvert;
import com.tangerinespecter.oms.system.domain.dto.system.VersionHistoryListDto;
import com.tangerinespecter.oms.system.domain.enums.VersionEnum;
import com.tangerinespecter.oms.system.domain.pojo.SystemVersionInfo;
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
public interface VersionConvert extends BaseConvert {

    VersionConvert INSTANCE = Mappers.getMapper(VersionConvert.class);

    List<VersionHistoryListDto> convert(List<SystemVersionInfo.History> history);

    @Mappings({
            @Mapping(target = "version", source = "code"),
            @Mapping(target = "createTime", source = "date"),
            @Mapping(target = "historyInfos", source = "content"),
    })
    VersionHistoryListDto convert(SystemVersionInfo.History history);


    List<VersionHistoryListDto.History> contentListToHistoryList(List<SystemVersionInfo.History.Content> list);

    @Mappings({
            @Mapping(target = "type", expression = "java(convert2VersionType(history.getInfo()))"),
            @Mapping(target = "content", expression = "java(convert2VersionInfo(history.getInfo()))")
    })
    VersionHistoryListDto.History contentListToHistoryList(SystemVersionInfo.History.Content history);

    /**
     * 转化为版本描述对应type
     *
     * @param info 描述信息
     * @return 版本记录type
     */
    @Named("convert2VersionType")
    default Integer convert2VersionType(String info) {
        List<String> result = CharSequenceUtil.split(info, "：");
        return VersionEnum.convert2Type(CollUtil.get(result, 0));
    }

    /**
     * 转化为版本描述对应内容
     *
     * @param info 描述信息
     * @return 版本记录内容
     */
    @Named("convert2VersionInfo")
    default String convert2VersionInfo(String info) {
        List<String> result = CharSequenceUtil.split(info, "：");
        return CollUtil.get(result, 1);
    }
}
