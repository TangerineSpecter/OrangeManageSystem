package com.tangerinespecter.oms.system.convert.system;

import cn.hutool.core.convert.Convert;
import com.tangerinespecter.oms.common.enums.ScheduledTypeEnum;
import com.tangerinespecter.oms.job.schedule.SendMsgBot;
import com.tangerinespecter.oms.system.convert.BaseConvert;
import com.tangerinespecter.oms.system.domain.entity.SystemScheduledTask;
import com.tangerinespecter.oms.system.domain.vo.system.SystemScheduledVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * @author 丢失的橘子
 */
@Mapper
public interface ScheduledConvert extends BaseConvert {

    ScheduledConvert INSTANCE = Mappers.getMapper(ScheduledConvert.class);

    @Mappings({
        @Mapping(target = "msgType", constant = "0"),
        @Mapping(target = "classPath", expression = "java(getClassPath(param.getType(), param.getClassPath()))"),
        @Mapping(target = "extraInfo", expression = "java(getExtraInfo(param))")
    })
    SystemScheduledTask convert(SystemScheduledVo param);

    @Named("getClassPath")
    default String getClassPath(Integer type, String classPath) {
        //机器人使用固定的通知类
        if (ScheduledTypeEnum.BOT_NOTIFY.getValue().equals(type)) {
            return SendMsgBot.class.getName();
        }
        return classPath;
    }

    @Named("getExtraInfo")
    default String getExtraInfo(SystemScheduledVo param) {
        if (ScheduledTypeEnum.BOT_NOTIFY.getValue().equals(param.getType())) {
            return Convert.toStr(param.getBotId());
        } else {
            return param.getParams();
        }
    }
}
